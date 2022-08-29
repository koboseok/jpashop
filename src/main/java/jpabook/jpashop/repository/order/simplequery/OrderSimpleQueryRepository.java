package jpabook.jpashop.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    /**
     * repository를 분리하는 이유
     * repository는 가급적 순수한 엔티티를 검색,조회(페치조인)하는 용도
     * 재사용도 되고 어떤 엔티티를 조회한다는것이 명확하다.
     * but !
     * dto로 직접 조회하는 방식은 화면단에 의존성이 있을 경우 이렇게 뽑아 쓰는게 좋다.
     * 조회 전용으로 화면에 맞춰서 쓴다.
     * 유지보수성이 좋다.
     */
    public List<OrderSimpleQueryDto> findOrderDtos(){
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)"
                        + " from Order o "
                        + " join o.member m"
                        + " join o.delivery d" , OrderSimpleQueryDto.class).getResultList();
    }

}
