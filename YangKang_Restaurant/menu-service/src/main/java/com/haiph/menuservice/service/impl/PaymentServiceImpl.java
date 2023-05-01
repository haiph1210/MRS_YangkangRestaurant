package com.haiph.menuservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.exception.CommonException;
import com.haiph.menuservice.dto.request.PaymentRequest;
import com.haiph.menuservice.dto.response.DiscountResponse;
import com.haiph.menuservice.dto.response.OrderResponse;
import com.haiph.menuservice.dto.response.PaymentResponse;
import com.haiph.menuservice.entity.Payment;
import com.haiph.menuservice.repository.PaymentRepository;
import com.haiph.menuservice.service.DiscountService;
import com.haiph.menuservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PaymentServiceImpl implements com.haiph.menuservice.service.PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private DiscountService discountService;
//    List<PaymentResponse> paymentResponses = new ArrayList<>();

    private List<OrderResponse> findListIdOrder(List<Integer> ids) {
        List<OrderResponse> responses = orderService.findListId(ids);
        if (responses.isEmpty()) {
            throw new CommonException(Response.NOT_FOUND, "Cannot find order");
        }
        return responses;
    }

    private Double findTotalPrice(List<Integer> ids) {
        List<OrderResponse> responses = findListIdOrder(ids);
        return responses.iterator().next().getTotalPrice();
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
                                payment.getPersonCode(),
                                payment.getPaymentCode(),
                                findListIdOrder(payment.getOrderIds()),
                                findByDiscountId(payment.getDiscountId()),
                                findTotalPrice(payment.getOrderIds()),
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
                                payment.getPersonCode(),
                                payment.getPaymentCode(),
                                findListIdOrder(payment.getOrderIds()),
                                findByDiscountId(payment.getDiscountId()),
                                findTotalPrice(payment.getOrderIds()),
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
            remain = customerPay - ((totalPrice * response.getPercentDiscount().getPercent()) / 100);
        } else {
            log.info("Mã Giảm Giá Hết Hiệu Lực");
            remain = customerPay - totalPrice;
        }
        return remain;
    }

    @Override
    public List<PaymentResponse> findAllPersonCode(String personCode) {
        List<Payment> payments = paymentRepository.findAllByPersonCode(personCode);
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentResponse response =
                    PaymentResponse
                            .build(
                                    payment.getId(),
                                    payment.getPersonCode(),
                                    payment.getPaymentCode(),
                                    findListIdOrder(payment.getOrderIds()),
                                    findByDiscountId(payment.getDiscountId()),
                                    findTotalPrice(payment.getOrderIds()),
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
                                    payment.getPersonCode(),
                                    payment.getPaymentCode(),
                                    findListIdOrder(payment.getOrderIds()),
                                    findByDiscountId(payment.getDiscountId()),
                                    findTotalPrice(payment.getOrderIds()),
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
                                    payment.getPersonCode(),
                                    payment.getPaymentCode(),
                                    findListIdOrder(payment.getOrderIds()),
                                    findByDiscountId(payment.getDiscountId()),
                                    findTotalPrice(payment.getOrderIds()),
                                    payment.getCustomerPay(),
                                    payment.getRemain(),
                                    payment.getStatus(),
                                    payment.getScore(),
                                    payment.getCreateDate());
            paymentResponses.add(response);
        }
        return new PageImpl<>(paymentResponses, pageable, page.getTotalPages());
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

    private Double score(String personCode, Double totalPrice) {
        Double maxScore =0d;
        List<PaymentResponse> responses = findAllPersonCode(personCode);
        for (PaymentResponse respons : responses) {
            maxScore = respons.getScore();
        }
        totalPrice += maxScore;
        if (!responses.isEmpty()) {
            totalPrice += 0d;
        }
        return totalPrice;
    }


    @Override
    public String create(PaymentRequest request) {
        Payment payment = new Payment(
                request.getPersonCode(),
                genPaymentCode(LocalDate.now(), request.getPersonCode()),
                request.getOrderIds(),
                request.getDiscountId(),
                findTotalPrice(request.getOrderIds()),
                request.getCustomerPay(),
                totalRemain(findTotalPrice(request.getOrderIds()), request.getCustomerPay(), findByDiscountId(request.getDiscountId()), LocalDate.now()),
                request.getStatus(),
                score(request.getPersonCode(), findTotalPrice(request.getOrderIds())),
                LocalDateTime.now()
        );
        paymentRepository.save(payment);
        return "create succes";
    }

    @Override
    public String update(Integer id, PaymentRequest request) {
        PaymentResponse response = findById(id);
        if (response != null) {
            Payment payment =Payment.build(
                    response.getId(),
                    request.getPersonCode(),
                    genPaymentCode(LocalDate.now(), request.getPersonCode()),
                    request.getOrderIds(),
                    request.getDiscountId(),
                    findTotalPrice(request.getOrderIds()),
                    request.getCustomerPay(),
                    totalRemain(findTotalPrice(request.getOrderIds()), request.getCustomerPay(), findByDiscountId(request.getDiscountId()), LocalDate.now()),
                    request.getStatus(),
                    score(request.getPersonCode(), findTotalPrice(request.getOrderIds())),
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
}
