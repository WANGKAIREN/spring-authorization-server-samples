package com.ciglink.authcenter.test.client.credentials.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * @author WANGKairen
 * @since 2023-02-02 18:15:39
 **/
@Slf4j
@Service
public class ArticleJob {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient webClient;


    /**
     * 调用资源服务器任务，间隔2秒执行
     */
    @Scheduled(cron = "0/2 * * * * ? ")
    public void exchange() {

        //ResponseEntity<String> forEntity = restTemplate.getForEntity("http://127.0.0.1:8090/test", String.class);
        //log.info("调用资源服务器执行结果:{}", forEntity);

        List list = this.webClient
                .get()
                .uri("http://127.0.0.1:8090/test")
                .attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId("messaging-client-client-credentials"))
                .retrieve()
                .bodyToMono(List.class)
                .block();
        log.info("调用资源服务器执行结果：" + list);
    }
}
