package com.haiph.common.enums.status.menuService.payment;

public interface PaymentVnPay {
    static final String IP_DEFAULT="127.0.0.1";
    static final String VERSION = "2.1.0";
    static final String COMMAND = "pay";
    static final String ORDER_TYPE= "ordertype";
    static final String TMN_CODE ="UCBJ3UTA";
    static final String HASH_SECREST ="EONNWQQPLATCZPXJNBCLVPYVLNZHTDAN";
    static final String RETURN_URL = "http://localhost:8000/api/payment/thanks";
    static final String PAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    static final String LOCALE_DEFAULT = "vn";
    static final String API_URL = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";
}
