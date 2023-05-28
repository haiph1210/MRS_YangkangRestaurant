package com.haiph.common.uploadfile;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface UploadFile {
    String saveFile(MultipartFile file, String name, Path path);

    List<String> saveListFile(List<MultipartFile> files, String name,Path path);

    byte[] readFileContent(String fileName, Path path);

    List<byte[]> readFileContent2(String fileName, Path path);

    void delete(Path path);

    Stream<Path> loadAll(Path newPath);
}
