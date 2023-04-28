package com.haiph.restaurant_service.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.exception.CommonException;
import com.haiph.restaurant_service.dto.request.detail.DetailCreateRequest;
import com.haiph.restaurant_service.dto.request.detail.DetailUpdateRequest;
import com.haiph.restaurant_service.dto.response.DetailResponse;
import com.haiph.restaurant_service.entity.Detail;
import com.haiph.restaurant_service.entity.Info;
import com.haiph.restaurant_service.repository.DetailRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DetailServiceImpl implements com.haiph.restaurant_service.service.DetailService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private DetailRepository detailRepository;


    @Override
    public Page<DetailResponse> findAllPage(Pageable pageable) {
        Page<Detail> page = detailRepository.findAll(pageable);
        List<Detail> infos = page.getContent();
        List<DetailResponse> dtos = mapper.map(infos, new TypeToken<List<DetailResponse>>(){}.getType());
        return new PageImpl<>(dtos, pageable, page.getTotalPages());
    }

    @Override
    public DetailResponse findById(Integer id) {
        Detail info = detailRepository.findById(id).orElseThrow(() -> {
            throw new CommonException(Response.NOT_FOUND, "Cannot find id: " + id);
        });
        return mapper.map(info, DetailResponse.class);
    }

    @Override
    public DetailResponse findByName(String name) {
        Detail info = detailRepository.findByName(name);
        if (info == null) {
            throw new CommonException(Response.NOT_FOUND, "Cannot find name: " + name);
        }
        return mapper.map(info, DetailResponse.class);
    }

    @Override
    public String create(DetailCreateRequest request) {
        Detail detail = new Detail();
        detail.setName(request.getName());
        detail.setCreatedDate(LocalDate.now());
        detail.setInfo(new Info(request.getInfoId()));
        detailRepository.save(detail);
        return "Create success";
    }

    @Override
    public String update(DetailUpdateRequest request) {
        DetailResponse response = findById(request.getId());
        if (response != null) {
            Detail detail = mapper.map(request, Detail.class);
            detailRepository.save(detail);
            return "Update success";
        }
        return "Update false";
    }

    @Override
    public String deleteById(Integer id) {
        DetailResponse response = findById(id) ;
        detailRepository.deleteById(id);
        return "delete success";
    }
}



