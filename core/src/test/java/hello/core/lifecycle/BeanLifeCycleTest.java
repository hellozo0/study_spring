package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

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
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {

        /*  설정 정보 사용 특징
            - 메서드 이름을 자유롭게 줄 수 있다.
            - 스프링 빈이 스프링 코드에 의존하지 않는다.
            - 코드가 아니라 설정 정보를 사용하기 때문에 고칠 수 없는 외부 라이브러리 코드에도
            초기화, 종료 메서드를 적용할 수 있다.
         */

//        @Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }

}
