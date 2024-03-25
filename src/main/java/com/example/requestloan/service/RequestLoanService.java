package com.example.requestloan.service;

import com.example.requestloan.model.ClientResponse;
import com.example.requestloan.model.RequestLoan;
import reactor.core.publisher.Mono;

/**
 * @author samwel.wafula
 * Created on 16/03/2024
 * Time 08:57
 * Project RequestLoan
 */
public interface RequestLoanService {

    Mono<ClientResponse> loanRequest(RequestLoan requestLoan);


}
