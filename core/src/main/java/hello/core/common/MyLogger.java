package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)   // request 스코프로 지정. 이 빈은 HTTP 요청 당 하나씩 생성 HTTP 요청이 끝나는 시점에 소멸
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }

    @PostConstruct  //이 빈이 생성되는 시점에 UUID 생성해서 저장. 이 빈은 HTTP 요청 당 하나씩 생성되므로, uuid를 저장해두면 다른 HTTP요청과 구분 가능
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create: " + this);
    }

    @PreDestroy // 이 빈이 소멸되는 시점에 `@PreDestroy`를 사용해서 종료 메세지를 남긴다.
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close : " + this);
    }
}