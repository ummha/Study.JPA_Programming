package com.example.jpa.bookmanager.repository;

import com.example.jpa.bookmanager.domain.User;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            if(a.getId() > b.getId()) return -1;
            else if(a.getId() == b.getId()) return 0;
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
    }
}