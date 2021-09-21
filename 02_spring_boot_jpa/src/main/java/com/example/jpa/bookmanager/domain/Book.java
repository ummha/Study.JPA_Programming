package com.example.jpa.bookmanager.domain;

import com.example.jpa.bookmanager.domain.listener.Auditable;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(
        name="BOOK_SEQ_GEN", //시퀀스 제너레이터 이름
        sequenceName="BOOK_SEQ", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
)
public class Book extends BaseEntity implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_SEQ_GEN")
    private Long id;

    private String name;

    private String author;
}
