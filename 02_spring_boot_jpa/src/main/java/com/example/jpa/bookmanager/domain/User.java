package com.example.jpa.bookmanager.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
@Entity // 자바객체 선언 어노테이션
@Table(name = "user", indexes = {@Index(columnList = "name")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@EntityListeners(value = {AuditingEntityListener.class, UserEntityListener.class})
public class User implements Auditable {
    @Id // PK 설정
    @GeneratedValue // 자동 카운트
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    // updatable : 수정시 반영할지에 대한 여부(false=반영안함)
    // insertable : 삽입시 반영할지에 대한 여부(false=반영안함)
    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column
    @Enumerated(value = EnumType.STRING)
    // Enum 객체는 기본적으로 DB에 ordinary(index 0,1,2...) 값이 들어가게 된다. 그러니 꼭 EnumType.STRING을 정의해주자
    private Gender gender;

    // @Transient : 영속성 처리에서 제외하는 어노테이션, DB데이터 반영X, 해당객체와 생명주기가 동일
    @Transient
    private String testData;

//    @OneToMany(fetch = FetchType.EAGER)
//    private List<Address> address;

//    @PrePersist // 삽입 전
//    public void prePersist() {
//        System.out.println(">>> CALL prePersist()");
//        this.createdAt = LocalDateTime.now();
//        this.updatedAt = LocalDateTime.now();
//    }

//    @PostPersist // 삽입 후
//    public void postPersist() {
//        System.out.println(">>> CALL postPersist()");
//    }

//    @PreUpdate // 수정 전
//    public void preUpdate() {
//        System.out.println(">>> CALL preUpdate()");
//        this.updatedAt = LocalDateTime.now();
//    }

//    @PostUpdate // 수정 후
//    public void postUpdate() {
//        System.out.println(">>> CALL postUpdate()");
//    }
//
//    @PreRemove // 삭제 전
//    public void preRemove() {
//        System.out.println(">>> CALL preRemove()");
//    }
//
//    @PostRemove // 삭제 후
//    public void postRemove() {
//        System.out.println(">>> CALL postRemove()");
//    }
//
//    @PostLoad // 조회가 일어난 직후
//    public void postLoad() {
//        System.out.println(">>> CALL postLoad()");
//    }
}
