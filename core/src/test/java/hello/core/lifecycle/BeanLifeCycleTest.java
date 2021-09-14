package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    /* 스프링 빈의 간단한 라이프 사이클
        객체 생성 -> 의존관계 주입
        스프링 빈은 객체를 생성하고, 의존관계 주입이 다 끝난 다음에야 필요한 데이터를 사용할 수 있는 준비가 완료된다.
        따라서 초기화 작업은 의존관계 주입이 모두 완료되고 난 다음에 호출해야 한다.
        그런데 개발자가 의존관계 주입이 모두 완료된 시점을 어떻게 알 수 있을까?
        스프링은 의존관계 주입이 완료되면 스프링 빈에게 콜백 메서드를 통해서 초기화 시점을 알려주는 다양한 기능을 제공한다.
        또한 스프링은 스프링 컨테이너가 종료되기 직전에 소멸 콜백을 준다. 따라서 안전하게 종료 작업을 진행할 수 있다.
    */

    /*  스프링 빈의 이벤트 라이프 사이클
        스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료

        초기화 콜백: 빈이 생성되고, 빈의 의존관계가 주입이 완료된 후 호출
        소멸전 콜백: 빈이 소멸되기 직전에 호출

        * 객체의 생성과 초기화를 분리하자
        - 생성자는 필수정보(파라미터)를 받고, 메모리를 할당해서 객체를 생성하는 책임을 가진다.
        초기화는 이렇게 생성된 값들을 활용해서 외부 커넥션을 연결하는등의 무거운 동작을 수행한다.

     */
    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close(); //스프링 컨테이너를 종료, ConfigurableApplicationContext 필요
    }

    @Configuration
    static class LifeCycleConfig {

        /*  설정 정보 사용 특징
            - 메서드 이름을 자유롭게 줄 수 있다.
            - 스프링 빈이 스프링 코드에 의존하지 않는다.
            - 코드가 아니라 설정 정보를 사용하기 때문에 고칠 수 없는 외부 라이브러리 코드에도
            초기화, 종료 메서드를 적용할 수 있다.
         */

        // 빈 등록 초기화, 소멸 메서드 지정
        // @Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }

}
