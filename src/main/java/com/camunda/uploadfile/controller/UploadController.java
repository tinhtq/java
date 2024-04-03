package com.camunda.uploadfile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.camunda.uploadfile.service.UploadMinIOService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
public class UploadController {
    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("{minio.key}")
    private String minioKey;

    @Value("{minio.secret}")
    private String minioSecret;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(path = "/")
    private void uploadFile(@RequestBody Map<String, Object> body)  {
        if (body.get("destination").getClass().getSimpleName() != "String") {
            System.out.println("Wrong type");
        }
        String dest = (String) body.get("destination");
        if (dest == "MinIO") {
            try {
                UploadMinIOService.FileUploaderService(endpoint, minioKey,minioSecret);
                
            } catch (InvalidKeyException | NoSuchAlgorithmException | IOException e) {
                    e.printStackTrace();
            }
        }

    }
}
