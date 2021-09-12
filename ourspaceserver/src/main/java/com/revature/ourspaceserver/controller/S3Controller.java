package com.revature.ourspaceserver.controller;


import com.revature.ourspaceserver.model.JsonResponse;
import com.revature.ourspaceserver.service.S3Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController("s3Service")
@RequestMapping(value = "s3")
@CrossOrigin(value="http://localhost:4200")
public class S3Controller {

    S3Service s3Service;

    @Autowired
    public S3Controller(S3Service s3Service){
        this.s3Service = s3Service;
    }

    @PostMapping("signup")
    public JsonResponse postProfilePic(@RequestParam("imageFile")MultipartFile file) throws IOException {
        JsonResponse jsonResponse;
        System.out.println("test");
        String fileName = file.getOriginalFilename();
        byte[] fileBytes = file.getBytes();

        this.s3Service.uploadFile(fileBytes,fileName);
        jsonResponse = new JsonResponse(true,"success", null);
        return jsonResponse;
    }
}
