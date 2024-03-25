package com.example.requestloan.repository;

import com.example.requestloan.model.RequestLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author samwel.wafula
 * Created on 16/03/2024
 * Time 09:30
 * Project RequestLoan
 */
@Repository
public interface RequestLoanRepository extends JpaRepository<RequestLoan, Integer> {

    @Query(nativeQuery = true, value = "update tbl_request_loan set is_loan_req_sent=:isLoanReqSent,response_desc=:responseDesc where phone_number=:phone_number ")
    @Transactional
    @Modifying
    void updateLoanAfterProcessing(@Param("isLoanReqSent") boolean isLoanReqSent, @Param("responseDesc") String responseDesc, @Param("phone_number") String phoneNumber);
}
