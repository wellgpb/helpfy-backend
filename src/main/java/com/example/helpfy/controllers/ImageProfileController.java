package com.example.helpfy.controllers;

import com.example.helpfy.dtos.ImageUploadResponse;
import com.example.helpfy.utils.FileUploadUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/upload-image")
@CrossOrigin
public class ImageProfileController {

    @PostMapping
    public ResponseEntity<ImageUploadResponse> uploadImage(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        String fileName = new Date().getTime() + "-" + StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String uploadDir = "pictures/";

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        String url = "/" + uploadDir + fileName;
        return new ResponseEntity<>(new ImageUploadResponse(url), HttpStatus.CREATED);
    }
}
