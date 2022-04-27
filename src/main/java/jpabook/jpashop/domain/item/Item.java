package jpabook.jpashop.domain.item;

import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import jpabook.jpashop.domain.Category;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//SINGLE_TABLE - 한테이블에 모두 저장
//TABLE_PER_CLASS - book, movie, album 3개의 테이블로
//JOINED - 가장 정규화된 스타일
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item { // 추상 클래스
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;// 재고수량

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();


    //== 비지니스 로직 ==//

    // 재고를 늘리고 줄이고 로직 추가
    // 보통 도메인 주도설계라고 할때 엔티티자체가 해결할 수 있는 것들을 엔티티 안에 비지니스 로직을 넣는게 좋다
    // 가장 응집도가 있다.
    // stockQuantity 를 변경해야할 일이 있으면 밑에 비지니스 로직으로 변경해야한다. setter 로 변경하는건 안좋아.
    // == 객체지향적이다 !!!

    /**
     * 재고(stock) 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * 재고(stock) 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}