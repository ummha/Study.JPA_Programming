package com.example.jpa.bookmanager.domain;

import com.example.jpa.bookmanager.domain.listener.Auditable;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private Long authorId;

//    @Column(name = "publisher_id", insertable = false, updatable = false)
//    private Long publisherId;

    // mappedBy 속성 = "book" : book 테이블에 book_review_info_id가 사라짐, 즉, 이 속성은 해당 테이블이 연관키를 갖지 않게 하는 것
    // 테스트 코드 실행시 toString 메소드 순환참조 오류 발생(StackOverflowError) : 그렇기 때문에 relational은 특별한 상황이 아니면 단방향으로 처리한다. 또는 toString 메소드를 제외하는 작업이 필요하다.
    @OneToOne(mappedBy = "book")
    @ToString.Exclude
    private BookReviewInfo bookReviewInfo;

    @OneToMany
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne
    @ToString.Exclude
    private Publisher publisher;
}
