package jpaStudy.start.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MEMBER")
public class Member {

    public Member() {
    }

    public Member(String id, String username) {
        this.id = id;
        this.username = username;
        //        this.team = team;
    }

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    //    @OneToOne
    //    @JoinColumn(name = "LOCKER_ID", unique = true)
    //    private Locker locker;

    //    @ManyToOne
    //    @JoinColumn(name = "TEAM_ID")
    //    private Team team;

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT",
               joinColumns = @JoinColumn(name = "MEMBER_ID"),
               inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    List<Product> products = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //    public Team getTeam() {
    //        return team;
    //    }

    //    public void setTeam(Team team) {
    //        if(this.team != null) {
    //            this.team.getMembers()
    //                     .remove(this);
    //        }
    //        this.team = team;
    //        if(!team.getMembers().contains(this)) {
    //            team.addMember(this);
    //        }
    //    }

    //    public Locker getLocker() {
    //        return locker;
    //    }

    //    public void setLocker(Locker locker) {
    //        this.locker = locker;
    //    }


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Member{" + "id='" + id + '\'' + ", username='" + username + '\'' + ", products=" + products + '}';
    }
}