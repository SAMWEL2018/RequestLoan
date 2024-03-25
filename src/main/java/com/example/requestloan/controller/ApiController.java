package com.example.requestloan.controller;

import com.example.requestloan.model.ClientResponse;
import com.example.requestloan.model.RequestLoan;
import com.example.requestloan.service.RequestLoanService;
import com.example.requestloan.service.RequestLoanServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @author samwel.wafula
 * Created on 16/03/2024
 * Time 08:56
 * Project RequestLoan
 */
@Controller
@Slf4j
public class ApiController {
    private final RequestLoanServiceImpl requestLoanService;
    public static final String REQUEST_LOAN_SERVICE = "requestLoanService";
    private int attempt = 1;


    @Autowired
    public ApiController(RequestLoanServiceImpl requestLoanService) {
        this.requestLoanService = requestLoanService;
    }

    /**
     * ------------------------------------------------------------------------------------------
     * -------------------------------*** REQUEST LOAN SERVICE ENDPOINT ***-------------------------------------------
     * ------------------------------------------------------------------------------------------
     */


    @RequestMapping(value = "/requestLoanByCustomer", method = RequestMethod.POST)
    @Retry(name = "requestLoanService", fallbackMethod = "fallbackMethod")
    //@CircuitBreaker(name = REQUEST_LOAN_SERVICE, fallbackMethod = "fallbackMethod")
    public ResponseEntity<?> loanRequest(@RequestBody RequestLoan requestLoan) {
        // System.out.println("retry method called " + attempt++ + " times " + " at " + new Date());
        if (requestLoan != null) {
            Mono<ClientResponse> res = requestLoanService.loanRequest(requestLoan);
            res.subscribe(r -> {
                        try {
                            log.info("loan process response {}", new ObjectMapper().writeValueAsString(r));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    err -> log.error("And Error Occurred:: " + err.getMessage()),
                    () -> log.info("Loan process complete")
            );
        } else {
            return ResponseEntity.status(400).body(ClientResponse.builder().responseCode("400").responseDescription("Empty Object").build());
        }
        return ResponseEntity.status(200).body(ClientResponse.builder().responseCode("200").responseDescription("Loan Request in progress").build());
    }

    /**
     * ------------------------------------------------------------------------------------------
     * --*** FALLBACK METHOD CALLED WHEN ANOTHER MICRO-SERVICE BEING CALLED IS UNAVAILABLE ***---
     * ------------------------------------------------------------------------------------------
     */
    public ResponseEntity<?> fallbackMethod(Exception e) {
        /// Email notification service can be executed here no notify Tech Ops
        return ResponseEntity.status(503).body(ClientResponse.builder()
                .responseCode("503")
                .responseDescription("THE SERVICE IS CURRENTLY DOWN, PLEASE BE PATIENT AS WE RESOLVE THE ISSUE")
                .build());
    }
}
