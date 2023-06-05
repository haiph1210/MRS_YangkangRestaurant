package com.haiph.menuservice.mapper;

import com.haiph.menuservice.dto.request.VottingRequest;
import com.haiph.menuservice.dto.response.VottingResponse;
import com.haiph.menuservice.entity.Votting;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface VottingMapper {
   VottingResponse VottingToVottingResponse(Votting votting);
   List<VottingResponse> ListVottingToListVottingResponse(List<Votting> votting);

   Votting vottingRequestToVottingCreate(VottingRequest request);

   Votting vottingRequestToVottingUpdate(Integer id, VottingRequest request);
}
