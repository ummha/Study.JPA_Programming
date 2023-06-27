package jpaStudy.start;

import jpaStudy.start.domain.Member;
import jpaStudy.start.domain.Team;
import org.hibernate.id.enhanced.PooledOptimizer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TwoDirectionMain {

    /**
     * add로 하면 NULL 들어감
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaStudy");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Team team1 = new Team("team1", "1팀");
        em.persist(team1);
        Team team2 = new Team("team2", "2팀");
        em.persist(team2);

        Member memberA = new Member("memberA", "A회원");
//        memberA.setTeam(team1);
        em.persist(memberA);

        Member memberB = new Member("memberB", "B회원");
//        team1.addMember(memberB);
        em.persist(memberB);

//        memberA.setTeam(team2);

        transaction.commit();

        team1.getMembers().forEach(System.out::println);
        team2.getMembers().forEach(System.out::println);
    }
}