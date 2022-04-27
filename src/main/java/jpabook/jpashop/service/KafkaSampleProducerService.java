package jpabook.jpashop.service;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.web.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KafkaSampleProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MemberRepository memberRepository;

    @Transactional
    public void sendMessage(MemberForm member) {
        // ide console
        System.out.println("send message : " + member.getName());
        // kafka send message
        this.kafkaTemplate.send("boseok","sada" , member.getName() + "님이 회원가입 하셨습니다.");

        // set member
        Address address = new Address(member.getCity(), member.getStreet(), member.getZipCode());
        Member insertMember = new Member();
        insertMember.setName(member.getName());
        insertMember.setAddress(address);
        memberRepository.save(insertMember);
    }

}
