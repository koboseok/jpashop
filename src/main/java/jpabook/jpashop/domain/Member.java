package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded // (임베디드타입)내장타입 포함 어노테이션
    private Address address;
    // 기본
    // ex) persist(orderItemA)
    //    persist(orderItemC)
    //    persist(orderItemC)
    //    persist(order)
    // 엔티티당 각각 persist를 호출해야한다.
    // cascade = CascadeType.ALL 를 두면
    // persist(order)
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}