package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 맨위에 readOnly 속성때문에 따로 걸어줘야함
    // -> 왜 ? 저장하는 로직이기 때문에 트랜잭션처리가 필요하니까
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, UpdateItemDTO itemDTO) {

        // item을 기반으로 실제 DB에 있는 영속 상태 엔티티를 찾아온다.
        Item findItem = itemRepository.findOne(itemId);
        // param == 준영속 상태 엔티티
        // 영속 상태 엔티티 안에 준영속 상태 엔티티를 setting 하면
        // @Transactional 에 의해서 commit 이 된다.
        // flush(); 날리면서
        // findItem은 영속 상태이기 때문에 JPA가 변경감지를해서 알아서 update문을 날려준다.

        /*
        영속성 컨텍스트에서 엔티티를 다시 조회한 후에 데이터를 수정하는 방법
        트랜잭션 안에서 엔티티를 다시 조회, 변경할 값 선택 트랜잭션 커밋 시점에 변경 감지(Dirty Checking)이 동작해서 데이터베이스에 UPDATE SQL 실행
        * */

        // 영속 상태 엔티티 setting
        // setter 를  사용하지 않고 아래방식처럼 setter 없이 엔티티에서 바로 추적할 수 있는 메서드를 만들어라.
        // findItem.change(itemDTO.getName(),itemDTO.getPrice(), itemDTO.getStockQuantity());

        findItem.setName(itemDTO.getName());
        findItem.setPrice(itemDTO.getPrice());
        findItem.setStockQuantity(itemDTO.getStockQuantity());
        // ...update
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }


}
