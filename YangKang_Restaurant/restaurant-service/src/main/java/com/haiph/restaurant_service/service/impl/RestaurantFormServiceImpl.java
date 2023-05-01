package com.haiph.restaurant_service.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.enums.status.restaurantService.RestaurantFormStatus;
import com.haiph.common.exception.CommonException;

import com.haiph.restaurant_service.dto.request.form.RestaurantFormCreate;
import com.haiph.restaurant_service.dto.request.form.RestaurantFormUpdate;
import com.haiph.restaurant_service.dto.response.form.APIResponse;
import com.haiph.restaurant_service.dto.response.form.MasterialDTO;
import com.haiph.restaurant_service.dto.response.form.RestaurantFormResponse;
import com.haiph.restaurant_service.entity.RestaurantForm;
import com.haiph.restaurant_service.repository.RestaurantFormRepository;
import com.haiph.restaurant_service.service.RestaurantFormService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RestaurantFormServiceImpl implements RestaurantFormService {
    @Autowired
    private RestaurantFormRepository formRepository;
    @Value("${url.restaurantService.http}")
    private String urlFindByNameMasterial;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Page<RestaurantFormResponse> findAllPage(Pageable pageable) {
        Page<RestaurantForm> page = formRepository.findAll(pageable);
        List<RestaurantForm> forms = page.getContent();
        List<RestaurantFormResponse> responses = new ArrayList<>();
        for (RestaurantForm form : forms) {
            if (form != null) {
                RestaurantFormResponse newForm = RestaurantFormResponse
                        .build(form.getId(),
                                findByMasterialName(form.getMasterialName()), form.getFormCode(), form.getStatus());
                responses.add(newForm);
            }
        }
        return new PageImpl<>(responses, pageable, page.getTotalPages());
    }

    @Override
    public RestaurantFormResponse findById(Integer id) {
        RestaurantForm form = formRepository.findById(id).orElseThrow(() -> {
            throw new CommonException(Response.NOT_FOUND, "Cannot find id: " + id);
        });
        return mapper.map(form, RestaurantFormResponse.class);
    }

    @Override
    public RestaurantFormResponse findByFormCode(String formCode) {
        RestaurantForm form = formRepository.findByFormCode(formCode);
        if (form == null) {
            throw new CommonException(Response.NOT_FOUND, "Cannot find FormCode: " + formCode);
        }
        return mapper.map(form, RestaurantFormResponse.class);
    }

    public String create(RestaurantFormCreate form) {

        List<MasterialDTO> masterialDTO = findByMasterialName(form.getMasterialName());
        for (MasterialDTO dto : masterialDTO) {
            List<String> genarateForm = Arrays.stream(genarateFormCode(dto.getDetailName(),
                    dto.getName(), dto.getQuantity()).split(",")).toList();
            List<RestaurantForm> forms = new ArrayList<>();
            for (int i = 0; i < genarateForm.size(); i++) {
                RestaurantForm newForm = new RestaurantForm(
                        form.getMasterialName(),
                        genarateForm.get(i),
                        RestaurantFormStatus.PENDING);
                forms.add(newForm);
            }
            formRepository.saveAll(forms);

        }
        return "create success";
    }

    @Override
    public String update(RestaurantFormUpdate update) {
        RestaurantFormResponse response = findById(update.getId());
        if (response != null) {
            RestaurantForm form = mapper.map(update, RestaurantForm.class);
            formRepository.save(form);
            return "update with id: " + update.getId() + " success";
        }
        return "update fails";
    }

    @Override
    public String deleteById(Integer id) {
        RestaurantFormResponse response = findById(id);
        if (response != null) {
            formRepository.deleteById(id);
            return "delete id: " + id + " success";
        }
        return "delete fails";
    }

    @Override
    public String updateReady(List<Integer> ids) {
        for (Integer id : ids) {
            if (findById(id).getStatus().equals(RestaurantFormStatus.READY)) {
                return "updateReady with id: " + ids + " fail";
            }
        }
        formRepository.updateReady(ids);
        return "updateReady with id: " + ids + " success";
    }


    @Override
    public String updatePending(List<Integer> ids) {

        for (Integer id : ids) {
            if (findById(id).getStatus().equals(RestaurantFormStatus.PENDING)) {
                return "updatePending with id: " + ids + " fails";
            }

        }
        formRepository.updatePending(ids);
        return "updatePending with id: " + ids + " success";
    }

    @Override
    public String updateRefuse(List<Integer> ids) {

        for (Integer id : ids) {
            if (findById(id).getStatus().equals(RestaurantFormStatus.MAINTENANCE)) {
                return "updateRefuse with id: " + ids + " fail";
            }

        }
        formRepository.updateRefuse(ids);
        return "updateRefuse with id: " + ids + " success";
    }

    @Override
    public String updateBooked(List<Integer> ids) {
        for (Integer id : ids) {
            if (findById(id).getStatus().equals(RestaurantFormStatus.BOOKED)) {
                return "updateBooked with id: " + ids + " fail";
            }
        }
        formRepository.updateBooked(ids);
        return "updateBooked with id: " + ids + " success";
    }

    private List<MasterialDTO> findByMasterialName(String name) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<APIResponse> response = restTemplate.exchange(urlFindByNameMasterial + "/" + name, HttpMethod.GET, entity, APIResponse.class);
            return response.getBody().getResponseData();
        } catch (CommonException exception) {
            throw new CommonException(Response.NOT_FOUND, "cannot find Masterial");
        }
    }

    private static String genarateFormCode(String detailName, String name, Integer quantity) {
        String[] detailNames = detailName.split(" ");
        String[] names = name.split(" ");
        String a = detailNames[0].substring(0, 1);
        String b = detailNames[detailNames.length - 1].substring(0, 1);
        String c = names[0].substring(0, 1);
        StringBuilder formCode = new StringBuilder();
        for (int i = 1; i <= quantity; i++) {
            formCode.append(a).append(b).append("-").append(c).append(i);
            if (i != quantity) {
                formCode.append(",");
            }
        }

        return formCode.toString();
    }

    @Override
    public List<RestaurantFormResponse> findByListId(List<Integer> ids) {
        List<RestaurantForm> forms = formRepository.findByListID(ids);
        if (forms.isEmpty()) {
            throw new CommonException(Response.NOT_FOUND, "NO DATA");
        }
        List<RestaurantFormResponse> responses = mapper.map(forms, new TypeToken<List<RestaurantFormResponse>>() {
        }.getType());
        return responses;
    }
}
