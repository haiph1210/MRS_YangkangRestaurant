package com.haiph.menuservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.exception.CommonException;
import com.haiph.common.uploadfile.UploadFile;
import com.haiph.menuservice.dto.form.SearchFormCombo;
import com.haiph.menuservice.dto.request.ComboRequest;
import com.haiph.menuservice.dto.response.ComboResponse;
import com.haiph.menuservice.dto.response.MenuResponse;
import com.haiph.menuservice.entity.Combo;
import com.haiph.menuservice.repository.ComboRepository;
import com.haiph.menuservice.repository.MenuRepository;
import com.haiph.menuservice.service.MenuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComboServiceImpl implements com.haiph.menuservice.service.ComboService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ComboRepository comboRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UploadFile genfile;
    private Path path;

    public ComboServiceImpl() {
        path = Paths.get("menu-service/upload/img/combo");
    }

    private List<String> genUrlImage(List<MultipartFile> files, String name, Path path) {
        return genfile.saveListFile(files, name, path);
    }

    @Override
    public byte[] readFileImg(String fileName) {
        return genfile.readFileContent(fileName, path);
    }

    @Override
    public byte[] readListFileImg(String fileName) {
        String[] fileNameList = fileName.trim().split(",");
        if (fileNameList != null) {
            for (int i = 0; i < fileNameList.length; i++) {
                return readFileImg(fileNameList[i]);
            }
        }
        return null;
    }
    private List<MenuResponse> findMenuDTO(List<Integer> ids) {
            List<MenuResponse> menu = menuService.findByListId(ids);
        return menu;
    }
    @Override
//    @Cacheable(cacheNames = "Combo")
    public List<ComboResponse> findAllList() {
        List<Combo> combos = comboRepository.findAll();
        List<ComboResponse> comboResponses = new ArrayList<>();
        Integer number = 0;
        for (Combo combo : combos) {
            ComboResponse response =
                    ComboResponse.build(combo.getId(),
                            combo.getName(),
                            combo.getCode(),
                            combo.getPrice(),
                            combo.getDescription(),
                            combo.getImgUrl(),
                            findMenuDTO(combo.getMenuIds()));
            comboResponses.add(response);
            number++;
        }
        if (combos.isEmpty()) {
            throw new CommonException(Response.DATA_NOT_FOUND, "List Combo Have Not Data");
        }
        return comboResponses;
    }

    @Override
//    @Cacheable(cacheNames = "Combo")
    public Map<Integer, List<ComboResponse>> findAll() {
        Map<Integer, List<ComboResponse>> findAll = new HashMap<>();
        List<Combo> combos = comboRepository.findAll();
        List<ComboResponse> comboResponses = new ArrayList<>();
        Integer number = 0;
        for (Combo combo : combos) {
            ComboResponse response =
                    ComboResponse.build(combo.getId(),
                    combo.getName(),
                            combo.getCode(),
                            combo.getPrice(),
                            combo.getDescription(),
                            combo.getImgUrl(),
                            findMenuDTO(combo.getMenuIds()));
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
//    @Cacheable(cacheNames = "Combo")
    public Page<ComboResponse> findAll(Pageable pageable) {
        Page<Combo> page = comboRepository.findAll(pageable);
        List<Combo> combos = page.getContent();
        List<ComboResponse> dtos = new ArrayList<>();
        for (Combo combo : combos) {
            if (combo != null) {
                ComboResponse response =
                        ComboResponse.
                                build(combo.getId(),
                                        combo.getName(),
                                        combo.getCode(),
                                        combo.getPrice(),
                                        combo.getDescription(),
                                        combo.getImgUrl(),
                                        findMenuDTO(combo.getMenuIds()));
                dtos.add(response);
            }

        }
        if (dtos.isEmpty()) {
            throw new CommonException(Response.DATA_NOT_FOUND, "List Combo Have Not Data");
        }
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @Override
    public List<ComboResponse> findForm(SearchFormCombo request) {
        List<Combo> combos = comboRepository.findWithForm(request.getSearch(), request.getMinPrice(), request.getMaxPrice());
        List<ComboResponse> dtos = new ArrayList<>();
        for (Combo combo : combos) {
            if (combo != null) {
                ComboResponse response = ComboResponse.build(combo.getId(), combo.getName(),combo.getCode(), combo.getPrice(), combo.getDescription(), combo.getImgUrl(), findMenuDTO(combo.getMenuIds()));
                dtos.add(response);
            }
        }
        if (dtos.isEmpty()) {
            throw new CommonException(Response.DATA_NOT_FOUND, "Search With Form Haven't Data");

        }
        return dtos;
    }

    @Override
    public ComboResponse findById(Integer id) {
        Combo combo = comboRepository.findById(id).orElseThrow(() ->
                new CommonException(Response.DATA_NOT_FOUND.getResponseCode(), "Combo cannot having Id: " + id));
        ComboResponse response = ComboResponse.build(combo.getId(), combo.getName(), combo.getCode(),combo.getPrice(), combo.getDescription(), combo.getImgUrl(), findMenuDTO(combo.getMenuIds()));
        return response;

    }

    @Override
    public ComboResponse findByCode(String code) {
        Combo combo = comboRepository.findByCode(code).orElseThrow(() ->
                new CommonException(Response.DATA_NOT_FOUND.getResponseCode(), "Combo cannot having code: " + code));
        ComboResponse response = ComboResponse.build(combo.getId(), combo.getName(), combo.getCode(),combo.getPrice(), combo.getDescription(), combo.getImgUrl(), findMenuDTO(combo.getMenuIds()));
        return response;

    }

    @Override
//    @Cacheable(cacheNames = "Combo")
    public List<ComboResponse> findByName(String name) {
        List<Combo> combos = comboRepository.findByName(name);
        List<ComboResponse> responses = new ArrayList<>();
        for (Combo combo : combos) {
            if (combo != null) {
                ComboResponse response = ComboResponse.build(combo.getId(), combo.getName(), combo.getCode(),combo.getPrice(), combo.getDescription(), combo.getImgUrl(), findMenuDTO(combo.getMenuIds()));
                responses.add(response);
            }
        }
        if (responses.isEmpty()) {
            throw new CommonException(Response.DATA_NOT_FOUND, "List menu cannot name: " + name);
        }
        return responses;
    }

    @Override
//    @Cacheable(cacheNames = "Combo")
    public List<ComboResponse> findByPrice(Double price) {
        List<Combo> combos = comboRepository.findByPrice(price);
        List<ComboResponse> responses = new ArrayList<>();
        for (Combo combo : combos) {
            if (combo != null) {
                ComboResponse response = ComboResponse.build(combo.getId(), combo.getName(), combo.getCode(),combo.getPrice(), combo.getDescription(), combo.getImgUrl(), findMenuDTO(combo.getMenuIds()));
                responses.add(response);
            }
        }
        if (responses.isEmpty()) {
            throw new CommonException(Response.DATA_NOT_FOUND, "Combo haven't price: " + price);
        }
        return responses;
    }

    private List<MenuResponse> findListMenuByID(List<Integer> ids) {
        return menuService.findByListId(ids);
    }

    @Override
    public List<ComboResponse> findByListId(List<Integer> ids) {
        List<ComboResponse> responses = new ArrayList<>();
        List<Combo> combos = comboRepository.findByListId(ids);
        for (Combo combo : combos) {
            ComboResponse response = ComboResponse
                    .build(combo.getId(),
                            combo.getName(),
                            combo.getCode(),
                            combo.getPrice(),
                            combo.getDescription(),
                            combo.getImgUrl(),
                            findListMenuByID(combo.getMenuIds()));
            responses.add(response);
        }
        return responses;

    }

    private boolean checkCombosAndMenus(List<MenuResponse> menus, List<Integer> idMenuCombos) {
        for (MenuResponse menu : menus) {
            if (menu.getId().equals(idMenuCombos)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String create(ComboRequest request) {
        List<String> urlImages = genUrlImage(request.getImgUrl(),request.getName(),path);
        String url = String.join(",",urlImages);
            Combo combo = new Combo
                    (request.getName(),
                            checkCode(request.getName()),
                            totalPriceMenu(request.getMenuIds()),
                            request.getDescription(),
                            url,
                            request.getMenuIds());
            comboRepository.save(combo);
            return "Create Success";
    }

    private Double totalPriceMenu(List<Integer> ids) {
       List<MenuResponse> responses = findListMenuByID(ids);
       Double initPrice = 0d;
        for (MenuResponse respons : responses) {
            initPrice += respons.getPrice();
        }
        return initPrice;
    }
    @Override
//    @CachePut(cacheNames = "Combo")
    public String update(Integer id, ComboRequest request) {
        List<String> urlImages = genUrlImage(request.getImgUrl(),request.getName(),path);
        String url = String.join(",",urlImages);
        try {
            Combo combo = comboRepository.findById(id).orElseThrow(() ->
                    new CommonException(Response.PARAM_INVALID, "Id NOT Exists,Cannot Update"));
            String oldFilePath = combo.getImgUrl();
            String[] urls = oldFilePath.split(",");
            try {
                for (int i = 0; i < urls.length; i++) {
                    Path oldFile = Paths.get(urls[i]);
                    Files.delete(oldFile);
                }
            } catch (IOException e) {
                throw new CommonException(Response.PARAM_INVALID, "Cannot delete old image file: " + oldFilePath);
            }
            Combo comboUpdate = combo;
            comboUpdate.setName(request.getName());
            comboUpdate.setPrice(totalPriceMenu(request.getMenuIds()));
            comboUpdate.setDescription(request.getDescription());
            comboUpdate.setImgUrl(url);
            comboUpdate.setMenuIds(request.getMenuIds());
            comboRepository.save(comboUpdate);
            return "Update Success";
        } catch (CommonException exception) {
            throw new CommonException(Response.PARAM_NOT_VALID, exception.getMessage());
        }
    }

    @Override
//    @CacheEvict(cacheNames = "Combo")
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

    @Override
    public String deleteListById(List<Integer> ids) {
            comboRepository.deleteAllById(ids);
            return "Delete Success";
    }
    private String checkCode(String comboName) {
        String code = gennareateCode(comboName);
        String codeBefore = code;
        Integer number = 0;
        while (comboRepository.findByCode(code).isPresent()) {
            number++;
            code = codeBefore+"-"+number;
        }
        return code;
    }
    private String gennareateCode(String comboName) {
        String[] arrMenuName = comboName.split(" ");
        String newCode= "";
        for (int i = 0; i < arrMenuName.length; i++) {
            newCode+=arrMenuName[i].substring(0,1).toUpperCase();
        }
        StringBuilder result = new StringBuilder();
        result.append("COMBO").append("-").append(newCode);
        return result.toString();
    }

    @Override
    public List<byte[]> readFileImg2(Integer id) {
        ComboResponse response = findById(id);
        return genfile.readFileContent2(response.getImgUrl(), path);
    }
}
