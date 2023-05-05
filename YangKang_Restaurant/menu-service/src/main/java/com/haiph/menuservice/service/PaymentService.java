package com.haiph.menuservice.service;

import com.haiph.menuservice.dto.request.PaymentRequest;
import com.haiph.menuservice.dto.response.PaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    PaymentResponse findById(Integer id);

    PaymentResponse findByPaymentCode(String paymentCode);

    boolean checkExpired(LocalDate createDate, LocalDateTime startDate, LocalDateTime endDate);


    List<PaymentResponse> findAllByOrderId(Integer orderId);

    List<PaymentResponse> findListId(List<Integer> ids);

    Page<PaymentResponse> findAllPage(Pageable pageable);

    String create(PaymentRequest request);

    String update(Integer id, PaymentRequest request);

    String delete(Integer id);
}
