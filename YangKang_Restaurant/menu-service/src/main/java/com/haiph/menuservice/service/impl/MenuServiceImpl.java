package com.haiph.menuservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.dto.response.ResponseBody;
import com.haiph.common.enums.status.restaurantService.RestaurantStar;
import com.haiph.common.exception.CommonException;
import com.haiph.common.uploadfile.UploadFile;
import com.haiph.menuservice.dto.form.FormChooseStar;
import com.haiph.menuservice.dto.form.SearchFormMenu;
import com.haiph.menuservice.dto.request.MenuRequest;
import com.haiph.menuservice.dto.response.MenuResponse;
import com.haiph.menuservice.dto.response.restApi.APIResponse2;
import com.haiph.menuservice.dto.response.restApi.PersonResponse;
import com.haiph.menuservice.entity.Combo;
import com.haiph.menuservice.entity.Menu;
import com.haiph.menuservice.entity.Votting;
import com.haiph.menuservice.feignClient.PersonController;
import com.haiph.menuservice.repository.ComboRepository;
import com.haiph.menuservice.repository.MenuRepository;
import com.haiph.menuservice.repository.VottingRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class MenuServiceImpl implements com.haiph.menuservice.service.MenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private VottingRepository vottingRepository;
    @Autowired
    private PersonController personController;
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

    private String findFullnameByPersonCode(String personCode) {
        APIResponse2<PersonResponse> response = personController.findByPersonCode(personCode);
        return response.getResponseData().getFullName();
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
        if (allImages.isEmpty() && allImages.size() > 1) {
            System.out.println("are you okey");
            for (String allImage : allImages) {

            }
        }
        return genfile.readFileContent(fileName, path);
    }


    private String findComboName(Integer id) {

        Combo combo = comboRepository.findById(id).orElse(null);
        if (combo != null) {
            return combo.getName();
        } else throw new CommonException(Response.DATA_NOT_FOUND, "Cannot find Combo ID: " + id);
    }


//    @Override
//    public List<MenuResponse> findAll() {
//        List<Menu> menus = menuRepository.findAll();
//        if (menus.isEmpty()) {
//            throw new CommonException(Response.DATA_NOT_FOUND, "List Menu Has No Data");
//        }
//
//        List<MenuResponse> menuResponses = new ArrayList<>();
//        for (Menu menu : menus) {
//            List<MenuResponse.Votting> vottings = new ArrayList<>();
//            for (Votting votting : menu.getVottings()) {
//                MenuResponse.Votting vottingResponse = MenuResponse.Votting.builder()
//                        .star(votting.getStar())
//                        .fullName(findFullnameByPersonCode(votting.getUserCode()))
//                        .build();
//                vottings.add(vottingResponse);
//            }
//
//            MenuResponse menuResponse = MenuResponse.builder()
//                    .id(menu.getId())
//                    .name(menu.getName())
//                    .price(menu.getPrice())
//                    .imgUrl(menu.getImgUrl())
//                    .description(menu.getDescription())
//                    .totalStar(menu.getInitStar())
//                    .vottings(vottings)
//                    .build();
//            menuResponses.add(menuResponse);
//        }
//        return menuResponses;
//    }


    @Override
    public Page<MenuResponse> findAll(Pageable pageable) {
        Page<Menu> page = menuRepository.findAll(pageable);
        List<Menu> menus = page.getContent();
        List<MenuResponse> menuResponses = new ArrayList<>();
        for (Menu menu : menus) {
            List<MenuResponse.Votting> vottings = new ArrayList<>();
            for (Votting votting : menu.getVottings()) {

                MenuResponse.Votting vottingResponse = MenuResponse.Votting.builder()
                        .star(votting.getStar())
                        .fullName(findFullnameByPersonCode(votting.getUserCode()))
                        .build();
                vottings.add(vottingResponse);
            }

            MenuResponse menuResponse = MenuResponse.builder()
                    .id(menu.getId())
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .imgUrl(menu.getImgUrl())
                    .description(menu.getDescription())
                    .totalStar(menu.getInitStar())
                    .totalStarInTotalUser(menu.getTotalStarInTotalUser())
                    .vottings(vottings)

                    .build();
            menuResponses.add(menuResponse);
        }
        return new PageImpl<>(menuResponses, pageable, page.getTotalElements());
    }
    private Double totalVoting(Integer id,Double votting) {
        MenuResponse response = findById(id);
        Double vottingTotal = 0d;
        if (response!= null) {
            vottingTotal = votting;
        }
        return vottingTotal;
    }

    @Override
    //   @Cacheable(cacheNames = "Menu")
    public List<MenuResponse> findForm(SearchFormMenu request) {
        List<Menu> menus = menuRepository.findWithForm(request.getSearch(), request.getMinPrice(), request.getMaxPrice(), request.getImgUrl(), request.getDescription());
        List<MenuResponse> dtos = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu != null) {
                List<MenuResponse.Votting> vottings = new ArrayList<>();
                for (Votting votting : menu.getVottings()) {
                    MenuResponse.Votting vottingResponse = MenuResponse.Votting.builder()
                            .star(votting.getStar())
                            .fullName(findFullnameByPersonCode(votting.getUserCode()))
                            .build();
                    vottings.add(vottingResponse);
                }
                MenuResponse menuResponse = MenuResponse.builder()
                        .id(menu.getId())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .imgUrl(menu.getImgUrl())
                        .description(menu.getDescription())
                        .totalStar(menu.getInitStar())
                        .vottings(vottings)
                        .build();
                dtos.add(menuResponse);
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
            List<MenuResponse.Votting> vottings = new ArrayList<>();
            for (Votting votting : menu.getVottings()) {
                MenuResponse.Votting vottingResponse = MenuResponse.Votting.builder()
                        .star(votting.getStar())
                        .fullName(findFullnameByPersonCode(votting.getUserCode()))
                        .build();
                vottings.add(vottingResponse);
            }
            MenuResponse menuResponse = MenuResponse.builder()
                    .id(menu.getId())
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .imgUrl(menu.getImgUrl())
                    .description(menu.getDescription())
                    .totalStar(menu.getInitStar())
                    .vottings(vottings)
                    .build();
            responses.add(menuResponse);
        }
        return responses;
    }

    @Override
    //   @Cacheable(cacheNames = "Menu")
    public MenuResponse findById(Integer id) {
        try {
            Menu menu = menuRepository.findById(id).orElse(null);
            if (menu != null) {
                List<MenuResponse.Votting> vottings = new ArrayList<>();
                for (Votting votting : menu.getVottings()) {
                    MenuResponse.Votting vottingResponse = MenuResponse.Votting.builder()
                            .star(votting.getStar())
                            .fullName(findFullnameByPersonCode(votting.getUserCode()))
                            .build();
                    vottings.add(vottingResponse);
                }
                MenuResponse menuResponse = MenuResponse.builder()
                        .id(menu.getId())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .imgUrl(menu.getImgUrl())
                        .description(menu.getDescription())
                        .totalStar(menu.getInitStar())
                        .vottings(vottings)
                        .build();
                return menuResponse;
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
                List<MenuResponse.Votting> vottings = new ArrayList<>();
                for (Votting votting : menu.getVottings()) {
                    MenuResponse.Votting vottingResponse = MenuResponse.Votting.builder()
                            .star(votting.getStar())
                            .fullName(findFullnameByPersonCode(votting.getUserCode()))
                            .build();
                    vottings.add(vottingResponse);
                }
                MenuResponse menuResponse = MenuResponse.builder()
                        .id(menu.getId())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .imgUrl(menu.getImgUrl())
                        .description(menu.getDescription())
                        .totalStar(menu.getInitStar())
                        .vottings(vottings)
                        .build();
                menuResponses.add(menuResponse);
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
                List<MenuResponse.Votting> vottings = new ArrayList<>();
                for (Votting votting : menu.getVottings()) {
                    MenuResponse.Votting vottingResponse = MenuResponse.Votting.builder()
                            .star(votting.getStar())
                            .fullName(findFullnameByPersonCode(votting.getUserCode()))
                            .build();
                    vottings.add(vottingResponse);
                }
                MenuResponse menuResponse = MenuResponse.builder()
                        .id(menu.getId())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .imgUrl(menu.getImgUrl())
                        .description(menu.getDescription())
                        .totalStar(menu.getInitStar())
                        .vottings(vottings)
                        .build();
                menuResponses.add(menuResponse);
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
                checkCode(menuRequest.getName()),
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
    @Transactional
    public String update(Integer id, MenuRequest menuRequest) {
        try {
            Menu menu = menuRepository.findById(id).orElseThrow(() ->
                    new CommonException(Response.PARAM_INVALID, "Id NOT Exists,Cannot Update"));
            if (menu != null) {
                String oldFilePath = menu.getImgUrl();
                String[] urls = oldFilePath.split(",");
                for (int i = 0; i < urls.length; i++) {
                    Path oldFile = Paths.get(urls[i]);
                    genfile.delete(oldFile);
                }
                List<String> urlImages = genUrlImage(menuRequest.getImgUrl(), menuRequest.getName(), path);
                String url = String.join(",", urlImages);
                Menu menuUpdate = menu;
                menuUpdate.setName(menuRequest.getName());
                menuUpdate.setCode(checkCode(menuUpdate.getName()));
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
    public String deleteById(Integer id) {
        try {
            Menu menu = menuRepository.findById(id).orElse(null);
            if (menu != null) {
                String oldFilePath = menu.getImgUrl();
                String[] urls = oldFilePath.split(",");
                for (int i = 0; i < urls.length; i++) {
                    Path oldFile = Paths.get(urls[i]);
                    genfile.delete(oldFile);
                }
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
    private String checkCode(String menuName) {
        String code = gennareateCode(menuName);
        String codeBefore = code;
        Integer number = 0;
        while (menuRepository.findByCode(code).isPresent()) {
            number++;
            code = codeBefore+"-"+number;
        }
        return code;
    }
    private String gennareateCode(String menuName) {
        String[] arrMenuName = menuName.split(" ");
        String newCode= "";
        for (int i = 0; i < arrMenuName.length; i++) {
            newCode+=arrMenuName[i].substring(0,1).toUpperCase();
        }
        StringBuilder result = new StringBuilder();
        result.append("MENU").append("-").append(newCode);
        return result.toString();

    }


}
