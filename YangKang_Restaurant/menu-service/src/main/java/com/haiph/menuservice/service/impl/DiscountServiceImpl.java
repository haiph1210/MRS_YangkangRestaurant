package com.haiph.menuservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.enums.status.menuService.discount.DiscountStatus;
import com.haiph.common.exception.CommonException;
import com.haiph.common.qr_code_util.QRService;
import com.haiph.menuservice.dto.request.DiscountRequest;
import com.haiph.menuservice.dto.response.DiscountResponse;
import com.haiph.menuservice.dto.response.restApi.APIResponse2;
import com.haiph.menuservice.dto.response.restApi.InfoResponse;
import com.haiph.menuservice.entity.Discount;
import com.haiph.menuservice.feignClient.RestaurantController;
import com.haiph.menuservice.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountServiceImpl implements com.haiph.menuservice.service.DiscountService {
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private RestaurantController restaurantController;

    @Autowired
    private QRService qrService;
    @Override
    public Page<DiscountResponse> findAll(Pageable pageable) {
        Page<Discount> page = discountRepository.findAll(pageable);
        List<Discount> discounts = page.getContent();
        List<DiscountResponse> responses = new ArrayList<>();
        for (Discount discount : discounts) {
            DiscountResponse response = DiscountResponse
                    .build(discount.getId(),
                            discount.getDiscountCode(),
                            discount.getStartDate(),
                            discount.getEndDate(),
                            findByInfoId(discount.getInfoId()),
                            discount.getPercentDiscount(),
                            checkExpired(discount.getStartDate(), discount.getEndDate()));
            responses.add(response);
        }
        return new PageImpl<>(responses, pageable, page.getTotalElements());
    }

    @Override
    public DiscountResponse findById(Integer id) {
        Discount discount = discountRepository.findById(id).orElseThrow(() -> new CommonException(Response.NOT_FOUND, "Cannot find Id : " + id));
        DiscountResponse response = DiscountResponse
                .build(discount.getId(),
                        discount.getDiscountCode(),
                        discount.getStartDate(),
                        discount.getEndDate(),
                        findByInfoId(discount.getInfoId()),
                        discount.getPercentDiscount(),
                        checkExpired(discount.getStartDate(), discount.getEndDate()));
        return response;
    }

    @Override
    public DiscountResponse findByDiscountCode(String discountCode) {
        Discount discount = discountRepository.findByDiscountCode(discountCode);
        if (discount == null) {
            throw new CommonException(Response.NOT_FOUND, "Cannot find DiscountCode : " + discountCode);

        } else {
            DiscountResponse response = DiscountResponse
                    .build(discount.getId(),
                            discount.getDiscountCode(),
                            discount.getStartDate(),
                            discount.getEndDate(),
                            findByInfoId(discount.getInfoId()),
                            discount.getPercentDiscount(),
                            checkExpired(discount.getStartDate(), discount.getEndDate()));
            return response;
        }
    }

    @Override
//    public String create(DiscountRequest request) {
//        Discount discount = new Discount(request.getInfoId(), request.getStartDate(), request.getEndDate());
//        discount.setDiscountCode(genDiscountCode(request.getStartDate(), request.getEndDate(), findByInfoId(request.getInfoId()).getName()));
//        discount.setStatus(DiscountStatus.CREATE);
//        Discount checkDiscountCode = discountRepository.findByDiscountCode(discount.getDiscountCode());
//        Integer number = 0;
//        if (checkDiscountCode != null) {
//            number++;
//            discount.setDiscountCode(discount.getDiscountCode().concat("-" + number));
//            discountRepository.save(discount);
//        } else {
//            discountRepository.save(discount);
//        }
//        return "Create Success";
//    }

    public String create(DiscountRequest request) {
        Discount discount = new Discount(request.getInfoId(), request.getPercentDiscount(), request.getStartDate(), request.getEndDate());
        discount.setDiscountCode(genDiscountCode(request.getStartDate(), request.getEndDate(), findByInfoId(request.getInfoId()).getName()));
        discount.setStatus(DiscountStatus.CREATE);
        Discount checkDiscountCode = discountRepository.findByDiscountCode(discount.getDiscountCode());
        Integer number = 0;
        while (checkDiscountCode != null) {
            number++;
            discount.setDiscountCode(genDiscountCode(request.getStartDate(), request.getEndDate(), findByInfoId(request.getInfoId()).getName()) + "-" + number);
            checkDiscountCode = discountRepository.findByDiscountCode(discount.getDiscountCode());
        }
        discountRepository.save(discount);
        return "Create Success";
    }


    @Override
    public String update(Integer id, DiscountRequest request) {
        if (findById(id) != null) {
            Discount discount = new Discount(request.getInfoId(), request.getPercentDiscount(), request.getStartDate(), request.getEndDate());
            discount.setDiscountCode(genDiscountCode(request.getStartDate(), request.getEndDate(), findByInfoId(request.getInfoId()).getName()));
            discount.setStatus(checkExpired(request.getStartDate(), request.getEndDate()));
            DiscountResponse checkDiscountCode = findByDiscountCode(discount.getDiscountCode());
            Integer number = 0;
            if (checkDiscountCode != null) {
                discount.getDiscountCode().concat("-" + number++);
            } else {
                discountRepository.save(discount);
            }
            return "update Success";
        }
        return "update fail";
    }

    @Override
    public String delete(Integer id) {
        if (findById(id) != null) {
            discountRepository.deleteById(id);
            return "Delete success";
        }
        return "Delete fail";
    }

    private InfoResponse findByInfoId(Integer id) {
        APIResponse2<InfoResponse> response = restaurantController.findByInfoId(id);
        return response.getResponseData();
    }

    private boolean existByStartDateAndEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (!discountRepository.existsByStartDateAndEndDate(startDate, endDate).isEmpty()) {
            return true;
        }
        return false;
    }

    private DiscountStatus checkExpired(LocalDateTime startDate, LocalDateTime endDate) {
        if (existByStartDateAndEndDate(startDate, endDate)) {
            return DiscountStatus.CREATE;
        }
        return DiscountStatus.EXPIRED;
    }

    private String genDiscountCode(LocalDateTime startDate, LocalDateTime endDate, String infoName) {
        String[] gendiscountCode = infoName.split(" ");
        String discountCode = "";
        for (int i = 0; i < gendiscountCode.length; i++) {
            discountCode += gendiscountCode[i].substring(0, 1);
        }
        StringBuilder builder = new StringBuilder();
        int mounthStart = startDate.getMonth().getValue();
        int dayStart = startDate.getDayOfMonth();
        builder.append(mounthStart).append(dayStart);
        int mounthEnd = endDate.getMonth().getValue();
        int dayEnd = endDate.getDayOfMonth();
        builder.append(discountCode).append(mounthEnd).append(dayEnd);
        return builder.toString();
    }

    @Override
    public String gennaterateQrService(Integer id) {
        DiscountResponse response = findById(id);
        return qrService.gennateQrCode(response);
    }

}
