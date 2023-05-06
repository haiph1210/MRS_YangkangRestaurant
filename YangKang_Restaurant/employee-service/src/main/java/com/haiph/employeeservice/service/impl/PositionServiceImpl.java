package com.haiph.employeeservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.enums.status.emplService.empl.position.PositionEmpl;
import com.haiph.common.enums.status.emplService.empl.position.Salary;
import com.haiph.common.exception.CommonException;
import com.haiph.employeeservice.dto.request.PositionRequest;
import com.haiph.employeeservice.dto.response.PositionRessponse;
import com.haiph.employeeservice.entity.Position;
import com.haiph.employeeservice.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PositionServiceImpl implements com.haiph.employeeservice.service.PositionService {
    @Autowired
    private PositionRepository positionRepository;

    @Override
    public List<PositionRessponse> findAll() {
        List<Position> positions =  positionRepository.findAll();
        List<PositionRessponse> ressponses = new ArrayList<>();
        for (Position position : positions) {
            PositionRessponse ressponse = PositionRessponse
                    .build(position.getId(),position.getPosition(), position.getSalary());
            ressponses.add(ressponse);
        }
        return ressponses;
    }

    @Override
    public Page<PositionRessponse> findAll(Pageable pageable) {
        Page<Position> page =  positionRepository.findAll(pageable);
        List<Position> positions =  page.getContent();
        List<PositionRessponse> ressponses = new ArrayList<>();
        for (Position position : positions) {
            PositionRessponse ressponse = PositionRessponse
                    .build(position.getId(),position.getPosition(), position.getSalary());
            ressponses.add(ressponse);
        }
        return new PageImpl<>(ressponses,pageable,page.getTotalElements());
    }

    @Override
    public PositionRessponse findById(Integer id) {
        Position position = positionRepository.findById(id).orElseThrow(() -> {throw new CommonException(Response.NOT_FOUND,"Cannot find Id: " +id);
        });
        PositionRessponse ressponse = PositionRessponse
                .build(position.getId(),position.getPosition(), position.getSalary());
        return ressponse;
    }

    @Override
    public String create(PositionRequest request) {
        Position position = new Position(request.getPositionEmpl(),findSalary(request.getPositionEmpl()));
        positionRepository.save(position);
        return "Create Success" ;
    }
    private Double findSalary(PositionEmpl position) {
        Double firstSalary = 0d;
        switch (position) {
            case CHEF:
                firstSalary = Salary.CHEF;
                break;
            case SERVE:
                firstSalary = Salary.SERVE;
                break;
            case SERVICE:
                firstSalary = Salary.SERVICE;
                break;
            case MANAGER:
                firstSalary = Salary.MANAGER;
                break;
        }
        return firstSalary;}
    @Override
    public String update(Integer id, PositionRequest request) {
        PositionRessponse ressponse = findById(id);
        if (ressponse != null) {
            Position position = Position.build(id,request.getPositionEmpl(),findSalary(request.getPositionEmpl()));
            positionRepository.save(position);
            return "update Success" ;
        }
        return "update fail" ;
    }

    @Override
    public String delete(Integer id) {
        PositionRessponse ressponse = findById(id);
        if (ressponse != null) {
            positionRepository.deleteById(id);
            return "delete Success" ;
        }
        return "delete fail" ;
    }

}
