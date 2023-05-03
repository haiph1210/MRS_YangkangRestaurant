package com.haiph.menuservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.enums.status.menuService.order.OrderStatus;
import com.haiph.common.exception.CommonException;
import com.haiph.menuservice.dto.form.SearchFormOrder;
import com.haiph.menuservice.dto.request.OrderRequest;
import com.haiph.menuservice.dto.response.ComboResponse;
import com.haiph.menuservice.dto.response.restApi.APIResponse;
import com.haiph.menuservice.dto.response.restApi.APIResponse2;
import com.haiph.menuservice.dto.response.restApi.PersonResponse;
import com.haiph.menuservice.dto.response.restApi.RestaurantFormResponse;
import com.haiph.menuservice.dto.response.MenuResponse;
import com.haiph.menuservice.dto.response.OrderResponse;
import com.haiph.menuservice.entity.Order;
import com.haiph.menuservice.feignClient.PersonController;
import com.haiph.menuservice.feignClient.RestaurantController;
import com.haiph.menuservice.repository.OrderRepository;
import com.haiph.menuservice.service.ComboService;
import com.haiph.menuservice.service.MenuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements com.haiph.menuservice.service.OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ComboService comboService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RestaurantController getFormCode;
    @Autowired
    private PersonController personController;

    private List<MenuResponse> findMenuList(List<Integer> ids) {
        return menuService.findByListId(ids);
    }

    private List<ComboResponse> findComboList(List<Integer> ids) {
        return comboService.findByListId(ids);
    }

    private PersonResponse findPerson(String personCode) {
        APIResponse2<PersonResponse> response = personController.findByPersonCode(personCode);
        return response.getResponseData();
    }
    private List<RestaurantFormResponse> findRestaurantFormList(List<Integer> ids) {
        APIResponse response = getFormCode.findByListId(ids);
        return response.getResponseData();
    }

    @Override
    public Page<OrderResponse> findAllPage(Pageable pageable) {
        Page<Order> page = orderRepository.findAll(pageable);
        List<Order> orders = page.getContent();
        List<OrderResponse> responses = new ArrayList<>();
        for (Order order : orders) {
            OrderResponse response = OrderResponse
                    .build(order.getId(),
                            order.getOrderCode(),
                            findPerson(order.getPersonCode()),
                            findMenuList(order.getIdMenus()),
                            findComboList(order.getIdCombos()),
                            findRestaurantFormList(order.getIdForms()),
                            order.getPeoples(),
                            order.getTotalAmount(),
                            order.getTotalPrice(),
                            order.getCreateDate(),
                            order.getHour(),
                            order.getDescription(),
                            order.getType(),
                            order.getStatus()
                    );
            responses.add(response);
        }
        return new PageImpl<>(responses, pageable, page.getTotalPages());
    }

    @Override
    public OrderResponse findById(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            throw new CommonException(Response.NOT_FOUND, "Cannot find Order id: " + id);
        });
        OrderResponse response = OrderResponse
                .build(order.getId(),
                        order.getOrderCode(),
                        findPerson(order.getPersonCode()),
                        findMenuList(order.getIdMenus()),
                        findComboList(order.getIdCombos()),
                        findRestaurantFormList(order.getIdForms()),
                        order.getPeoples(),
                        order.getTotalAmount(),
                        order.getTotalPrice(),
                        order.getCreateDate(),
                        order.getHour(),
                        order.getDescription(),
                        order.getType(),
                        order.getStatus());
        return response;
    }

    @Override
    public OrderResponse findByOrderCode(String orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode);
        if (order != null) {
            OrderResponse response = OrderResponse
                    .build(order.getId(),
                            order.getOrderCode(),
                            findPerson(order.getPersonCode()),
                            findMenuList(order.getIdMenus()),
                            findComboList(order.getIdCombos()),
                            findRestaurantFormList(order.getIdForms()),
                            order.getPeoples(),
                            order.getTotalAmount(),
                            order.getTotalPrice(),
                            order.getCreateDate(),
                            order.getHour(),
                            order.getDescription(),
                            order.getType(),
                            order.getStatus());
            return response;
        } else throw new CommonException(Response.NOT_FOUND, "Cannot find OrderCode: " + orderCode);
    }


    @Override
    public String create(OrderRequest request) {
        Order order = new Order(
                genarateOrderCode(request.getHour(),
                        findRestaurantFormList(request.getIdForms()).iterator().next().getFormCode(),
                        request.getType().getDescription()),
                request.getPersonCode(),
                request.getIdMenus(),
                request.getIdCombos(),
                request.getIdForms(),
                totalAmount(findMenuList(request.getIdMenus()), findComboList(request.getIdCombos())),
                totalPrice(findMenuList(request.getIdMenus()), findComboList(request.getIdCombos())),
                LocalDate.now(),
                request.getHour(),
                request.getDescription(),
                request.getType(),
                OrderStatus.PENDING,
                request.getPeople());
        orderRepository.save(order);
        getFormCode.updateBooked(request.getIdForms());
        return "Create Success";
    }

    @Override
    public String update(Integer id, OrderRequest request) {
        OrderResponse response = findById(id);
        if (response != null) {
            Order order = new Order(
                    genarateOrderCode(request.getHour(),
                            findRestaurantFormList(request.getIdForms()).iterator().next().getFormCode(),
                            request.getType().getDescription()),
                    request.getPersonCode(),
                    request.getIdMenus(),
                    request.getIdCombos(),
                    request.getIdForms(),
                    totalAmount(findMenuList(request.getIdMenus()), findComboList(request.getIdCombos())),
                    totalPrice(findMenuList(request.getIdMenus()), findComboList(request.getIdCombos())),
                    LocalDate.now(),
                    request.getHour(),
                    request.getDescription(),
                    request.getType(),
                    OrderStatus.PENDING,
                    request.getPeople());
            orderRepository.save(order);
            return "Update success";
        }
        return "Update fail";
    }

    @Override
    public String delete(Integer id) {
        OrderResponse response = findById(id);
        if (response != null) {
            orderRepository.deleteById(id);
            return "Delete success";
        }
        return "Delete fail";
    }

//    public String deleteAllById(List<Integer> ids) {
//
//    }

    @Override
    public String approvedOrder(Integer id) {
        if (orderRepository.approvedOrder(id) > 0) {
            return "update Approved success";
        }
        return "update Approved fails";
    }

    @Override
    public String refuseOrder(Integer id) {
        if (orderRepository.refuseOrder(id) > 0) {
            return "update refuse success";
        }
        return "update refuse fails";
    }

    @Override
    public List<OrderResponse> findFormOrder(SearchFormOrder formOrder) {
        List<Order> orders = orderRepository.
                findByFormOrder(formOrder.getSearch(),
                        formOrder.getMinTotalPrice(),
                        formOrder.getMaxTotalPrice(),
                        formOrder.getHour(),
                        formOrder.getType(),
                        formOrder.getStatus());
        if (orders.isEmpty()) {
            throw new CommonException(Response.NOT_FOUND, "NOT DATA");
        }
        List<OrderResponse> responses = new ArrayList<>();
        for (Order order : orders) {
            OrderResponse response = OrderResponse
                    .build(order.getId(),
                            order.getOrderCode(),
                            findPerson(order.getPersonCode()),
                            findMenuList(order.getIdMenus()),
                            findComboList(order.getIdCombos()),
                            findRestaurantFormList(order.getIdForms()),
                            order.getPeoples(),
                            order.getTotalAmount(),
                            order.getTotalPrice(),
                            order.getCreateDate(),
                            order.getHour(),
                            order.getDescription(),
                            order.getType(),
                            order.getStatus());
            responses.add(response);
        }
        return responses;
    }

    @Override
    public List<OrderResponse> findListId(List<Integer> ids) {
        List<Order> orders = orderRepository.findByListId(ids);
        if (orders.isEmpty()) {
            throw new CommonException(Response.NOT_FOUND, "NOT DATA");
        }
        List<OrderResponse> responses = new ArrayList<>();
        for (Order order : orders) {
            OrderResponse response = OrderResponse
                    .build(order.getId(),
                            order.getOrderCode(),
                            findPerson(order.getPersonCode()),
                            findMenuList(order.getIdMenus()),
                            findComboList(order.getIdCombos()),
                            findRestaurantFormList(order.getIdForms()),
                            order.getPeoples(),
                            order.getTotalAmount(),
                            order.getTotalPrice(),
                            order.getCreateDate(),
                            order.getHour(),
                            order.getDescription(),
                            order.getType(),
                            order.getStatus());
            responses.add(response);
        }
        return responses;
    }

    private Double totalPrice(List<MenuResponse> menuResponses, List<ComboResponse> comboResponses) {
        Double initPrice = 0d;
        for (MenuResponse menuRespons : menuResponses) {
            initPrice += menuRespons.getPrice();
        }
        for (ComboResponse comboRespons : comboResponses) {
            initPrice += comboRespons.getPrice();
        }
        return initPrice;
    }

    private Integer totalAmount(List<MenuResponse> responses, List<ComboResponse> comboResponses) {
        Integer totalAmountMenu = responses.size();
        Integer totalAmountCombo = comboResponses.size();
        Integer totalAmount = totalAmountCombo + totalAmountMenu;
        return totalAmount;
    }

    private String genarateOrderCode(LocalDateTime hour, String formCode, String type) {
        String[] genType = type.split(" ");
        System.out.println("genType = " + genType);
        String gentypes = "";
        for (int i = 0; i < genType.length; i++) {
            gentypes += genType[i].substring(0, 1);
        }
        System.out.println("gentypes = " + gentypes);
        int year = hour.getYear();
        int month = hour.getMonthValue();
        int day = hour.getDayOfMonth();
        int hours = hour.getHour();
        int minute = hour.getMinute();
        StringBuilder builder = new StringBuilder();
        builder.append(day).append(month).append(year).append(".").append(hours).append(minute);
        String result = gentypes + "." + builder + "." + formCode;
        return result;
    }
}
