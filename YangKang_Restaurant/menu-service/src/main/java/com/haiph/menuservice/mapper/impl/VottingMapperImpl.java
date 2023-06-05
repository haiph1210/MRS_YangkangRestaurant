package com.haiph.menuservice.mapper.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.exception.CommonException;
import com.haiph.menuservice.dto.request.VottingRequest;
import com.haiph.menuservice.dto.response.VottingResponse;
import com.haiph.menuservice.dto.response.restApi.APIResponse2;
import com.haiph.menuservice.dto.response.restApi.PersonResponse;
import com.haiph.menuservice.entity.Menu;
import com.haiph.menuservice.entity.Votting;
import com.haiph.menuservice.feignClient.PersonController;
import com.haiph.menuservice.mapper.VottingMapper;
import com.haiph.menuservice.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VottingMapperImpl implements VottingMapper {
    @Autowired
    private PersonController personController;
    @Autowired
    private MenuRepository menuRepository;

    private Menu findByMenuById(Integer id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> {
            throw new CommonException(Response.NOT_FOUND, "Cannot find Menu ID : " + id);
        });
        return menu;
    }

    private String findByUserCodeToFullName(String userCode) {
        APIResponse2<PersonResponse> response = personController.findByPersonCode(userCode);
        return response.getResponseData().getFullName();
    }

    @Override
    public VottingResponse VottingToVottingResponse(Votting votting) {
        VottingResponse vottingResponse =
                VottingResponse.build(
                        votting.getId(),
                        votting.getStar(),
                        findByUserCodeToFullName(votting.getUserCode()),
                        votting.getMenu().getName()
                );
        return vottingResponse;
    }

    @Override
    public List<VottingResponse> ListVottingToListVottingResponse(List<Votting> votting) {
        List<VottingResponse> vottingResponses = new ArrayList<>();
        if (!votting.isEmpty()) {
            for (Votting votting1 : votting) {
                VottingResponse vottingResponse =
                        VottingResponse.build(
                                votting1.getId(),
                                votting1.getStar(),
                                findByUserCodeToFullName(votting1.getUserCode()),
                                votting1.getMenu().getName()
                        );
                vottingResponses.add(vottingResponse);
            }
        }
        return vottingResponses;
    }

    @Override
    public Votting vottingRequestToVottingCreate(VottingRequest request) {
        Votting votting =
                new Votting(
                        request.getStar()
                        , request.getUserCode()
                        , findByMenuById(request.getMenuId()));
        return votting;
    }

    @Override
    public Votting vottingRequestToVottingUpdate(Integer id, VottingRequest request) {
        Votting votting =
                Votting.build(
                        id,
                        request.getStar()
                        , request.getUserCode()
                        , findByMenuById(request.getMenuId()));
        return votting;
    }
}
