package jpaStudy.start;

import jpaStudy.start.domain.Locker;
import jpaStudy.start.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class OneToOneMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaStudy");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Locker locker1 = new Locker();
        locker1.setNumber(1);
        em.persist(locker1);

        Locker locker2 = new Locker();
        locker2.setNumber(2);
        em.persist(locker2);

//        Member memberA = new Member("memberA", "A회원");
//        memberA.setLocker(locker1);
//        em.persist(memberA);

//        Member memberB = new Member("memberB", "B회원");
//        memberB.setLocker(locker2);
//        em.persist(memberB);

        transaction.commit();
    }
}