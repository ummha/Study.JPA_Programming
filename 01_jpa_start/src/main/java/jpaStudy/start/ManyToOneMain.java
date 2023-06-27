package jpaStudy.start;

import jpaStudy.start.domain.Member;
import jpaStudy.start.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ManyToOneMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaStudy");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Team team = new Team("teamA", "팀A");
        em.persist(team);

        Member memberA = new Member("memberA", "회원A");
//        memberA.setTeam(team);
        em.persist(memberA);
        Member memberB = new Member("memberB", "회원B");
//        memberB.setTeam(team);
        em.persist(memberB);
        em.flush();
        em.clear();

        Member member = em.find(Member.class, "memberA");

//        System.out.println(member.getTeam().getId());
//        System.out.println(member.getTeam().getName());


        transaction.commit();
    }
}