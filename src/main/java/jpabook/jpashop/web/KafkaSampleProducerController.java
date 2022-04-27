package jpabook.jpashop.web;

import jpabook.jpashop.service.KafkaSampleProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class KafkaSampleProducerController {

    private final KafkaSampleProducerService kafkaSampleProducerService;
    // postMapping
    // Postman에서 localhost:8080/sendMessage
    /*
    {
        "name" : "name",
        "city" : "city",
        "street" : "street",
        "zipCode" : "zipCode"
    }
}*/
   @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody @Valid MemberForm member) {
        kafkaSampleProducerService.sendMessage(member);
    }
}
