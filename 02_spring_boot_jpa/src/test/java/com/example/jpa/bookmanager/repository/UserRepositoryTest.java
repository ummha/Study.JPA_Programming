package com.example.jpa.bookmanager.repository;

import com.example.jpa.bookmanager.domain.User;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;

@SpringBootTest // Spring Context를 로딩해서 활용하겠다는 어노테이션
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

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
         * delete == delete
         * delete() <- must not be null
         * delete(), deleteById() <- delete를 하기 전에 해당 자료가 존재하는지 여부를 위한 select 쿼리가 돌아감
         */
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
        userRepository.save(new User("new Minseo", "minseo@gmail.com"));

        userRepository.flush();

        userRepository.findAll().forEach(System.out::println);
    }

    @Test
    void flushTest2() {
        userRepository.saveAndFlush(new User("new Minseo", "minseo@gmail.com"));

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
}
