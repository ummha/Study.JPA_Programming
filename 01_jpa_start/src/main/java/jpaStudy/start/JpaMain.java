package jpaStudy.start;

import jpaStudy.start.domain.Member;
import jpaStudy.start.domain.Student;
import jpaStudy.start.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        //        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaStudy");
        //        EntityManager em = emf.createEntityManager();
        //        EntityTransaction transaction = em.getTransaction();
        //        transaction.begin();
        //        Team team1 = new Team("team1", "1팀");
        //        Team team2 = new Team("team2", "2팀");
        //        em.persist(team1);
        //        em.persist(team2);
        //        Member memberA = new Member("memberA", "A회원");
        //        memberA.setTeam(team1);
        //        em.persist(memberA);
        //        Member memberB = new Member("memberB", "B회원");
        //        memberB.setTeam(team1);
        //        em.persist(memberB);
        //        memberB.setTeam(team2);
        //        transaction.commit();
    }
}