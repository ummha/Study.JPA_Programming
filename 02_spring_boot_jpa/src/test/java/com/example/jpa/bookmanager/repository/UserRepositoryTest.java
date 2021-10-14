package com.example.jpa.bookmanager.repository;

import com.example.jpa.bookmanager.domain.Gender;
import com.example.jpa.bookmanager.domain.User;
import com.example.jpa.bookmanager.domain.UserHistory;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;

@SpringBootTest // Spring Context를 로딩해서 활용하겠다는 어노테이션
@Transactional  // 전체 테스트 수행시 앞전에 테스트 했던 데이터가 변경됨에 따라 오류가 발생했던 것을 일시방지함.
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Resource
    private UserHistoryRepository userHistoryRepository;

    @Test
    void crud() {
        /**
         * select == find
         * findAll : 전체 조회
         * finaAllById : 복수의 Id를 대상으로 조회 ex) where id in (?, ?, ?, ...)
         * findById : 단 하나의 Id를 조회 ex) where id = ?
         *          반환객체 -> Optional
         * getOne : @Deprecated / use -> getById()
         */
        List<User> users1 = userRepository.findAll(Sort.by(Direction.DESC, "name"));
        System.out.println(">>> findAll() with Sort");
        users1.forEach(System.out::println);

        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(3L);
        ids.add(5L);
        var users2 = userRepository.findAllById(ids);
        System.out.println(">>> findAllById() Solution - 1 ");
        users2.forEach(System.out::println);

        System.out.println(">>> findAllById() Solution - 2 (using test library-assertj) ");
        userRepository.findAllById(Lists.newArrayList(2L, 4L)).forEach(System.out::println);

        System.out.println(">>> findById()");
        Optional<User> user = userRepository.findById(1L);
        System.out.println(user.get());
        // Optional 객체를 통해 선택권을 제공 받는다.
        var nullUser = userRepository.findById(100L).orElse(null);
        System.out.println(nullUser);

        // try sorting after findAllById
        userRepository.findAllById(Lists.newArrayList(1L, 3L, 5L)).stream().sorted((a, b) -> {
            if (a.getId() > b.getId()) return -1;
            else if (a.getId() == b.getId()) return 0;
            else return 1;
        }).forEach(System.out::println);

        /**
         * insert == save
         * save : 단수, 한개의 객체 저장
         * saveAll : 복수, 다수의 객체 리스트 저장
         */
        User user1 = new User("jack", "jack@gmail.com");
        User user2 = new User("steve", "steve@gmail.com");

        System.out.println(">>> saveAll() ");
        userRepository.saveAll(Lists.newArrayList(user1, user2));
        userRepository.findAll().forEach(System.out::println);

        /**
         * update == save
         * Look -> SimpleJpaRepository
         */
        System.out.println(">>> save() : update");
        User u = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        u.setName("MINSEO");
        userRepository.save(u);
        userRepository.findAll().forEach(System.out::println);

        /**
         * delete == delete
         * delete() <- must not be null
         * delete(), deleteById() <- delete를 하기 전에 해당 자료가 존재하는지 여부를 위한 select 쿼리가 돌아감
         */
        // 관계 형성을 추가하고 나서 삭제후 조회시 constraint 오류 발생 -> 자식 엔터티 데이터 삭제 (실제 개발시 이렇게 하면 안됨!)
        userHistoryRepository.deleteAll();
        userRepository.delete(userRepository.findById(1L).orElseThrow(RuntimeException::new));
        userRepository.findAll().forEach(System.out::println);


        userRepository.deleteById(2L);
        userRepository.findAll().forEach(System.out::println);
    }

    /**
     * flush는 quert에 변화를 주는 것이 아니고 DB 반영 시점을 조절하는 기능이므로,
     * 로그 상에서 별다른 차이점을 찾을 수 없다.
     */

    @Test
    void flushTest1() {
        userRepository.save(new User("new Minseo", "newMinseo1@gmail.com"));

        userRepository.flush();

        userRepository.findAll().forEach(System.out::println);
    }

    @Test
    void flushTest2() {
        userRepository.saveAndFlush(new User("new Minseo", "newMinseo2@gmail.com"));

        userRepository.findAll().forEach(System.out::println);
    }

    @Test
    void countTest() {
        long count = userRepository.count();
        System.out.println("count: " + count);
    }

    @Test
    void existsTest() {
        // 특이점: existsById 는 쿼리상에 count(*)로 확인한다.
        boolean exists = userRepository.existsById(1L);
        System.out.println("exists: " + exists);
    }

    @Test
    void deleteAllTest1() {
        // delete from user where id = ? 의 쿼리가 연속적으로 실행됨 :: findAll()과 같이 데이터가 많을시 성능 효율이 떨어짐
        userRepository.deleteAll();
        userRepository.findAll().forEach(System.out::println);
    }

    @Test
    void deleteAllTest2() {
        // deleteAllTest1() 의 결과로 인해 다음과 같은 정의가 가능하다. :: findAll()과 같이 데이터가 많을시 성능 효율이 떨어짐
        userRepository.deleteAll(userRepository.findAllById(Lists.newArrayList(1L, 2L)));
        userRepository.findAll().forEach(System.out::println);
    }

    @Test
    void deleteInBatchTest() {
        // deleteInBatch : @Deprecated
        // deleteAllInBatch : **delete 쿼리가 하나만 실행되며 or 연산자를 사용하여 삭제 쿼리가 작성됨 + **select 쿼리로 자료존재 여부 확인 안함
        userRepository.deleteAllInBatch(userRepository.findAllById(Lists.newArrayList(1L, 2L)));
        userRepository.deleteAllByIdInBatch(Lists.newArrayList(3L, 4L));
        userRepository.findAll().forEach(System.out::println);
        // 파라미터를 전달하지 않을시 where 조건문 없이 delete from user 쿼리가 작성됨. 전체 데이터가 삭제되므로 주의해야함.
        userRepository.deleteAllInBatch();
        userRepository.findAll().forEach(System.out::println);
    }

    @Test
    void pagingTest() {
        // page index는 0부터 시작
        Page<User> users = userRepository.findAll(PageRequest.of(0, 3));

        System.out.println("page: " + users);
        users.forEach(System.out::println);
        System.out.println("total elements : " + users.getTotalElements());
        System.out.println("total pages : " + users.getTotalPages());
        System.out.println("number of elements : " + users.getNumberOfElements());
        System.out.println("sort : " + users.getSort());
        System.out.println("size: " + users.getSize());
        users.getContent().forEach(System.out::println);
    }

    /**
     * matcher는 어떻게 보면 만능같아 보이지만, 약간의 제약이 있다. 예) String 자료형만 가능
     * 실제로 matcher는 생각보다 많이 쓰이지 않는다고 한다.
     */
    // qbd = query by example / qbm = query by matcher
    @Test
    void qbeTest1() {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("name")    // 무시할 필드명
                .withMatcher("email", endsWith());  // 매칭할(조건절) 필드명
        Example<User> example = Example.of(new User("mi", ".com"), matcher);
        userRepository.findAll(example).forEach(System.out::println);
    }

    @Test
    void qbeTest2() {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("name")
                .withMatcher("email", contains());
        Example<User> example = Example.of(new User("", "gmail"), matcher);
        userRepository.findAll(example).forEach(System.out::println);
    }

    @Test
    void qbeTest3() {
        User user = new User();
        user.setEmail("min");
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("email", startsWith());
        Example<User> example = Example.of(user, matcher);
        userRepository.findAll(example).forEach(System.out::println);
    }

    @Test
    void qbeTest4() {
        Example<User> example = Example.of(new User("minseo", "minseo@gmail.com"));
        userRepository.findAll(example).forEach(System.out::println);
    }

    @Test
    void selectQueryMethodTest1() {
        // 다양한 방식의 select 절
        System.out.println(userRepository.findByName("John"));
        System.out.println("findByEmail: " + userRepository.findByEmail("minseo@gmail.com"));
        System.out.println("getByEmail: " + userRepository.getByEmail("minseo@gmail.com"));
        System.out.println("readByEmail: " + userRepository.readByEmail("minseo@gmail.com"));
        System.out.println("queryByEmail: " + userRepository.queryByEmail("minseo@gmail.com"));
        System.out.println("searchByEmail: " + userRepository.searchByEmail("minseo@gmail.com"));
        System.out.println("streamByEmail: " + userRepository.streamByEmail("minseo@gmail.com"));
        System.out.println("findUserByEmail: " + userRepository.findUserByEmail("minseo@gmail.com"));
        System.out.println("findSomethingByEmail: " + userRepository.findSomethingByEmail("minseo@gmail.com"));
    }

    @Test
    void selectQueryMethodTest2() {
        // 하나의 조건절
        System.out.println("findFirst1ByName: " + userRepository.findFirst1ByName("John"));
        System.out.println("findTop2ByName: " + userRepository.findTop2ByName("John"));
        System.out.println("findLast1ByName: " + userRepository.findLast1ByName("John"));
    }

    @Test
    void selectQueryMethodTest3() {
        // 다수의 AND 조건절
        System.out.println("findByEmailAndName: " + userRepository.findByEmailAndName("minseo@gmail.com", "minseo"));
        // 다수의 OR 조건절
        System.out.println("findByEmailOrName: " + userRepository.findByEmailOrName("minseo@gmail.com", "John"));
    }

    @Test
    void afterBeforeTest() {
        System.out.println("findByCreatedAtAfter: " + userRepository.findByCreatedAtAfter(LocalDateTime.now().minusDays(1L)));
        // 다음과 같은 조건절 작성됨 : 비교 연산자 사용
        // WHERE createdAt > ?

        System.out.println("findByIdAfter: " + userRepository.findByIdAfter(3L));
        // WHERE id > ?
        System.out.println("findByIdBefore: " + userRepository.findByIdBefore(3L));
        // WHERE id < ?
    }

    @Test
    void thanTest() {
        /**
         * after/before는 equal(=)을 포함한 비교 연산자가 적용되지 않는다.
         * 하지만 than 구문은 GreaterThanEqual(>=) 방식으로 작성 가능하다.
         * 즉, 가독성은 after,before가 더 좋겠지만 than 구문이 더 범용성이 넓다고 볼 수 있다.
         */

        System.out.println("findByCreatedAtGreaterThan: " + userRepository.findByCreatedAtGreaterThan(LocalDateTime.now().minusDays(1L)));
        // WHERE createdAt > ?

        System.out.println("findByIdGreaterThanEqual: " + userRepository.findByIdGreaterThanEqual(3L));
        // WHERE id >= ?
        System.out.println("findByIdLessThanEqual: " + userRepository.findByIdLessThanEqual(3L));
        // WHERE id <= ?
    }

    @Test
    void betweenTest() {
        // between 구문
        System.out.println("findByCreatedAtBetween: " + userRepository.findByCreatedAtBetween(LocalDateTime.now().minusDays(1L), LocalDateTime.now().plusDays(1L)));

        // 아래 두 개는 같은 결과
        System.out.println("findByIdBetween: " + userRepository.findByIdBetween(1L, 3L));
        System.out.println("findByIdGreaterThanEqualAndIdLessThanEqual: " + userRepository.findByIdGreaterThanEqualAndIdLessThanEqual(1L, 3L));
    }

    @Test
    void isNotTest() {
        System.out.println("findByIdIsNotNull: " + userRepository.findByIdIsNotNull());
        // WHERE id IS NOT NULL

        // 오류
        // System.out.println("findByIdIsNotEmpty: " + userRepository.findByIdIsNotEmpty());
        // 자바 문자열 not empty는 일반적으로 null과 빈문자열("")의 여부를 확인한다.
        // 하지만 해당 NotEmpty 구문은 Collection 타입의 NotEmpty를 의미한다.
        // 이후 relational에서 사용하게 되지만, 잘 사용되지 않음

        // relational과 isNotEmpty 사용예
//        System.out.println("findByAddressIsNotEmpty: " + userRepository.findByAddressIsNotEmpty());
    }

    @Test
    void inTest() {
        System.out.println("findByNameIn: " + userRepository.findByNameIn(Lists.newArrayList("minseo", "John")));
        // WHERE name IN (?, ?)
    }

    @Test
    void likeTest() {
        System.out.println("findByNameStartingWith: " + userRepository.findByNameStartingWith("Jo"));
        System.out.println("findByNameEndingWith: " + userRepository.findByNameEndingWith("seo"));

        // 아래 두 개의 결과 동일
        System.out.println("findByNameContains: " + userRepository.findByNameContains("ns"));
        System.out.println("findByNameLike: " + userRepository.findByNameLike("%ns%"));
    }

    @Test
    void isTest() {
        // 결과 모두 동일
        System.out.println("findByName: " + userRepository.findByName("minseo"));
        System.out.println("findUserByNameIs: " + userRepository.findUserByNameIs("minseo"));
        System.out.println("findUserByName: " + userRepository.findUserByName("minseo"));
        System.out.println("findByNameEquals: " + userRepository.findByNameEquals("minseo"));
    }

    @Test
    void pagingAndSortingTest() {
        System.out.println("findTop1ByName: " + userRepository.findTop1ByName("John"));

        // 마지막 이름이 John인 데이터를 가져오려고 했지만 가져와지지 않음
        System.out.println("findLast1ByName: " + userRepository.findLast1ByName("John"));

        /**
         * order by
         */
        // 역순해서 가져옴 TopN -> N: default=1
        System.out.println("findTop1ByNameOrderByIdDesc: " + userRepository.findTop1ByNameOrderByIdDesc("John"));
        System.out.println("findTopByNameOrderByIdDesc: " + userRepository.findTopByNameOrderByIdDesc("John"));

        // 메소드 네이밍으로 정렬을 할 경우 Order by가 길어질수록 가독성이 떨이지며, 메소드 자유도가 떨어진다.
        System.out.println("findFirstByNameOrderByIdDescEmailAsc: " + userRepository.findFirstByNameOrderByIdDescEmailAsc("John"));

        System.out.println("findFirstByName (with sort param): " + userRepository.findFirstByName("John", Sort.by(Order.desc("id"), Order.asc("email"))));

        // 페이징
        System.out.println("findByName (with paging): " + userRepository.findByName(
                "John"
                , PageRequest.of(0, 1, Sort.by(Order.desc("id")))
        ).getContent());
        System.out.println("findByName (with paging): " + userRepository.findByName(
                "John"
                , PageRequest.of(1, 1, Sort.by(Order.desc("id")))
        ).getContent());
    }

    @Test
    void insertAndUpdateTest() {
        User user = new User();
        user.setName("MinseoRhie");
        user.setEmail("minseorhie@gmail.com");

        userRepository.save(user);
        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user2.setName("MMMM");

        userRepository.save(user2);
    }

    @Test
    void enumTest() {
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);

        user.setGender(Gender.MALE);

        userRepository.save(user);

        userRepository.findAll().forEach(System.out::println);

        System.out.println(userRepository.findRowRecord().get("gender"));
    }

    @Test
    void listenerTest() {
        User user = new User();
        user.setEmail("minseo2@gmail.com");
        user.setName("minseo");
        userRepository.save(user);

        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user2.setName("MMMMM");
        userRepository.save(user2);

        userRepository.deleteById(4L);
    }

    @Test
    void prePersistTest() {
        User user = new User();
        user.setEmail("minseo2@gmail.com");
        user.setName("MINSEO");
//        user.setCreatedAt(LocalDateTime.now());
//        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        System.out.println(userRepository.findByEmail("minseo2@gmail.com"));
    }

    @Test
    void preUpdateTest() {
        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        System.out.println(user2);
        user2.setName("MMMMM");
        userRepository.save(user2);
        System.out.println("to-be: "+ userRepository.findAll().get(0));
    }

    @Test
    void userHistoryTest() {
        User user = new User();
        user.setEmail("minseoNew@gmail.com");
        user.setName("minseoNEW");
        userRepository.save(user);

        user.setName("minseoNewNew");
        userRepository.save(user);

        userHistoryRepository.findAll().forEach(System.out::println);
    }

    @Test
    void userRelationTest() {
        User user = new User();
        user.setName("David");
        user.setEmail("david@gmail.com");
        user.setGender(Gender.MALE);

        userRepository.save(user);

        user.setName("Daniel");
        userRepository.save(user);

        user.setEmail("daniel@gmail.com");
        userRepository.save(user);

        userHistoryRepository.findAll().forEach(System.out::println);

//        List<UserHistory> result = userHistoryRepository.findByUserId(userRepository.findByEmail("daniel@gmail.com").getId());
        List<UserHistory> result = userRepository.findByEmail("daniel@gmail.com").getUserHistories();
        result.forEach(System.out::println);

        System.out.println(">>> UserHistory.getUser(): " + userHistoryRepository.findAll().get(0).getUser());
    }
}
