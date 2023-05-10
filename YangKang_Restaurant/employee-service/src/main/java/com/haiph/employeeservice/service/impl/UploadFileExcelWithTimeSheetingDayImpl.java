package com.haiph.employeeservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.enums.status.emplService.empl.empl.Source;
import com.haiph.common.enums.status.emplService.empl.empl.WorkingDay;
import com.haiph.common.enums.status.emplService.empl.position.PositionEmpl;
import com.haiph.common.exception.CommonException;
import com.haiph.employeeservice.dto.request.TimeSheetingDayRequest;
import com.haiph.employeeservice.dto.response.PositionRessponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
;
import com.haiph.employeeservice.service.TimeSheetingDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.ConnectException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class UploadFileExcelWithTimeSheetingDayImpl implements com.haiph.employeeservice.service.UploadFileExcelWithTimeSheetingDay {
    @Autowired
    private TimeSheetingDayService dayService;
    @Autowired
    private PositionServiceImpl positionService;


    @Override
    public String importFileExcel(MultipartFile file) throws IOException {
        Map<String, Integer> positionMap = new HashMap<>();
        List<PositionRessponse> positions = positionService.findAll();
        for (PositionRessponse position : positions) {
            positionMap.put(position.getPosition().getDescription(), position.getId());
        }
        List<TimeSheetingDayRequest> requests = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        Row headerRow = rowIterator.next();
        List<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            headers.add(cell.getStringCellValue());
        }
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            TimeSheetingDayRequest create = new TimeSheetingDayRequest();
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = row.getCell(i);
                String header = headers.get(i);
                if (header.equalsIgnoreCase("Mã Nhân Viên")) {
                    create.setPersonCode(cell.getStringCellValue());
                } else if (header.equalsIgnoreCase("Ngày")) {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date dateValue = cell.getDateCellValue();
                        LocalDate localDate = dateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        System.out.println("localDate = " + localDate);
                        create.setDateCheck(localDate);
                    } else {
                        throw new CommonException(Response.PARAM_NOT_VALID, "Date check not valid");
                    }
                } else if (header.equalsIgnoreCase("Vị Trí")) {
                    String positionValue = cell.getStringCellValue();
                    Integer positionId = positionMap.get(positionValue);
                    create.setPositionId(positionId);
                } else if (header.equalsIgnoreCase("Ngày Làm Việc")) {
                    String workingTimeValue = cell.getStringCellValue();
                    WorkingDay workingDay;
                    if (workingTimeValue.equalsIgnoreCase("Cả Ngày")) {
                        workingDay = WorkingDay.FULL;
                    } else if (workingTimeValue.equalsIgnoreCase("Nửa Ngày")) {
                        workingDay = WorkingDay.HALF_DAY;
                    } else {
                        workingDay = WorkingDay.OFF;
                    }
                    create.setDay(workingDay);
                    create.setSource(Source.QUAN_LI_CHAM_CONG);
                } else if (header.equalsIgnoreCase("Ghi Chú")) {
                    create.setNote(cell.getStringCellValue());
                }
            }
            dayService.create(create);
        }
        return "oke";

    }
}
