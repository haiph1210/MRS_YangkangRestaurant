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
    byte[] readFileImg(String fileName);

    byte[] readListFileImg(String fileName);

    //    @Cacheable(cacheNames = "combo")
    Map<Integer, List<ComboResponse>> findAll();

//    @Cacheable(cacheNames = "Combo")
    Page<ComboResponse> findAll(Pageable pageable);

//    @Cacheable(cacheNames = "Combo")
    List<ComboResponse> findForm(SearchFormCombo request);

//    @Cacheable(cacheNames = "Combo")
    ComboResponse findById(Integer id);

//    @Cacheable(cacheNames = "Combo")
    List<ComboResponse> findByName(String name);

//    @Cacheable(cacheNames = "Combo")
    List<ComboResponse> findByPrice(Double price);

    List<ComboResponse> findByListId(List<Integer> ids);


//    @CachePut("combo")
    String create(ComboRequest request);

//    @CachePut(cacheNames = "Combo")
    String update(Integer id, ComboRequest request);

//    @CacheEvict(cacheNames = "Combo")
    String deleteById(Integer id);

    String deleteListById(List<Integer> ids);
}
