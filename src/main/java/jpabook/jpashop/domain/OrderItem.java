package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jpabook.jpashop.domain.item.Item;
import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item; //주문 상품

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order; //주문

    private int orderPrice; //주문 가격

    private int count; //주문 수량

    /*
    @NoArgsConstructor(access = AccessLevel.PROTECTED) 와 같은 의미 lombok
    protected OrderItem(){
        // new 연산자를 이용한 생성을 막는다.
        // 왜? 아래의 createOrderItem 메서드드 맷을 유지하기 위해. 유지보수의 편의성, 코드의 단일화
        // 항상 코드를 제약하는 스타일로 작성해야 좋은 설계와 유지보수로 끌고갈 수 있다.
    }
    */
    //== 생성 메서드 ==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //== 비지니스 로직 ==/
    public void cancel() {
        // 재고수량 복구
        getItem().addStock(count);

    }
    //== 조회 로직 ==/

    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}