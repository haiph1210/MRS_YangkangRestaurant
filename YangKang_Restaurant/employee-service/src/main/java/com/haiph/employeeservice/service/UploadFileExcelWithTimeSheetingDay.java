package com.haiph.employeeservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileExcelWithTimeSheetingDay {
    String importFileExcel(MultipartFile file) throws IOException;
}
