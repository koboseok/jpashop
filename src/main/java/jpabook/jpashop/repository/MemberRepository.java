package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository // spring bean 등록
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    // db 저장
    public void save(Member member){
        em.persist(member);
    }


    /*
   @PersistenceContext // 스프링부트가 엔티티매니저가 주입을 해준다. 따로 설정 X
   private EntityManager em;
   @PersistenceUnit
   private EntityManagerFactory emf;
   */
    public Member findOne(Long id){ // 단건 조회
        return em.find(Member.class, id); // (type, pk)
    }

    public List<Member> findAll() {
        // sql은 테이블을 대상으로 쿼리를 날리는데 jpql은 entity 대상으로 쿼리를 날린다.
        return em.createQuery("select m from Member m", Member.class).getResultList();

    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }



}
