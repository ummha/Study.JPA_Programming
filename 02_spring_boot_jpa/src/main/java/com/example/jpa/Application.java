package com.example.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/**
 * HelloWorldControllerTest의 해결방안 2 적용시 @EnableJpaAuditing 주석하기
 */
//@EnableJpaAuditing // JpaConfiguration 에서 별도로 처리함 (이유: 전체 단위 테스트 실행시 Controller 에서 오류 발생 방지),
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
