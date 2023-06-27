package jpaStudy.start;

import jpaStudy.start.domain.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class IdentityTest {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaStudy");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Teacher teacherA = new Teacher();
        teacherA.setName("teacherA");
        em.persist(teacherA);

        Teacher teacherB = new Teacher();
        teacherB.setName("teacherB");

        teacherA.setName("changedTeacher");

        em.persist(teacherB);

        transaction.commit();
    }
}