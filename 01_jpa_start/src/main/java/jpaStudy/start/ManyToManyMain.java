package jpaStudy.start;

import jpaStudy.start.domain.Member;
import jpaStudy.start.domain.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ManyToManyMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaStudy");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Product productA = new Product("productA", "A상품");
        //        Product productB = new Product("productB", "B상품");
        em.persist(productA);

        Member member = new Member("memberA", "A회원");
        member.getProducts()
              .add(productA);
        em.persist(member);
        em.flush();
        em.clear();

        Member foundMember = em.find(Member.class, member.getId());
        foundMember.getProducts()
                   .forEach(System.out::println);

        transaction.commit();
    }
}