package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    /*
        - 생성자 주입을 하면 의존관계가 누락되면 NullPointException이 발생한다.
        => 컴파일 오류가 발생해서 어떤 값을 주입해야 하는지 알 수 있다.

        - 생성자 주입을 사용하면 필드에 final 키워드를 사용할 수 있다. 생성자에서 혹시라도 값이
        결정되지 않은 오류를 컴파일 시점에서 막아준다.

        - 생성자 주입을 사용하면 프레임워크에 의존하지 않고, 순수한 자바 언어의 특징으로 테스트 가능
        - 필드 주입 사용은 x
     */
    @Test
    void craeteOrder() {
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}