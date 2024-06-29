package com.guardians.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileServiceImpl implements FileService {

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        // get name of the file
        String fileName=file.getOriginalFilename();

        //get the file path
        String filePath= path + File.separator+fileName;

        //file object
        File f=new File(path);

        if(!f.exists()){
           f.mkdir();
        }

        //copy the file  or upload file the path

        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
        String filePath= path+ File.separator+fileName;
        return new FileInputStream(filePath);
    }
}
