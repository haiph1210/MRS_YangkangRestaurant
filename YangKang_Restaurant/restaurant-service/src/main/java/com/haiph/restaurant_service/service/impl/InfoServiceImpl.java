package com.haiph.restaurant_service.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.exception.CommonException;
import com.haiph.common.uploadfile.UploadFile;
import com.haiph.restaurant_service.dto.request.Info.InfoCreateRequest;
import com.haiph.restaurant_service.dto.request.Info.InfoUpdateRequest;
import com.haiph.restaurant_service.dto.response.InfoResponse;
import com.haiph.restaurant_service.entity.Info;
import com.haiph.restaurant_service.repository.InfoRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
import java.util.List;

@Service
public class InfoServiceImpl implements com.haiph.restaurant_service.service.InfoService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private InfoRepository infoRepository;
    @Autowired
    private UploadFile genfile;
    private Path path;
    public InfoServiceImpl() {
        path = Paths.get("restaurant-service/upload/img");
    }
    private String genUrlImage(MultipartFile file, String name, Path path) {
        return genfile.saveFile(file, name, path);
    }
    @Override
    public byte[] readFileImg(String fileName) {
        return genfile.readFileContent(fileName, path);
    }


    @Override
    public Page<InfoResponse> findAllPage(Pageable pageable) {
        Page<Info> page = infoRepository.findAll(pageable);
        List<Info> infos = page.getContent();
        List<InfoResponse> dtos = mapper.map(infos, new TypeToken<List<InfoResponse>>(){}.getType());
        return new PageImpl<>(dtos, pageable, page.getTotalPages());
    }

    @Override
    public InfoResponse findById(Integer id) {
        Info info = infoRepository.findById(id).orElseThrow(() -> {
            throw new CommonException(Response.NOT_FOUND, "Cannot find id: " + id);
        });
        return mapper.map(info, InfoResponse.class);
    }

    @Override
    public InfoResponse findByName(String name) {
        InfoResponse newInfo = null;
        List<Info> infos = infoRepository.findByName(name);
        if (infos.isEmpty()) {
            throw new CommonException(Response.NOT_FOUND, "Name: " + name + " haven't exist");
        } else {
            for (Info info : infos) {
                newInfo = findById(info.getId());
                return newInfo;
            }
        }
        return newInfo;
    }

    @Override
    public String create(InfoCreateRequest request) {
        String fileUrl = genUrlImage(request.getImgUrl(),request.getName(),path);
        if (request.getEmail() == null || request.getHostline() == null){
            throw new CommonException(Response.MISSING_PARAM,"Create False !");
        }
        Info info = new Info(
                request.getName(),
                request.getHostline(),
                request.getPhoneNumber(),
                request.getEmail(),
                request.getAddress(),
                request.getDescription(),
                request.getStar(),
                request.getCreatedAt(),
                fileUrl);
        infoRepository.save(info);
        return "Create Success";
    }

    @Override
    public String update(InfoUpdateRequest request) {
        String fileUrl = genUrlImage(request.getImgUrl(),request.getName(),path);
        InfoResponse response = findById(request.getId());
        if (response != null) {
            String oldFilePath = response.getImgUrl();
            try {
                Path oldFile = Paths.get(oldFilePath);
                Files.delete(oldFile);
            } catch (IOException e) {
                throw new CommonException(Response.PARAM_INVALID, "Cannot delete old image file: " + oldFilePath);
            }
            Info info = new Info(request.getId(),
                    request.getName(),
                    request.getHostline(),
                    request.getPhoneNumber(),
                    request.getEmail(),
                    request.getAddress(),
                    request.getDescription(),
                    request.getStar(),
                    request.getCreatedAt(),
                    fileUrl);
            infoRepository.save(info);
            return "Update success";
        }
        return "Update false";
    }
    @Override
    public String deleteById(Integer id) {
        InfoResponse response = findById(id) ;
            infoRepository.deleteById(id);
            return "delete success";
    }

}
