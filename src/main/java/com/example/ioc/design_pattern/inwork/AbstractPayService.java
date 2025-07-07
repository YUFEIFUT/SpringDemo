package com.example.ioc.design_pattern.inwork;

public abstract class AbstractPayService implements PayService {

    @Override
    public void pay(PayRequest payRequest) {
      	//前置检查
        validateRequest(payRequest);
      	//支付核心逻辑
        doPay(payRequest);
      	//后置处理
        postPay(payRequest);
    }

    public abstract void doPay(PayRequest payRequest);

    protected void postPay(PayRequest payRequest) {
        //支付成功的后置处理
    }

    protected void validateRequest(PayRequest payRequest) {
        //参数检查
    }
}