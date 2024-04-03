package com.camunda.uploadfile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class UploadController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(path = "/")
    private void uploadFile(@RequestBody Map<String,Object> body) {


    }
}
