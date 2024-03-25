package com.example.requestloan.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

/**
 * @author samwel.wafula
 * Created on 16/03/2024
 * Time 08:52
 * Project RequestLoan
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "tbl_request_loan")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_request_id")
    private int loanRequestId;
    @Column(name = "phone_number")
    private String phoneNumber;
    private double loanAmount;
    @Column(name = "is_loan_req_sent")
    private boolean isLoanReqSent=false;
    @Column(name = "response_desc")
    private String responseDesc;
}
