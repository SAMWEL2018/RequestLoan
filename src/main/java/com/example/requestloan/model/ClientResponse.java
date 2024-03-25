package com.example.requestloan.model;

import lombok.*;

/**
 * @author samwel.wafula
 * Created on 16/03/2024
 * Time 08:58
 * Project RequestLoan
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
    private String responseCode;
    private String responseDescription;
}
