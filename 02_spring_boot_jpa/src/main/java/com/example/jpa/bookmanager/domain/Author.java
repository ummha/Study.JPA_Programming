package com.example.jpa.bookmanager.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Author extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String country;

    @OneToMany
    @JoinColumn(name = "author_id")
    @ToString.Exclude
    private List<BookAndAuthor> bookAndAuthors = new ArrayList<>();

    public void addBookAndAuthors(BookAndAuthor... bookAndAuthors) {
        if (bookAndAuthors != null) {
            this.bookAndAuthors.addAll(Arrays.asList(bookAndAuthors));
        }
    }

//    @ManyToMany
//    @ToString.Exclude
//    private List<Book> books = new ArrayList<>();

//    public void addBooks(Book... books) {
//        if (books != null) {
//            this.books.addAll(Arrays.asList(books));
//        }
//    }
}
