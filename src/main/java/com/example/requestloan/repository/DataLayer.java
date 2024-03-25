package com.example.requestloan.repository;

import com.example.requestloan.model.RequestLoan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author samwel.wafula
 * Created on 16/03/2024
 * Time 09:31
 * Project RequestLoan
 */
@Repository
public class DataLayer {

    private final RequestLoanRepository requestLoanRepository;

    @Autowired
    public DataLayer(RequestLoanRepository requestLoanRepository) {
        this.requestLoanRepository = requestLoanRepository;
    }

    public void saveLoanRequest(RequestLoan requestLoan) {
        requestLoanRepository.save(requestLoan);
    }

    public void updateLoanAfterProcessing(boolean isLoanReqSent, String responseDesc, String phoneNumber) {
        requestLoanRepository.updateLoanAfterProcessing(isLoanReqSent, responseDesc, phoneNumber);

    }
}
