package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;


    public void save(Item item) {

        // 아이템은 JPA에 저장할때까지 id값이 없다. id값이 없다는건 새로 생성하는 객체이다.
        if (item.getId() == null) { // 신규등록
            em.persist(item);
        }else { // 이미 id값이 있다면 update

            // 병합은 준영속 상태의 엔티티를 영속 상태로 변경할 때 사용하는 기능이다.
            // merge :
            em.merge(item);
            /*
            *** 병합시 동작 방식을 간단히 정리 ***
            1. 준영속 엔티티의 식별자 값으로 영속 엔티티를 조회한다.
            2. 영속 엔티티의 값을 준영속 엔티티의 값으로 모두 교체한다.(병합한다.)
            3. 트랜잭션 커밋 시점에 변경 감지 기능이 동작해서 데이터베이스에 UPDATE SQL이 실행
            *
            * 주의: 변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있지만, 병합을 사용하면 모든 속성이변경된다.
            * 병합시 값이 없으면 null 로 업데이트 할 위험도 있다. (병합은 모든 필드를 교체한다.)
            *
            * 조금 귀찮더라도 변경감지를 써야한다. merge ? 쓰지마라

            */
        }



    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
