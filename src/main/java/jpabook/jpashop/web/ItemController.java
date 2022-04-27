package jpabook.jpashop.web;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.UpdateItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {

        model.addAttribute("form", new BookForm());

        return "items/createItemForm";

    }

    @PostMapping("/items/new")
    public String create(BookForm form) {

        itemService.saveItem(Book.createBook(form));
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {

        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {

        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long itemId, @ModelAttribute("form") UpdateItemDTO itemDTO) {
        /*
         * 준영속 엔티티 == JPA가 관리하지 않는다.
         * 영속성 컨텍스트가 더는 관리하지 않는 엔티티를 말한다. 즉,(쉽게말해) 디비에 저장된 데이터(엔티티)
         * 왜 ? 데이터가 저장되기 전까지는 영속성 컨텍스트 안에서 관리되다가 DB로 들어가거나 rollback 되면 영속성 컨텍스트에서 벗어나니
         * rollback 은  id값 즉, 고유값(식별자)이 없음으로 다시 영속성컨텍스트로 들어오지만 id값이 생성된 (DB에 저장된) 엔티티는 준영속 엔티티이다.
         *
         * 준영속 엔티티는 JPA가 관리를 하지 않기때문에 변경감지가 일어나지 않는다 !!
         * 준영속 엔티티를 수정하는 방법은 2가지
         * - 변경 감지 기능 사용
         * - 병함(merge) 사용
         *  엔티티를 변경할 때는 항상 변경 감지를 사용하세요
            컨트롤러에서 어설프게 엔티티를 생성하지 마세요.
            트랜잭션이 있는 서비스 계층에 식별자( id )와 변경할 데이터를 명확하게 전달하세요.(파라미터 or dto)
            트랜잭션이 있는 서비스 계층에서 영속 상태의 엔티티를 조회하고, 엔티티의 데이터를 직접 변경하세요.
            트랜잭션 커밋 시점에 변경 감지가 실행됩니다.
         * */
//        Book book = new Book();
//        book.setId(form.getId());
//        book.setName(form.getName());
//        book.setPrice(form.getPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn());

        itemService.updateItem(itemId, itemDTO);

        return "redirect:/items";

    }
}
