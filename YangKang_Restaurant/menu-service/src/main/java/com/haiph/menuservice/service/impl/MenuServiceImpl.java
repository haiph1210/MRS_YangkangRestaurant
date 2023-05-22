package com.haiph.menuservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.dto.response.ResponseBody;
import com.haiph.common.exception.CommonException;
import com.haiph.common.uploadfile.UploadFile;
import com.haiph.menuservice.dto.form.SearchFormMenu;
import com.haiph.menuservice.dto.request.MenuRequest;
import com.haiph.menuservice.dto.response.MenuResponse;
import com.haiph.menuservice.entity.Combo;
import com.haiph.menuservice.entity.Menu;
import com.haiph.menuservice.repository.ComboRepository;
import com.haiph.menuservice.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements com.haiph.menuservice.service.MenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ComboRepository comboRepository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UploadFile genfile;
    private Path path;

    public MenuServiceImpl() {
        path = Paths.get("menu-service/upload/img/menu");
    }

    private List<String> genUrlImage(List<MultipartFile> files, String name, Path path) {
        return genfile.saveListFile(files, name, path);
    }
    private List<String> findAllImage(String fileName) {
        String[] listFileName = fileName.split(",");
        return Arrays.stream(listFileName).toList();
    }
    @Override
    public List<byte[]> readFileImg2(Integer id) {
        MenuResponse response = findById(id);
        return genfile.readFileContent2(response.getImgUrl(), path);
    }
    @Override
    public byte[] readFileImg(String fileName) {
        List<String> allImages = findAllImage(fileName);
        if (allImages.isEmpty() && allImages.size()>1){
            System.out.println("are you okey");
            for (String allImage : allImages) {

            }
        }
        return genfile.readFileContent(fileName, path);
    }

//    @Override
//    public List<byte[]> readFileImg2(String fileName) {
//        List<byte[]> newImage = new ArrayList<>();
//        List<String> allImages = findAllImage(fileName);
//        if (allImages.isEmpty() && allImages.size()>1){
//            System.out.println("are you okey");
//            for (String allImage : allImages) {
//                System.out.println("add");
//                byte[] newFileName = genfile.readFileContent(fileName, path);
//                newImage.add(newFileName);
//            }
//        }
//        return newImage;
//    }


    private String findComboName(Integer id) {

        Combo combo = comboRepository.findById(id).orElse(null);
        if (combo != null) {
            return combo.getName();
        } else throw new CommonException(Response.DATA_NOT_FOUND, "Cannot find Combo ID: " + id);
    }

    @Override
    public Map<Integer, List<MenuResponse>> findAll() {
        Map<Integer, List<MenuResponse>> findAll = new HashMap<>();
        List<Menu> menus = menuRepository.findAll();
        List<MenuResponse> menuResponses = new ArrayList<>();
        Integer number = 0;
        for (Menu menu : menus) {

            MenuResponse response = MenuResponse.build(menu.getId(), menu.getName(),menu.getPrice(), menu.getImgUrl(), menu.getDescription());
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
    public Page<MenuResponse> findAll(Pageable pageable) {
        Page<Menu> page = menuRepository.findAll(pageable);
        List<Menu> menus = page.getContent();
        List<MenuResponse> dtos = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu != null) {
                MenuResponse response = MenuResponse.build(menu.getId(), menu.getName(),menu.getPrice(), menu.getImgUrl(), menu.getDescription()
                );
                dtos.add(response);
            } else
                throw new CommonException(Response.DATA_NOT_FOUND, "List Menu Have Not Data");
        }
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @Override
    //   @Cacheable(cacheNames = "Menu")
    public List<MenuResponse> findForm(SearchFormMenu request) {
        List<Menu> menus = menuRepository.findWithForm(request.getSearch(), request.getMinPrice(), request.getMaxPrice(), request.getImgUrl(), request.getDescription());
        List<MenuResponse> dtos = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu != null) {
                MenuResponse response = MenuResponse.build(menu.getId(), menu.getName(),menu.getPrice(), menu.getImgUrl(), menu.getDescription());
                dtos.add(response);
            } else
                throw new CommonException(Response.DATA_NOT_FOUND, "Search With Form Haven't Data");
        }
        return dtos;
    }

    private String findMenuNameByID(Integer id) {
        return menuRepository.findById(id).get().getName();
    }

    @Override
    public List<MenuResponse> findByListId(List<Integer> ids) {
        List<MenuResponse> responses = new ArrayList<>();
        List<Menu> menus = menuRepository.findByListId(ids);
        for (Menu menu : menus) {
            MenuResponse response = MenuResponse.
                    build(menu.getId(),
                            menu.getName(),menu.getPrice(),

                            menu.getImgUrl(),
                            menu.getDescription());
            responses.add(response);
        }
        return responses;
    }

    @Override
    //   @Cacheable(cacheNames = "Menu")
    public MenuResponse findById(Integer id) {
        try {
            Menu menu = menuRepository.findById(id).orElse(null);
            if (menu != null) {
                MenuResponse response = MenuResponse.build(menu.getId(), menu.getName(),menu.getPrice(), menu.getImgUrl(), menu.getDescription());
                return response;
            } else
                throw new CommonException(Response.DATA_NOT_FOUND, "Menu cannot having Id: " + id);

        } catch (CommonException exception) {
            throw new CommonException(Response.SYSTEM_ERROR, exception.getMessage());
        }

    }

    @Override
    //    @Cacheable(cacheNames = "Menu")
    public List<MenuResponse> findByName(String name) {
        List<Menu> menus = menuRepository.findByName(name);
        List<MenuResponse> menuResponses = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu != null) {
                MenuResponse response = MenuResponse.build(menu.getId(), menu.getName(),menu.getPrice(), menu.getImgUrl(), menu.getDescription());
                menuResponses.add(response);
            } else
                throw new CommonException(Response.DATA_NOT_FOUND, "Menu haven't name: " + name);
        }
        return menuResponses;
    }

    @Override
//    @Cacheable(cacheNames = "Menu")
    public List<MenuResponse> findByPrice(Double price) {
        List<Menu> menus = menuRepository.findByPrice(price);
        List<MenuResponse> menuResponses = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu != null) {
                MenuResponse response = MenuResponse.build(menu.getId(), menu.getName(),menu.getPrice(), menu.getImgUrl(), menu.getDescription());
                menuResponses.add(response);
            } else
                throw new CommonException(Response.DATA_NOT_FOUND, "Menu haven't price: " + price);
        }
        return menuResponses;
    }


    @Override
    public String create(MenuRequest menuRequest) {
        List<String> urlImages = genUrlImage(menuRequest.getImgUrl(), menuRequest.getName(), path);
        String url = String.join(",", urlImages);
        Menu menu = new Menu(menuRequest.getName(),
                menuRequest.getPrice(),
                url,
                menuRequest.getDescription());
        menuRepository.save(menu);

        return "create Success";
    }

    private Double updateComboPrice(Integer id, Double menuPrice) {
        Combo combo = comboRepository.findById(id).orElseThrow(() -> {
            throw new CommonException(Response.NOT_FOUND, "NO DATA WITH COMBO ID: " + id);
        });
        Double price = combo.getPrice();
        Double initPrice = 0d;
        initPrice += menuPrice;
        return initPrice;
    }

    @Override
    public String update(Integer id, MenuRequest menuRequest) {
        List<String> urlImages = genUrlImage(menuRequest.getImgUrl(), menuRequest.getName(), path);
        String url = String.join(",", urlImages);
        try {
            Menu menu = menuRepository.findById(id).orElseThrow(() ->
                    new CommonException(Response.PARAM_INVALID, "Id NOT Exists,Cannot Update"));
            if (menu != null) {
                String oldFilePath = menu.getImgUrl();
                String[] urls = oldFilePath.split(",");
                try {
                    for (int i = 0; i < urls.length; i++) {
                        Path oldFile = Paths.get(urls[i]);
                        Files.delete(oldFile);
                    }
                } catch (IOException e) {
                    throw new CommonException(Response.PARAM_INVALID, "Cannot delete old image file: " + oldFilePath);
                }
                Menu menuUpdate = menu;
                menuUpdate.setName(menuRequest.getName());
                menuUpdate.setPrice(menuRequest.getPrice());
                menuUpdate.setDescription(menuRequest.getDescription());
                menuUpdate.setImgUrl(url);
                menuRepository.save(menuUpdate);
                return "Update Success";
            }

        } catch (CommonException exception) {
            throw new CommonException(Response.PARAM_NOT_VALID, exception.getMessage());
        }
        return null;
    }

    @Override
    //   @CacheEvict(cacheNames = "Menu")
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

    @Override
    public String deleteByListId(List<Integer> ids) {
        try {
            menuRepository.deleteAllById(ids);
            return "Delete " + ids + " Success ";
        } catch (CommonException exception) {
            throw new CommonException(Response.PARAM_NOT_VALID, exception.getMessage());
        }
    }


}
