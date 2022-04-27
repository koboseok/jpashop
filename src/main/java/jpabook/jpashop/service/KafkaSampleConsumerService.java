package jpabook.jpashop.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaSampleConsumerService {

    // consumer Service
    // message가 제대로 들어갔는지 ide console
    @KafkaListener(topics = "boseok", groupId = "group-id-ko")
    public void consume(String message) throws IOException {

        System.out.println("receive message : " + message);
    }
}
