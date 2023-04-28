package com.haiph.restaurant_service.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.exception.CommonException;
import com.haiph.restaurant_service.dto.request.masterial.MasterialCreateRequest;
import com.haiph.restaurant_service.dto.request.masterial.MasterialUpdateRequest;
import com.haiph.restaurant_service.dto.response.MasterialResponse;
import com.haiph.restaurant_service.entity.Detail;
import com.haiph.restaurant_service.entity.Masterial;
import com.haiph.restaurant_service.repository.MasterialRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MasterialServiceImpl implements com.haiph.restaurant_service.service.MasterialService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private MasterialRepository masterialRepository;


    @Override
    public Page<MasterialResponse> findAllPage(Pageable pageable) {
        Page<Masterial> page = masterialRepository.findAll(pageable);
        List<Masterial> infos = page.getContent();
        List<MasterialResponse> dtos = mapper.map(infos, new TypeToken<List<MasterialResponse>>() {
        }.getType());
        return new PageImpl<>(dtos, pageable, page.getTotalPages());
    }

    @Override
    public MasterialResponse findById(Integer id) {
        Masterial info = masterialRepository.findById(id).orElseThrow(() -> {
            throw new CommonException(Response.NOT_FOUND, "Cannot find id: " + id);
        });
        return mapper.map(info, MasterialResponse.class);
    }

    @Override
    public List<MasterialResponse> findByName(String name) {
        List<MasterialResponse> newMasterial = new ArrayList<>();
        List<Masterial> masterials = masterialRepository.findByName(name);
        for (Masterial masterial : masterials) {
            MasterialResponse response = MasterialResponse.build(masterial.getId(),masterial.getName(), masterial.getQuantity(), masterial.getPrice(), masterial.getInitPrice(),masterial.getDetail().getName());
            newMasterial.add(response);
        }
        return newMasterial;

//        throw new CommonException(Response.NOT_FOUND, "Cannot find name: " + name);
    }

    @Override
    public String create(MasterialCreateRequest request) {
        Masterial masterial = new Masterial();
        masterial.setName(request.getName());
        masterial.setPrice(request.getPrice());
        masterial.setQuantity(request.getQuantity());
        masterial.setDetail(new Detail(request.getDetailId()));
        masterialRepository.save(masterial);
        return "Create Success";
    }

    @Override
    public String update(MasterialUpdateRequest request) {
        MasterialResponse response = findById(request.getId());
        if (response != null) {
            Masterial masterial = mapper.map(request, Masterial.class);
            masterialRepository.save(masterial);
            return "Update success";
        }
        return "Update false";
    }

    @Override
    public String deleteById(Integer id) {
        MasterialResponse response = findById(id);
        masterialRepository.deleteById(id);
        return "delete success";
    }
}
