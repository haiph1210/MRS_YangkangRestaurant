package com.haiph.menuservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.exception.CommonException;
import com.haiph.menuservice.dto.form.SearchFormCombo;
import com.haiph.menuservice.dto.request.ComboRequest;
import com.haiph.menuservice.dto.response.ComboResponse;
import com.haiph.menuservice.entity.Combo;
import com.haiph.menuservice.entity.Menu;
import com.haiph.menuservice.repository.ComboRepository;
import com.haiph.menuservice.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComboServiceImpl implements com.haiph.menuservice.service.ComboService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ComboRepository comboRepository;
    @Autowired
    private ModelMapper mapper;

    private List<ComboResponse.MenuDTO> findMenuDTO(List<Integer> ids) {
        List<ComboResponse.MenuDTO> menus = new ArrayList<>();
        for (Integer id : ids) {
            Menu menu = menuRepository.findById(id).orElse(null);
            if (menu != null) {
                ComboResponse.MenuDTO menuDTO = ComboResponse.MenuDTO.build(menu.getName(), menu.getDescription());
                menus.add(menuDTO);
                return menus;
            } else throw new CommonException(Response.DATA_NOT_FOUND, "Cannot find Menu ID: " + id);
        }
        return menus;
    }

    @Override
    @Cacheable(cacheNames = "Combo")
    public Map<Integer, List<ComboResponse>> findAll() {
        Map<Integer, List<ComboResponse>> findAll = new HashMap<>();
        List<Combo> combos = comboRepository.findAll();
        List<ComboResponse> comboResponses = new ArrayList<>();
        Integer number = 0;
        for (Combo combo : combos) {
            ComboResponse response = ComboResponse.build(combo.getId(), combo.getName(), combo.getPrice(), combo.getNumberOfPeople(), combo.getDescription(), combo.getImgUrl(), findMenuDTO(combo.getMenuIds()));
            comboResponses.add(response);
                number++;
        }
        findAll.put(number, comboResponses);

        if (combos.isEmpty()) {
            throw new CommonException(Response.DATA_NOT_FOUND, "List Combo Have Not Data");
        }

        return findAll;
    }

    @Override
    @Cacheable(cacheNames = "Combo")
    public Page<ComboResponse> findAll(Pageable pageable) {
        Page<Combo> page = comboRepository.findAll(pageable);
        List<Combo> combos = page.getContent();
        List<ComboResponse> dtos = new ArrayList<>();
        for (Combo combo : combos) {
            if (combo != null) {
                ComboResponse response = ComboResponse.build(combo.getId(), combo.getName(), combo.getPrice(), combo.getNumberOfPeople(), combo.getDescription(), combo.getImgUrl(), findMenuDTO(combo.getMenuIds()));
                dtos.add(response);
            }

        }
        if (dtos.isEmpty()) {
            throw new CommonException(Response.DATA_NOT_FOUND, "List Combo Have Not Data");
        }
        return new PageImpl<>(dtos, pageable, page.getTotalPages());
    }

    @Override
    @Cacheable(cacheNames = "Combo")
    public List<ComboResponse> findForm(SearchFormCombo request) {
        List<Combo> combos = comboRepository.findWithForm(request.getSearch(), request.getMinPrice(), request.getMaxPrice(), request.getNumberOfPeople(), request.getDescription());
        List<ComboResponse> dtos = new ArrayList<>();
        for (Combo combo : combos) {
            if (combo != null) {
                ComboResponse response = ComboResponse.build(combo.getId(), combo.getName(), combo.getPrice(), combo.getNumberOfPeople(), combo.getDescription(), combo.getImgUrl(), findMenuDTO(combo.getMenuIds()));
                dtos.add(response);
            }
        }
        if (dtos.isEmpty()) {
            throw new CommonException(Response.DATA_NOT_FOUND, "Search With Form Haven't Data");

        }
        return dtos;
    }

    @Override
    @Cacheable(cacheNames = "Combo")
    public ComboResponse findById(Integer id) {
        Combo combo = comboRepository.findById(id).orElseThrow(() ->
                new CommonException(Response.DATA_NOT_FOUND.getResponseCode(), "Combo cannot having Id: " + id));

        ComboResponse response = ComboResponse.build(combo.getId(), combo.getName(), combo.getPrice(), combo.getNumberOfPeople(), combo.getDescription(), combo.getImgUrl(), findMenuDTO(combo.getMenuIds()));
        return response;

    }

    @Override
    @Cacheable(cacheNames = "Combo")
    public List<ComboResponse> findByName(String name) {
        List<Combo> combos = comboRepository.findByName(name);
        List<ComboResponse> responses = new ArrayList<>();
        for (Combo combo : combos) {
            if (combo != null) {
                ComboResponse response = ComboResponse.build(combo.getId(), combo.getName(), combo.getPrice(), combo.getNumberOfPeople(), combo.getDescription(), combo.getImgUrl(), findMenuDTO(combo.getMenuIds()));
                responses.add(response);
            }
        }
        if (responses.isEmpty()) {
            throw new CommonException(Response.DATA_NOT_FOUND, "List menu cannot name: " + name);
        }
        return responses;
    }

    @Override
    @Cacheable(cacheNames = "Combo")
    public List<ComboResponse> findByPrice(Double price) {
        List<Combo> combos = comboRepository.findByPrice(price);
        List<ComboResponse> responses = new ArrayList<>();
        for (Combo combo : combos) {
            if (combo != null) {
                ComboResponse response = ComboResponse.build(combo.getId(), combo.getName(), combo.getPrice(), combo.getNumberOfPeople(), combo.getDescription(), combo.getImgUrl(), findMenuDTO(combo.getMenuIds()));
                responses.add(response);
            }
        }
        if (responses.isEmpty()) {
            throw new CommonException(Response.DATA_NOT_FOUND, "Combo haven't price: " + price);
        }
        return responses;
    }


    @Override
    @CachePut(cacheNames = "Combo")
    public String create(ComboRequest request) {
        Combo combo = mapper.map(request, Combo.class);
        comboRepository.save(combo);
        return "Create Success";
    }

    @Override
    @CachePut(cacheNames = "Combo")
    public String update(Integer id, ComboRequest request) {
        try {
            Combo combo = comboRepository.findById(id).orElseThrow(() ->
                    new CommonException(Response.PARAM_INVALID, "Id NOT Exists,Cannot Update"));
            Combo comboUpdate = combo;
            comboUpdate.setName(request.getName());
            comboUpdate.setNumberOfPeople(request.getNumberOfPeople());
            comboUpdate.setPrice(comboUpdate.getPrice());
            comboUpdate.setImgUrl(request.getImgUrl());
            comboUpdate.setMenuIds(request.getMenuIds());
            comboRepository.save(comboUpdate);
            return "Update Success";
        } catch (CommonException exception) {
            throw new CommonException(Response.PARAM_NOT_VALID, exception.getMessage());
        }
    }

    @Override
    @CacheEvict(cacheNames = "Combo")
    public String deleteById(Integer id) {
        try {
            Combo combo = comboRepository.findById(id).orElseThrow(() ->
                    new CommonException(Response.PARAM_INVALID, "Id NOT Exists,Cannot Delete"));
            comboRepository.deleteById(id);
            return "Delete Success";
        } catch (CommonException exception) {
            throw new CommonException(Response.PARAM_NOT_VALID, exception.getMessage());
        }
    }


}
