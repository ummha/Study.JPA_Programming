package com.example.jpa.bookmanager.repository;

import com.example.jpa.bookmanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    // Query return types = https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#appendix.query.return.types
    // Query subject keywords = https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#appendix.query.method.subject
    List<User> findByName(String name);

    User findByEmail(String email);

    User getByEmail(String email);

    User readByEmail(String email);

    User queryByEmail(String email);

    User searchByEmail(String email);

    User streamByEmail(String email);

    User findUserByEmail(String email);

    User findSomethingByEmail(String email);

    User findFirst1ByName(String name);

    List<User> findTop2ByName(String name);

    List<User> findLast1ByName(String name); // 인식하지 않는 키워드 작성시 -> 오류 또는 무시됨

    List<User> findByEmailAndName(String email, String name);

    List<User> findByEmailOrName(String email, String name);

    List<User> findByCreatedAtAfter(LocalDateTime yesterday);

    List<User> findByIdAfter(Long id);

    List<User> findByIdBefore(Long id);

    List<User> findByCreatedAtGreaterThan(LocalDateTime yesterday);

    List<User> findByIdGreaterThanEqual(Long id);

    List<User> findByIdLessThanEqual(Long id);

    List<User> findByCreatedAtBetween(LocalDateTime yesterday, LocalDateTime tomorrow);

    List<User> findByIdBetween(Long start, Long end);

    List<User> findByIdGreaterThanEqualAndIdLessThanEqual(Long start, Long end);
}
