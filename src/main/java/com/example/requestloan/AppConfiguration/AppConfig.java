package com.example.requestloan.AppConfiguration;

//import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
//import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

/**
 * @author samwel.wafula
 * Created on 16/03/2024
 * Time 09:01
 * Project RequestLoan
 */
@Getter
@Setter
@Configuration
public class AppConfig {

    @Value("${app.check_loan_balance_service_url_request_loan}")
    private String request_loan_from_check_balanceAndLimitService;

    @Bean
    WebClient webClient() {
        HttpClient httpClient = HttpClient.create().responseTimeout(Duration.ofMillis(5000));
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

    }


}
