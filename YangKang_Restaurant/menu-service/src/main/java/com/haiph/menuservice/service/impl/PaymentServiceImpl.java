package com.haiph.menuservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.dto.response.ResponseBody;
import com.haiph.common.enums.status.menuService.payment.PaymentVnPay;
import com.haiph.common.exception.CommonException;
import com.haiph.menuservice.config.PaymentConfig;
import com.haiph.menuservice.dto.request.PaymentRequest;
import com.haiph.menuservice.dto.response.DiscountResponse;
import com.haiph.menuservice.dto.response.OrderResponse;
import com.haiph.menuservice.dto.response.PaymentResponse;
import com.haiph.menuservice.dto.response.restApi.RestaurantFormResponse;
import com.haiph.menuservice.entity.Payment;
import com.haiph.menuservice.feignClient.OrderFeignClient;
import com.haiph.menuservice.feignClient.RestaurantController;
import com.haiph.menuservice.repository.PaymentRepository;
import com.haiph.menuservice.service.DiscountService;
import com.haiph.menuservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class PaymentServiceImpl implements com.haiph.menuservice.service.PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private DiscountService discountService;
    @Autowired
    private RestaurantController restaurantController;
    @Autowired
    private OrderFeignClient orderFeignClient;


    private OrderResponse findListIdOrder(Integer id) {
        OrderResponse responses = orderService.findById(id);
        if (responses == null ) {
            throw new CommonException(Response.NOT_FOUND, "Cannot find order");
        }
        return responses;
    }

    private String findPersonCodeListIdOrder(Integer ids) {
      OrderResponse responses = orderService.findById(ids);
        if (responses == null) {
            throw new CommonException(Response.NOT_FOUND, "Cannot find order");
        }
        return responses.getPersonResponses().getUserCode();
    }

    private Double findTotalPrice(Integer ids) {
        OrderResponse responses = findListIdOrder(ids);
        return responses.getTotalPrice();
    }

    private DiscountResponse findByDiscountId(Integer id) {
        DiscountResponse response = discountService.findById(id);
        if (response != null) {
            return response;
        } else throw new CommonException(Response.NOT_FOUND, "Cannot find Discount Id: " + id);
    }

    @Override
    public PaymentResponse findById(Integer id) {
        Payment payment = paymentRepository.findById(id).
                orElseThrow((() -> {
                    throw new CommonException(Response.NOT_FOUND, "Canot find PaymentId: " + id);
                }));
        PaymentResponse response =
                PaymentResponse
                        .build(
                                payment.getId(),
                                payment.getPaymentCode(),
                                findListIdOrder(payment.getOrderId()),
                                findByDiscountId(payment.getDiscountId()),
                                findTotalPrice(payment.getOrderId()),
                                payment.getCustomerPay(),
                                payment.getRemain(),
                                payment.getStatus(),
                                payment.getScore(),
                                payment.getCreateDate());
        return response;
    }

    @Override
    public PaymentResponse findByPaymentCode(String paymentCode) {
        Payment payment = paymentRepository.findByPaymentCode(paymentCode);
        if (payment == null) {
            throw new CommonException(Response.NOT_FOUND, "Canot find paymentCode: " + paymentCode);

        }
        PaymentResponse response =
                PaymentResponse
                        .build(
                                payment.getId(),
                                payment.getPaymentCode(),
                                findListIdOrder(payment.getOrderId()),
                                findByDiscountId(payment.getDiscountId()),
                                findTotalPrice(payment.getOrderId()),
                                payment.getCustomerPay(),
                                payment.getRemain(),
                                payment.getStatus(),
                                payment.getScore(),
                                payment.getCreateDate());
        return response;
    }

    @Override
    public boolean checkExpired(LocalDate createDate, LocalDateTime startDate, LocalDateTime endDate) {
        List<Payment> payments = paymentRepository.findPayMentByDiscount(createDate, startDate, endDate);
        if (!payments.isEmpty()) {
            return true;
        }
        return false;
    }

    private Double totalRemain(Double totalPrice, Double customerPay, DiscountResponse response, LocalDate create) {
        Double remain;
        if (checkExpired(create, response.getStartDate(), response.getEndDate())) {
            log.info("Đã Áp Dụng Mã Giảm Giá");
            remain = customerPay - (totalPrice- ((totalPrice * response.getPercentDiscount().getPercent()) / 100));
        } else {
            log.info("Mã Giảm Giá Hết Hiệu Lực");
            remain = customerPay - totalPrice;
        }
        return remain;
    }

    @Override
    public List<PaymentResponse> findAllByOrderId(Integer orderId) {
        List<Payment> payments = paymentRepository.findAllByOrderId(orderId);
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentResponse response =
                    PaymentResponse
                            .build(
                                    payment.getId(),
                                    payment.getPaymentCode(),
                                    findListIdOrder(payment.getOrderId()),
                                    findByDiscountId(payment.getDiscountId()),
                                    findTotalPrice(payment.getOrderId()),
                                    payment.getCustomerPay(),
                                    payment.getRemain(),
                                    payment.getStatus(),
                                    payment.getScore(),
                                    payment.getCreateDate());
            paymentResponses.add(response);
        }
        return paymentResponses;

    }

    @Override
    public List<PaymentResponse> findListId(List<Integer> ids) {
        List<Payment> payments = paymentRepository.findListId(ids);
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentResponse response =
                    PaymentResponse
                            .build(
                                    payment.getId(),
                                    payment.getPaymentCode(),
                                    findListIdOrder(payment.getOrderId()),
                                    findByDiscountId(payment.getDiscountId()),
                                    findTotalPrice(payment.getOrderId()),
                                    payment.getCustomerPay(),
                                    payment.getRemain(),
                                    payment.getStatus(),
                                    payment.getScore(),
                                    payment.getCreateDate());
            paymentResponses.add(response);
        }
        return paymentResponses;

    }

    @Override
    public Page<PaymentResponse> findAllPage(Pageable pageable) {
        Page<Payment> page = paymentRepository.findAll(pageable);
        List<Payment> payments = page.getContent();
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentResponse response =
                    PaymentResponse
                            .build(
                                    payment.getId(),
                                    payment.getPaymentCode(),
                                    findListIdOrder(payment.getOrderId()),
                                    findByDiscountId(payment.getDiscountId()),
                                    findTotalPrice(payment.getOrderId()),
                                    payment.getCustomerPay(),
                                    payment.getRemain(),
                                    payment.getStatus(),
                                    payment.getScore(),
                                    payment.getCreateDate());
            paymentResponses.add(response);
        }
        return new PageImpl<>(paymentResponses, pageable, page.getTotalElements());
    }

    private String genPaymentCode(LocalDate createDate, String personCode) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String createDateStr = createDate.format(formatter).replace("-", "");
        String genPaymentCode = personCode + "-" + createDateStr;
        System.out.println("genPaymentCode = " + genPaymentCode);
        return genPaymentCode;
    }

    private String checkExistPaymentCode(LocalDate createDate, String personCode, String paymentCode) {
        paymentCode = genPaymentCode(createDate, personCode);
        Integer number = 0;
        while (findByPaymentCode(paymentCode) != null) {
            number++;
            paymentCode = paymentCode + "-" + number;
            findByPaymentCode(paymentCode);
        }
        return paymentCode;
    }

    private Double score(OrderResponse orderResponse) {
        Double maxScore =0d;
        List<PaymentResponse> responses = findAllByOrderId(orderResponse.getId());
        for (PaymentResponse respons : responses) {
            maxScore = respons.getScore();
        }
         maxScore += orderResponse.getTotalPrice();
        if (!responses.isEmpty()) {
            maxScore += 0d;
        }
        return maxScore;
    }


    @Override
    public String create(PaymentRequest request) {

        Payment payment = new Payment(
                genPaymentCode(LocalDate.now(), findPersonCodeListIdOrder(request.getOrderIds())),
                request.getOrderIds(),
                request.getDiscountId(),
                findTotalPrice(request.getOrderIds()),
                request.getCustomerPay(),
                totalRemain(findTotalPrice(request.getOrderIds()), request.getCustomerPay(), findByDiscountId(request.getDiscountId()), LocalDate.now()),
                request.getStatus(),
                score(findListIdOrder(request.getOrderIds())),
                LocalDateTime.now()
        );
        paymentRepository.save(payment);
        restaurantController.updateReady(findFormIdWithListIdOrder(request.getOrderIds()));
        return "create succes";
    }

    private List<Integer> findFormIdWithListIdOrder(Integer ids) {
        OrderResponse responses = findListIdOrder(ids);
        List<Integer> listFormCode = new ArrayList<>();

            List<RestaurantFormResponse> list = responses.getForms();
            for (RestaurantFormResponse response : list) {
                listFormCode.add(response.getId());
            }

        return listFormCode;
    }
    


    @Override
    public String update(Integer id, PaymentRequest request) {
        PaymentResponse response = findById(id);
        if (response != null) {
            Payment payment =Payment.build(
                    response.getId(),
                    genPaymentCode(LocalDate.now(), findPersonCodeListIdOrder(request.getOrderIds())),
                    request.getOrderIds(),
                    request.getDiscountId(),
                    findTotalPrice(request.getOrderIds()),
                    request.getCustomerPay(),
                    totalRemain(findTotalPrice(request.getOrderIds()), request.getCustomerPay(), findByDiscountId(request.getDiscountId()), LocalDate.now()),
                    request.getStatus(),
                    score(findListIdOrder(request.getOrderIds())),
                    LocalDateTime.now()
            );
            paymentRepository.save(payment);
            return "update succes";
        }
        return "update fail";
    }

    @Override
    public String delete(Integer id) {
        PaymentResponse response = findById(id);
        if (response != null) {
            paymentRepository.deleteById(id);
            return "delete succes";
        }
        return "delete fail";
    }

    @Override
    public ResponseBody paymenByVnPay(Integer id) throws UnsupportedEncodingException {
        PaymentResponse response = findById(id);
        String orderType = PaymentVnPay.ORDER_TYPE;
        int amount =(int) Math.floor(response.getTotalPrice()) ;
        int amountReal = amount *100;
        String vnp_TxnRef = response.getPaymentCode();
        String vnp_IpAddr = PaymentVnPay.IP_DEFAULT;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", PaymentVnPay.VERSION);
        vnp_Params.put("vnp_Command", PaymentVnPay.COMMAND);
        vnp_Params.put("vnp_TmnCode", PaymentVnPay.TMN_CODE);
        vnp_Params.put("vnp_Amount", String.valueOf(amountReal));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", PaymentVnPay.LOCALE_DEFAULT);
        vnp_Params.put("vnp_ReturnUrl", PaymentVnPay.RETURN_URL);
        vnp_Params.put("vnp_IpAddr", PaymentVnPay.IP_DEFAULT);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentVnPay.HASH_SECREST, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PaymentVnPay.PAY_URL + "?" + queryUrl;
        ResponseBody paymentDto = new ResponseBody();
        paymentDto.setResponseCode(Response.SUCCESS.getResponseCode());
        paymentDto.setResponseMessage(Response.SUCCESS.getResponseMessage());
        paymentDto.setResponseData(paymentUrl);
        return paymentDto;
    }
}
