package com.haiph.menuservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.email.SendMail;
import com.haiph.common.enums.status.menuService.order.OrderStatus;
import com.haiph.common.exception.CommonException;
import com.haiph.common.formEmail.ApprovedOrder;
import com.haiph.common.formEmail.AutoOrder;
import com.haiph.common.formEmail.RefuseOrder;
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
import com.haiph.menuservice.feignClient.MailController;
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
    @Autowired
    private MailController mailController;

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
        if (request.getPersonCode() == null){
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
        }else if (request.getPersonCode() != null){
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
            sendMailAuto(request.getPersonCode());
            return "Create Success";
        }
        return "Create false";

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
        OrderResponse response = findById(id);
        if (orderRepository.approvedOrder(id) > 0) {
            sendMailApproved(response.getPersonResponses().getPersonCode(),
                    response.getCombos(),
                    response.getMenus(),
                    response.getForms());
            return "update Approved success";
        }
        return "update Approved fails";
    }

    @Override
    public String refuseOrder(Integer id) {
        OrderResponse response = findById(id);
        if (orderRepository.refuseOrder(id) > 0) {
            sendMailRefuse(response.getPersonResponses().getPersonCode());
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


    private String sendMailApproved(String personCode,
                                    List<ComboResponse> comboResponses,
                                    List<MenuResponse> menuResponses,
                                    List<RestaurantFormResponse> formResponses) {

        List<String> listCombo = new ArrayList<>();
        List<String> listMenu = new ArrayList<>();
        List<String> listForm = new ArrayList<>();
        for (ComboResponse comboRespons : comboResponses) {
            String comboName = comboRespons.getName();
            listCombo.add(comboName);
        }

        for (MenuResponse menuRespons : menuResponses) {
            String menuName = menuRespons.getName();
            listMenu.add(menuName);
        }

        for (RestaurantFormResponse formRespons : formResponses) {
            String formName = formRespons.getFormCode();
            listForm.add(formName);
        }

        APIResponse2<PersonResponse> person = personController.findByPersonCode(personCode);

        if (person.getResponseData() !=null) {
            String html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "\n" +
                    "<style>\n" +
                    "    html{\n" +
                    "        background-color: rgba(255, 59, 9, 0.2); \n" +
                    "        box-sizing: border-box;\n" +
                    "        margin: 0px;\n" +
                    "        padding: 0px;\n" +
                    "        width: 1200px;\n" +
                    "    }\n" +
                    "    body{\n" +
                    "    }\n" +
                    "    .heading{\n" +
                    "        padding: 2rem 0rem 1.5rem 10rem;\n" +
                    "        font-size: 22px;\n" +
                    "        background-color: rgba(255, 59, 9, 0.2); \n" +
                    "        font-family: Arial, Helvetica, sans-serif;\n" +
                    "        color: brown;\n" +
                    "    }\n" +
                    "    .content{\n" +
                    "        padding: 2rem 0rem 0rem 10rem ;\n" +
                    "        font-size: 18px;\n" +
                    "        background-color: rgba(255, 59, 9, 0.2); \n" +
                    "        font-family: Arial, Helvetica, sans-serif;\n" +
                    "        color: brown;\n" +
                    "    }\n" +
                    "   \n" +
                    "</style>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ\" crossorigin=\"anonymous\">\n" +
                    "    <div class=\"heading\">" + ApprovedOrder.SUBJECT_CENTER + "</div>\n" +
                    "    <div class=\"content\"><p>" + ApprovedOrder.MESSAGE + "</p>\n" +
                    "            <br> \n" +
                    "        <p>" + "Menu: "+ listMenu + "</p>\n" +
                    "        <p>" + "Combo: "+ listCombo + "</p>\n" +
                    "        <p>" + "Mã Bàn: "+ listForm + "</p>\n" +
                    "            <br> \n" +
                    "            <br> \n" +
                    "            <br> \n" +
                    "            <br> \n" +
                    "            <br> \n" +
                    "            <br> \n" +
                    "    </div>\n" +
                    "            \n" +
                    "</body>\n" +
                    "</html>";
            SendMail sendMail = SendMail.build(person.getResponseData().getEmail(),ApprovedOrder.SUBJECT,html);
            mailController.sendMail(sendMail);
            return "Send mail to person: " + person.getResponseData().getPersonCode() + " success";
        }else
            return "Send mail to person: " + person.getResponseData().getPersonCode() + " fail";
    }

    private String sendMailRefuse(String personCode) {

        APIResponse2<PersonResponse> person = personController.findByPersonCode(personCode);

        if (person.getResponseData() !=null) {
            String html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "\n" +
                    "<style>\n" +
                    "    html{\n" +
                    "        background-color: rgba(255, 59, 9, 0.2); \n" +
                    "        box-sizing: border-box;\n" +
                    "        margin: 0px;\n" +
                    "        padding: 0px;\n" +
                    "        width: 1200px;\n" +
                    "    }\n" +
                    "    body{\n" +
                    "    }\n" +
                    "    .heading{\n" +
                    "        padding: 2rem 0rem 1.5rem 10rem;\n" +
                    "        font-size: 22px;\n" +
                    "        background-color: rgba(255, 59, 9, 0.2); \n" +
                    "        font-family: Arial, Helvetica, sans-serif;\n" +
                    "        color: brown;\n" +
                    "    }\n" +
                    "    .content{\n" +
                    "        padding: 2rem 0rem 0rem 10rem ;\n" +
                    "        font-size: 18px;\n" +
                    "        background-color: rgba(255, 59, 9, 0.2); \n" +
                    "        font-family: Arial, Helvetica, sans-serif;\n" +
                    "        color: brown;\n" +
                    "    }\n" +
                    "   \n" +
                    "</style>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ\" crossorigin=\"anonymous\">\n" +
                    "    <div class=\"heading\">" + RefuseOrder.SUBJECT_CENTER + "</div>\n" +
                    "    <div class=\"content\"><p>" + RefuseOrder.MESSAGE + "</p>\n" +
                    "            <br> \n" +
                    "            <br> \n" +
                    "            <br> \n" +
                    "            <br> \n" +
                    "            <br> \n" +
                    "            <br> \n" +
                    "            <br> \n" +
                    "    </div>\n" +
                    "            \n" +
                    "</body>\n" +
                    "</html>";
            SendMail sendMail = SendMail.build(person.getResponseData().getEmail(),RefuseOrder.SUBJECT,html);
            mailController.sendMail(sendMail);
            return "Send mail to person: " + person.getResponseData().getPersonCode() + " success";
        }else
            return "Send mail to person: " + person.getResponseData().getPersonCode() + " fail";
    }

    private String sendMailAuto(String personCode) {

        APIResponse2<PersonResponse> person = personController.findByPersonCode(personCode);

        if (person.getResponseData() !=null) {
            String html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "\n" +
                    "<style>\n" +
                    "    html{\n" +
                    "        background-color: rgba(255, 59, 9, 0.2); \n" +
                    "        box-sizing: border-box;\n" +
                    "        margin: 0px;\n" +
                    "        padding: 0px;\n" +
                    "        width: 1200px;\n" +
                    "    }\n" +
                    "    body{\n" +
                    "    }\n" +
                    "    .heading{\n" +
                    "        padding: 2rem 0rem 1.5rem 10rem;\n" +
                    "        font-size: 22px;\n" +
                    "        background-color: rgba(255, 59, 9, 0.2); \n" +
                    "        font-family: Arial, Helvetica, sans-serif;\n" +
                    "        color: brown;\n" +
                    "    }\n" +
                    "    .content{\n" +
                    "        padding: 2rem 0rem 0rem 10rem ;\n" +
                    "        font-size: 18px;\n" +
                    "        background-color: rgba(255, 59, 9, 0.2); \n" +
                    "        font-family: Arial, Helvetica, sans-serif;\n" +
                    "        color: brown;\n" +
                    "    }\n" +
                    "   \n" +
                    "</style>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ\" crossorigin=\"anonymous\">\n" +
                    "    <div class=\"heading\">" + AutoOrder.SUBJECT_CENTER + "</div>\n" +
                    "    <div class=\"content\"><p>" + AutoOrder.MESSAGE + "</p>\n" +
                    "            <br> \n" +
                    "            <br> \n" +
                    "            <br> \n" +
                    "            <br> \n" +
                    "            <br> \n" +
                    "            <br> \n" +
                    "            <br> \n" +
                    "    </div>\n" +
                    "            \n" +
                    "</body>\n" +
                    "</html>";
            SendMail sendMail = SendMail.build(person.getResponseData().getEmail(),AutoOrder.SUBJECT,html);
            mailController.sendMail(sendMail);
            return "Send mail to person: " + person.getResponseData().getPersonCode() + " success";
        }else
            return "Send mail to person: " + person.getResponseData().getPersonCode() + " fail";
    }

}
