package com.example.ioc.design_pattern.inwork;

public class PayRequest {

    private String payType;

    public PayRequest(String payType) {
        this.payType = payType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}