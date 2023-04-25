package com.haiph.menuservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.exception.CommonException;
import com.haiph.menuservice.dto.form.SearchFormMenu;
import com.haiph.menuservice.dto.request.MenuRequest;
import com.haiph.menuservice.dto.response.MenuResponse;
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

import java.util.*;

@Service
public class MenuServiceImpl implements com.haiph.menuservice.service.MenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ComboRepository comboRepository;
    @Autowired
    private ModelMapper mapper;

    private String findComboName(Integer id) {

        Combo combo = comboRepository.findById(id).orElse(null);
        if (combo != null) {
            return combo.getName();
        } else throw new CommonException(Response.DATA_NOT_FOUND, "Cannot find Combo ID: " + id);
    }

    @Override
    @Cacheable(cacheNames = "Menu")
    public Map<Integer, List<MenuResponse>> findAll() {
        Map<Integer, List<MenuResponse>> findAll = new HashMap<>();
        List<Menu> menus = menuRepository.findAll();
        List<MenuResponse> menuResponses = new ArrayList<>();
        Integer number = 0;
        for (Menu menu : menus) {

            MenuResponse response = MenuResponse.build(menu.getId(), menu.getName(), menu.getPrice(), menu.getImgUrl(), menu.getDescription(), findComboName(menu.getComboId()));
            menuResponses.add(response);
            if (menu != null) {
                number++;
                findAll.put(number, menuResponses);
            } else
                throw new CommonException(Response.DATA_NOT_FOUND, "List Menu Have Not Data");
        }
        return findAll;
    }

    @Override
    @Cacheable(cacheNames = "Menu")
    public Page<MenuResponse> findAll(Pageable pageable) {
        Page<Menu> page = menuRepository.findAll(pageable);
        List<Menu> menus = page.getContent();
        List<MenuResponse> dtos = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu != null) {
                MenuResponse response = MenuResponse.build(menu.getId(), menu.getName(), menu.getPrice(), menu.getImgUrl(), menu.getDescription(), findComboName(menu.getComboId()));
                dtos.add(response);
            } else
                throw new CommonException(Response.DATA_NOT_FOUND, "List Menu Have Not Data");
        }
        return new PageImpl<>(dtos, pageable, page.getTotalPages());
    }

    @Override
    @Cacheable(cacheNames = "Menu")
    public List<MenuResponse> findForm(SearchFormMenu request) {
        List<Menu> menus = menuRepository.findWithForm(request.getSearch(), request.getMinPrice(), request.getMaxPrice(), request.getImgUrl(), request.getDescription());
        List<MenuResponse> dtos = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu != null) {
                MenuResponse response = MenuResponse.build(menu.getId(), menu.getName(), menu.getPrice(), menu.getImgUrl(), menu.getDescription(), findComboName(menu.getComboId()));
                dtos.add(response);
            } else
                throw new CommonException(Response.DATA_NOT_FOUND, "Search With Form Haven't Data");
        }
        return dtos;
    }

    @Override
    @Cacheable(cacheNames = "Menu")
    public MenuResponse findById(Integer id) {
        try {
            Menu menu = menuRepository.findById(id).orElse(null);
            if (menu != null) {
                MenuResponse response = MenuResponse.build(menu.getId(), menu.getName(), menu.getPrice(), menu.getImgUrl(), menu.getDescription(), findComboName(menu.getComboId()));
                return response;
            } else
                throw new CommonException(Response.DATA_NOT_FOUND, "Menu cannot having Id: " + id);

        } catch (CommonException exception) {
            throw new CommonException(Response.SYSTEM_ERROR, exception.getMessage());
        }

    }

    @Override
    @Cacheable(cacheNames = "Menu")
    public List<MenuResponse> findByName(String name) {
        List<Menu> menus = menuRepository.findByName(name);
        List<MenuResponse> menuResponses = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu != null) {
                MenuResponse response = MenuResponse.build(menu.getId(), menu.getName(), menu.getPrice(), menu.getImgUrl(), menu.getDescription(), findComboName(menu.getComboId()));
                menuResponses.add(response);
            } else
                throw new CommonException(Response.DATA_NOT_FOUND, "Menu haven't name: " + name);
        }
        return menuResponses;
    }

    @Override
    @Cacheable(cacheNames = "Menu")
    public List<MenuResponse> findByPrice(Double price) {
        List<Menu> menus = menuRepository.findByPrice(price);
        List<MenuResponse> menuResponses = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu != null) {
                MenuResponse response = MenuResponse.build(menu.getId(), menu.getName(), menu.getPrice(), menu.getImgUrl(), menu.getDescription(), findComboName(menu.getComboId()));
                menuResponses.add(response);
            } else
                throw new CommonException(Response.DATA_NOT_FOUND, "Menu haven't price: " + price);
        }
        return menuResponses;
    }


    @Override
    @CachePut(cacheNames = "Menu")
    public String create(MenuRequest menuRequest) {
        try {
            Menu menu = mapper.map(menuRequest, Menu.class);
            menuRepository.save(menu);
            return "Create Success";
        } catch (CommonException exception) {
            throw new CommonException(Response.PARAM_NOT_VALID, exception.getMessage());
        }
    }

    @Override
    @CachePut(cacheNames = "Menu")
    public String update(Integer id, MenuRequest menuRequest) {
        try {
            Menu menu = menuRepository.findById(id).orElseThrow(() ->
                    new CommonException(Response.PARAM_INVALID, "Id NOT Exists,Cannot Update"));
            Menu menuUpdate = menu;
            menuUpdate.setName(menuRequest.getName());
            menuUpdate.setPrice(menuRequest.getPrice());
            menuUpdate.setDescription(menuRequest.getDescription());
            menuUpdate.setComboId(menuRequest.getComboId());
            menuRepository.save(menuUpdate);
            return "Update Success";
        } catch (CommonException exception) {
            throw new CommonException(Response.PARAM_NOT_VALID, exception.getMessage());
        }
    }

    @Override
    @CacheEvict(cacheNames = "Menu")
    public String deleteById(Integer id) {
        try {
            Menu menu = menuRepository.findById(id).orElse(null);
            if (menu != null) {
                menuRepository.deleteById(id);
                return "Delete Success";
            } else throw new CommonException(Response.PARAM_INVALID, "Id NOT Exists,Cannot Delete");
        } catch (CommonException exception) {
            throw new CommonException(Response.PARAM_NOT_VALID, exception.getMessage());
        }
    }

}
