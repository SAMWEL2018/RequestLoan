package com.example.requestloan;

import com.example.requestloan.model.RequestLoan;
import com.example.requestloan.service.RequestLoanServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class RequestLoanApplicationTests {

    private final RequestLoanServiceImpl requestLoanService;

    @Autowired
    public RequestLoanApplicationTests(RequestLoanServiceImpl requestLoanService) {
        this.requestLoanService = requestLoanService;
    }

    @Test
    void contextLoads() {
    }


    @Test
    void requestLoan() {
        RequestLoan requestLoanData = RequestLoan.builder()
                .phoneNumber("0712321806")
                .loanAmount(1000.0)
                .build();
        requestLoanService.loanRequest(requestLoanData).subscribe(r -> {
                    try {
                        log.info("loan process response {}", new ObjectMapper().writeValueAsString(r));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                },
                err -> log.error("And Error Occurred:: " + err.getMessage()),
                () -> log.info("Loan process complete")
        );
    }

}
