package jpaStudy.start;

import jpaStudy.start.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.Timestamp;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        System.out.println("RUN");
        // [엔티티 매니저 팩토리] - 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaStudy");

        // [엔티티 매니저] - 생성
        EntityManager em = emf.createEntityManager();

        // [트랜잭션] - 획득
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); // 트랜잭션 시작
            logic(em);  // 비지니스 로직 실행
            tx.commit();    // 트랜잭션 커밋
        }catch(Exception e){
            tx.rollback();  // 트랜잭션 롤백
        }
        emf.close();    // 엔티티 매니저 팩토리 종료
        System.out.println("FINISH");
    }

    /**
     * 비지니스 로직
     * @param em
     */
    private static void logic(EntityManager em) {
        String id = "id_" + System.currentTimeMillis();
        Member member = new Member();
        member.setId(id);
        member.setUsername("민서2");
        member.setAge(25);

        // 등록
        em.persist(member);

        // 수정
        member.setUsername("Minseo");

        // 한 건 조회 (JPQL)
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember>>" + findMember);

        // 목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members>>");
        members.stream().forEach(System.out::println);

        // 삭제
        em.remove(member);
    }
}
