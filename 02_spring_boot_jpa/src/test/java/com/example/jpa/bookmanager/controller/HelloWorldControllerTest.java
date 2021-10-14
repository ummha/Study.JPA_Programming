package com.example.jpa.bookmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 해당 @WebMvcTest 와 같은 SliceTest 형식은 전체 Spring Context 를 로딩하지 않고 웹 컨트롤러에 대한 일부의 빈들만 로딩하기 때문에
 * 전체 테스트시 JPA Option 에서 오류가 발생한다. (예: Caused by: java.lang.IllegalArgumentException: JPA metamodel must not be empty! )
 * 해결방법 1: @MockBean(JpaMetamodelMappingContext.class) 활용 - 마치 해당 빈이 있는 것처럼 테스트 진행
 * 해결방법 2: Application.java 에서 @EnableJpaAuditing 을 configuration.JpaConfiguration 처럼 별도로 처리한다.
 * 해결방법 3: SliceTest 를 사용하지 않고 Full Spring boot Test 를 사용한다. (@WebMvcTest -> @SpringBootTest), 추가 작업 필요(예, @BeforeEach)
 *
 * 해결방법 추천 2: 해결방법 2를 적용하게 된다면 별도로 처리되기 때문에 누군가가 해결방법1을 쓰던지, 2를 쓰던지 상관없이 오류 없이 처리된다.
 *               즉, 선택은 자유이나 좀 더 보편적인 방식을 추구해보자.
 */
@WebMvcTest
//@MockBean(JpaMetamodelMappingContext.class)
//@SpringBootTest
class HelloWorldControllerTest {

    // 1. @WebMvcTest 사용시
    @Autowired
    private MockMvc mockMvc;
    // 1. -------

    // 2. @SpringBootTest 사용시
//    @Autowired
//    private WebApplicationContext wac;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void before() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//    }
    // 2. -------

    @Test
    void helloWorld() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello-world"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello world"));
    }
}