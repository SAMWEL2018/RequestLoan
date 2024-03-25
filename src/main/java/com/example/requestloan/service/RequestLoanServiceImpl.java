package com.example.requestloan.service;

import com.example.requestloan.AppConfiguration.AppConfig;
import com.example.requestloan.AppConfiguration.HttpService;
import com.example.requestloan.model.ClientResponse;
import com.example.requestloan.model.RequestLoan;
import com.example.requestloan.repository.DataLayer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * @author samwel.wafula
 * Created on 16/03/2024
 * Time 08:57
 * Project RequestLoan
 */
@Service
@Slf4j
public class RequestLoanServiceImpl implements RequestLoanService {
    private final DataLayer dataLayer;
    private final HttpService httpService;
    private final AppConfig appConfig;

    @Autowired
    public RequestLoanServiceImpl(DataLayer dataLayer, HttpService httpService, AppConfig appConfig) {
        this.dataLayer = dataLayer;
        this.httpService = httpService;
        this.appConfig = appConfig;
    }

    @Transactional
    @Override
    public Mono<ClientResponse> loanRequest(RequestLoan requestLoan) {
        dataLayer.saveLoanRequest(requestLoan);
        RequestLoan request = RequestLoan.builder().loanAmount(requestLoan.getLoanAmount()).phoneNumber(requestLoan.getPhoneNumber()).build();
        JsonNode jsonNode = null;

        jsonNode = httpService.sendSyncRequestCall(HttpMethod.POST, appConfig.getRequest_loan_from_check_balanceAndLimitService(), request);

        //log.error("Error sending Loan Request to CheckLoanLimitAndBalanceService ");


        ClientResponse res = ClientResponse.builder().responseCode(jsonNode.get("responseCode").asText()).responseDescription(jsonNode.get("responseDescription").asText()).build();
        log.info(String.valueOf(res));
        dataLayer.updateLoanAfterProcessing(true, res.getResponseDescription(), request.getPhoneNumber());
        return Mono.just(res);
    }
}
