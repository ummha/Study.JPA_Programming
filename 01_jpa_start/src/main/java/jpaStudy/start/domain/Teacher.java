package jpaStudy.start.domain;

import javax.persistence.*;

@Entity
@Table(name = "TEACHER")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TEACHER_SEQ_GENERATOR")
//    @SequenceGenerator(name = "TEACHER_SEQ_GENERATOR", sequenceName = "TEACHER_SEQ", initialValue = 1, allocationSize = 2)
    @TableGenerator(name = "TEACHER_SEQ_GENERATOR",
                    table = "TB_SEQUENCE",
                    pkColumnName = "SEQ_ID",
                    pkColumnValue = "TEACHER_SEQ",
                    valueColumnName = "NEXT_VAL",
                    allocationSize = 50)
    private Long id;

    @Column(name = "NAME")
    private String name;

//    @Access(AccessType.FIELD)
    public Long getId() {
        return id;
    }
    @Temporal(TemporalType.DATE)
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}