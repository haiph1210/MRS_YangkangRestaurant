package com.haiph.menuservice.service;

import com.haiph.menuservice.dto.form.SearchFormCombo;
import com.haiph.menuservice.dto.form.SearchFormMenu;
import com.haiph.menuservice.dto.request.ComboRequest;
import com.haiph.menuservice.dto.response.ComboResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ComboService {
    @Cacheable(cacheNames = "combo")
    Map<Integer, List<ComboResponse>> findAll();

    @Cacheable(cacheNames = "Combo")
    Page<ComboResponse> findAll(Pageable pageable);

    @Cacheable(cacheNames = "Combo")
    List<ComboResponse> findForm(SearchFormCombo request);

    @Cacheable(cacheNames = "Combo")
    ComboResponse findById(Integer id);

    @Cacheable(cacheNames = "Combo")
    List<ComboResponse> findByName(String name);

    @Cacheable(cacheNames = "Combo")
    List<ComboResponse> findByPrice(Double price);

    //
    //    @Override
    ////    @Cacheable(cacheNames = "menu")
    //    public Page<MenuResponse> findAll(Pageable pageable) {
    //        Page<Menu> page = menuRepository.findAll(pageable);
    //        List<Menu> menus = page.getContent();
    //        List<MenuResponse> dtos = new ArrayList<>();
    //        for (Menu menu : menus) {
    //            if (menu != null) {
    //                MenuResponse response = MenuResponse.build(menu.getId(), menu.getName(), menu.getPrice(), menu.getImgUrl(), menu.getDescription(), findComboName(menu.getComboId()));
    //                dtos.add(response);
    //            } else
    //                throw new CommonException(Response.DATA_NOT_FOUND, "List Menu Have Not Data");
    //        }
    //        return new PageImpl<>(dtos, pageable, page.getTotalPages());
    //    }
    //
    //    @Override
    ////    @Cacheable(cacheNames = "menu")
    //    public List<MenuResponse> findForm(String menuNameOrId, Double minPrice, Double maxPrice, String imgUrl, String description, Integer comboId) {
    //        List<Menu> menus = menuRepository.findWithForm(menuNameOrId, minPrice, maxPrice, imgUrl, description, comboId);
    //        List<MenuResponse> dtos = new ArrayList<>();
    //        for (Menu menu : menus) {
    //            if (menu != null) {
    //                MenuResponse response = MenuResponse.build(menu.getId(), menu.getName(), menu.getPrice(), menu.getImgUrl(), menu.getDescription(), findComboName(menu.getComboId()));
    //                dtos.add(response);
    //            } else
    //                throw new CommonException(Response.DATA_NOT_FOUND, "Search With Form Haven't Data");
    //        }
    //        return dtos;
    //    }
    //
    //    @Override
    //    @Cacheable(cacheNames = "menu")
    //    public MenuResponse findById(Integer id) {
    //        try {
    //            Menu menu = menuRepository.findById(id).orElse(null);
    //            if (menu != null) {
    //                MenuResponse response = MenuResponse.build(menu.getId(), menu.getName(), menu.getPrice(), menu.getImgUrl(), menu.getDescription(), findComboName(menu.getComboId()));
    //                return response;
    //            } else
    //                throw new CommonException(Response.DATA_NOT_FOUND, "Menu cannot having Id: " + id);
    //
    //        } catch (CommonException exception) {
    //            throw new CommonException(Response.SYSTEM_ERROR, exception.getMessage());
    //        }
    //
    //    }
    //
    //    @Override
    //    @Cacheable(cacheNames = "menu")
    //    public List<MenuResponse> findByName(String name) {
    //        List<Menu> menus = menuRepository.findByName(name);
    //        List<MenuResponse> menuResponses = new ArrayList<>();
    //        for (Menu menu : menus) {
    //            if (menu != null) {
    //                MenuResponse response = MenuResponse.build(menu.getId(), menu.getName(), menu.getPrice(), menu.getImgUrl(), menu.getDescription(), findComboName(menu.getComboId()));
    //                menuResponses.add(response);
    //            } else
    //                throw new CommonException(Response.DATA_NOT_FOUND, "Menu haven't name: " + name);
    //        }
    //        return menuResponses;
    //    }
    //
    //    @Override
    //    @Cacheable(cacheNames = "menu")
    //    public List<MenuResponse> findByPrice(Double price) {
    //        List<Menu> menus = menuRepository.findByPrice(price);
    //        List<MenuResponse> menuResponses = new ArrayList<>();
    //        for (Menu menu : menus) {
    //            if (menu != null) {
    //                MenuResponse response = MenuResponse.build(menu.getId(), menu.getName(), menu.getPrice(), menu.getImgUrl(), menu.getDescription(), findComboName(menu.getComboId()));
    //                menuResponses.add(response);
    //            } else
    //                throw new CommonException(Response.DATA_NOT_FOUND, "Menu haven't price: " + price);
    //        }
    //        return menuResponses;
    //    }
    //
    //
    @CachePut("combo")
    String create(ComboRequest request);

    @CachePut(cacheNames = "Combo")
    String update(Integer id, ComboRequest request);

    @CacheEvict(cacheNames = "Combo")
    String deleteById(Integer id);
}
