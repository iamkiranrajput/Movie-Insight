package com.guardians.controller;


import com.guardians.exception.EmptyFileException;
import com.guardians.service.FileService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file/")
public class FileController {
    @Autowired
    private final FileService fileService;
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Value("${project.poster}")
    private String path;

    @PostMapping("upload")
    public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file) throws IOException, EmptyFileException {
        if(file.isEmpty()){
            throw new EmptyFileException("pleas add a file");
        }
        String uploadedFileName = fileService.uploadFile(path,file);
        return ResponseEntity.ok("File Uploaded "+uploadedFileName);
    }

    @GetMapping("/{fileName}")
    public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        InputStream resourceFile = fileService.getResourceFile(path,fileName);
        response.setContentType(String.valueOf(MediaType.ALL));
        StreamUtils.copy(resourceFile,response.getOutputStream());
    }

}
