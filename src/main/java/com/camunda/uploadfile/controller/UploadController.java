package com.camunda.uploadfile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.camunda.uploadfile.service.UploadMinIOService;

import io.minio.MinioClient;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
public class UploadController {
    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.key}")
    private String minioKey;

    @Value("${minio.secret}")
    private String minioSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UploadMinIOService uploadMinIOService;

    @PostMapping(path = "/")
    private void uploadFile(@RequestParam("file1") MultipartFile document,
    @RequestParam("text") String text)  {
        System.out.println(endpoint);

            try {
                uploadMinIOService.FileUploaderService(endpoint, minioKey,minioSecret);
                System.out.println(uploadMinIOService.minioClient);
                uploadMinIOService.uploadFile("storage", "test.jpg", document);
                
            } catch (InvalidKeyException | NoSuchAlgorithmException | IOException e) {
                    e.printStackTrace();
            }
        }

    }

