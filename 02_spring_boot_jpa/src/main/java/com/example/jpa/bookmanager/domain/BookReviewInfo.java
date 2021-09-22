package com.example.jpa.bookmanager.domain;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(
        name = "BOOK_REVIEW_INFO_SEQ_GEN",
        sequenceName = "BOOK_REVIEW_INFO_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class BookReviewInfo extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_REVIEW_INFO_SEQ_GEN")
    private Long id;

    private Long bookId;

    private float averageReviewScore;   // 자료형 타입 Float으로 선언하지 않은 이유는 null을 허용하지 않고 기본값 0.0을 받기 위해서 이따.

    private int reviewCount;    // 자료형 타입 Integer으로 선언하지 않은 이유는 null을 허용하지 않고 기본값 0을 받기 위해서 이따.
}
