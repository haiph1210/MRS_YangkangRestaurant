package com.haiph.menuservice.service;

import com.haiph.common.dto.response.ResponseBody;
import com.haiph.menuservice.dto.form.SearchFormMenu;
import com.haiph.menuservice.dto.request.MenuRequest;
import com.haiph.menuservice.dto.response.MenuResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface MenuService {

    byte[] readFileImg(String fileName);
    List<byte[]> readFileImg2(Integer id);

//    List<byte[]> readListFileImg(String fileName);

//    ResponseBody getAllUploadImage();

    //     @Cacheable(cacheNames = "Menu")
    List<MenuResponse> findAll();

    //    @Cacheable(cacheNames = "Menu")
    Page<MenuResponse> findAll(Pageable pageable);

    //    @Cacheable(cacheNames = "Menu")
    List<MenuResponse> findForm(SearchFormMenu request);

    List<MenuResponse> findByListId(List<Integer> ids);

    //   @Cacheable(cacheNames = "Menu")
    MenuResponse findById(Integer id);

    MenuResponse findByCode(String code);

    // @Cacheable(cacheNames = "Menu")
    List<MenuResponse> findByName(String name);

    //  @Cacheable(cacheNames = "Menu")
    List<MenuResponse> findByPrice(Double price);

    //   @CachePut(cacheNames = "Menu")
    String create(MenuRequest menuRequest);

    //  @CachePut(cacheNames = "Menu")
    String update(Integer id, MenuRequest menuRequest);

    //  @CacheEvict(cacheNames = "Menu")
    String deleteById(Integer id);

    String deleteByListId(List<Integer> ids);
}
