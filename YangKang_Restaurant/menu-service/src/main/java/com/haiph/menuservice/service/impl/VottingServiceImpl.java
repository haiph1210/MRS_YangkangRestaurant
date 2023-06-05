package com.haiph.menuservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.exception.CommonException;
import com.haiph.menuservice.dto.request.VottingRequest;
import com.haiph.menuservice.dto.response.VottingResponse;
import com.haiph.menuservice.entity.Votting;
import com.haiph.menuservice.mapper.VottingMapper;
import com.haiph.menuservice.repository.MenuRepository;
import com.haiph.menuservice.repository.VottingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class VottingServiceImpl implements com.haiph.menuservice.service.VottingService {
    @Autowired
    private VottingRepository vottingRepository;
    @Autowired
    private VottingMapper mapper;
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Page<VottingResponse> findAllPage(Pageable pageable) {
    Page<Votting> page = vottingRepository.findAll(pageable);
    List<Votting> vottingList = page.getContent();
    List<VottingResponse> vottingResponses = mapper.ListVottingToListVottingResponse(vottingList);
    return new PageImpl<>(vottingResponses,pageable, page.getTotalElements());
    }

    @Override
    public VottingResponse findById(Integer id) {
        Votting votting = vottingRepository.findById(id).orElseThrow(() ->{throw new CommonException(Response.NOT_FOUND,"Cannot find Id: " +id);
        });
        return mapper.VottingToVottingResponse(votting);
    }

    @Override
    public String create(VottingRequest request) {
        Votting votting = mapper.vottingRequestToVottingCreate(request);
        vottingRepository.save(votting);
        Double totalStar = 0d;
        totalStar+=votting.getStar().getStar();
        Double initStar = totalInitStar(request.getMenuId(),totalStar);
        String totalStarInTotalUser = totalStarInTotalUser(request.getMenuId(),totalStar);
        menuRepository.updateStarAndTotalUser(initStar,totalStarInTotalUser,request.getMenuId());
        return "create success";
    }
    @Override
    public String update(Integer id, VottingRequest request) {
        VottingResponse vottingResponse = findById(id);
        Votting votting = mapper.vottingRequestToVottingUpdate(id,request);
        vottingRepository.save(votting);
        return "Update success";
    }

    @Override
    public String deleteById(Integer id) {
        VottingResponse vottingResponse = findById(id);
        vottingRepository.deleteById(id);
        return "delete success";
    }
    private Double totalInitStar(Integer id,Double totalStar) {
        Integer totalVote = vottingRepository.findTotalVote(id);
        Double initStar = totalStar / totalVote;
        return initStar;
    }
    private String totalStarInTotalUser(Integer id, Double totalStar) {
        Double initStar = totalInitStar(id, totalStar);
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        String roundedNumber = decimalFormat.format(initStar);
        Integer totalVote = vottingRepository.findTotalVote(id);
        StringBuilder result = new StringBuilder();
        result.append(roundedNumber).append("/").append(totalVote);
        return result.toString();
    }

}
