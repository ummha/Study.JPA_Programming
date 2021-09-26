package com.example.jpa.bookmanager.domain;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BookReviewInfo extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private Long bookId;

    // 1대 1 관계 어노테이션
    // 해당 어노테이션 작성시 외래키로 설정될 book_id 필드가 자동으로 create 됨
    // optional 속성 = false : null을 허용하지 않겠다는 의미, book_id에 NOT NULL 조건 추가됨 + LEFT OUTER JOIN -> INNER JOIN
    // mappedBy 속성 = "bookReviewInfo" : book_riview_info 테이블에 book_id가 사라짐, 즉, 이 속성은 해당 테이블이 연관키를 갖지 않게 하는 것
    @OneToOne(optional = false)
    private Book book;

    private float averageReviewScore;   // 자료형 타입 Float으로 선언하지 않은 이유는 null을 허용하지 않고 기본값 0.0을 받기 위해서 이따.

    private int reviewCount;    // 자료형 타입 Integer으로 선언하지 않은 이유는 null을 허용하지 않고 기본값 0을 받기 위해서 이따.
}
