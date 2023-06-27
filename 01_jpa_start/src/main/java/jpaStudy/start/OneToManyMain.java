package jpaStudy.start;

import jpaStudy.start.domain.Member;
import jpaStudy.start.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class OneToManyMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaStudy");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Member memberA = new Member("memberA", "A회원");
        Member memberB = new Member("memberB", "B회원");

        Team team1 = new Team("team1", "1팀");
        team1.getMembers()
             .add(memberA);
        team1.getMembers()
             .add(memberB);

        em.persist(memberA);
        em.persist(memberB);
        em.persist(team1);

        transaction.commit();
    }
}