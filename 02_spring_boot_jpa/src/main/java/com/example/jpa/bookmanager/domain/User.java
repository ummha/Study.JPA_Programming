package com.example.jpa.bookmanager.domain;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @Getter
 * @Setter
 * @ToString
 * @NoArgsConstructor : 인자가 없는 생성자를 만들어줌
 * @AllArgsConstructor : 모든 인자를 이용한 생성자를 만들어줌
 * @RequiredArgsConstructor : 꼭 필요한 인자만을 이용해서 생성자를 만들어줌
 * @NonNull : 필수 변수 정의 (@RequiredArgsConstructor 가 생성자 인자를 통해서 받아줌)
 * @EqualsAndHashCode : JPA에서는 굳이 필요하지는 않지만 자바에서 equals와 hashcode 오버라이딩을 권고하고 있음
 * @Data : @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode 포함
 * @Builder : 객체를 생성하고 필드의 값을 주입해줌
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class User {
    @NonNull
    private String name;
    @NonNull
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
