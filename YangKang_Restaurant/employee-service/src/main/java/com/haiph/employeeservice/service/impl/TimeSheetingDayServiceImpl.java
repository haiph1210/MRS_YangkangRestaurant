package com.haiph.employeeservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.enums.status.emplService.empl.empl.Source;
import com.haiph.common.exception.CommonException;
import com.haiph.employeeservice.dto.request.TimeSheetingDayRequest;
import com.haiph.employeeservice.dto.response.PositionRessponse;
import com.haiph.employeeservice.dto.response.TimeSheetingDayResponse;
import com.haiph.employeeservice.entity.EmployeeId;
import com.haiph.employeeservice.entity.TimeSheetingDay;
import com.haiph.employeeservice.repository.TimeSheetingDayRepository;
import com.haiph.employeeservice.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeSheetingDayServiceImpl implements com.haiph.employeeservice.service.TimeSheetingDayService {
    @Autowired
    private TimeSheetingDayRepository dayRepository;
    @Autowired
    private PositionService positionService;

    private PositionRessponse findPositionById(Integer id) {
        return positionService.findById(id);
    }

    @Override
    public List<TimeSheetingDayResponse> findAll() {
        List<TimeSheetingDay> sheetingDays = dayRepository.findAll();
        List<TimeSheetingDayResponse> ressponses = new ArrayList<>();
        for (TimeSheetingDay sheetingDay : sheetingDays) {
            TimeSheetingDayResponse sheetingDayResponse = TimeSheetingDayResponse
                    .build(sheetingDay.getPersonCode(),
                            sheetingDay.getDateCheck(),
                            findPositionById(sheetingDay.getPositionId()),
                            sheetingDay.getDay(),
                            sheetingDay.getSource(),
                            sheetingDay.getNote());
            ressponses.add(sheetingDayResponse);
        }
        return ressponses;
    }

    @Override
    public Page<TimeSheetingDayResponse> findAll(Pageable pageable) {
        Page<TimeSheetingDay> page = dayRepository.findAll(pageable);
        List<TimeSheetingDay> sheetingDays = page.getContent();
        List<TimeSheetingDayResponse> ressponses = new ArrayList<>();
        for (TimeSheetingDay sheetingDay : sheetingDays) {
            TimeSheetingDayResponse sheetingDayResponse = TimeSheetingDayResponse
                    .build(sheetingDay.getPersonCode(),
                            sheetingDay.getDateCheck(),
                            findPositionById(sheetingDay.getPositionId()),
                            sheetingDay.getDay(),
                            sheetingDay.getSource(),
                            sheetingDay.getNote());
            ressponses.add(sheetingDayResponse);
        }
        return new PageImpl<>(ressponses, pageable, page.getTotalElements());
    }

    @Override
    public TimeSheetingDayResponse findById(EmployeeId id) {
        TimeSheetingDay sheetingDay = dayRepository.findById(id).orElseThrow(() -> {
            throw new CommonException(Response.NOT_FOUND, "Cannot find Id: " + id);
        });
        TimeSheetingDayResponse sheetingDayResponse = TimeSheetingDayResponse
                .build(sheetingDay.getPersonCode(),
                        sheetingDay.getDateCheck(),
                        findPositionById(sheetingDay.getPositionId()),
                        sheetingDay.getDay(),
                        sheetingDay.getSource(),
                        sheetingDay.getNote());
        return sheetingDayResponse;
    }

    @Override
    public String create(TimeSheetingDayRequest request) {
        TimeSheetingDay day = TimeSheetingDay.
                build(request.getPersonCode(),
                        request.getDateCheck(),
                        request.getPositionId(),
                        request.getDay(),
                        (request.getSource()),
                        request.getNote()
                );
        dayRepository.save(day);
        return "Create Success";
    }

    @Override
    public String update(TimeSheetingDayRequest request) {
        TimeSheetingDayResponse ressponse = findById(EmployeeId.buid(request.getPersonCode(), request.getDateCheck()));
        if (ressponse != null) {
            TimeSheetingDay day = TimeSheetingDay.
                    build(request.getPersonCode(),
                            request.getDateCheck(),
                            request.getPositionId(),
                            request.getDay(),
                            (request.getSource()),
                            request.getNote()
                    );
            dayRepository.save(day);
            return "update Success";
        }
        return "update fail";
    }

    @Override
    public String delete(EmployeeId id) {
        TimeSheetingDayResponse ressponse = findById(id);
        if (ressponse != null) {
            dayRepository.deleteById(id);
            return "delete Success";
        }
        return "delete fail";
    }
}
