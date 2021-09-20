package com.revature.ourspaceserver.controller;


import com.revature.ourspaceserver.model.JsonResponse;
import com.revature.ourspaceserver.service.S3Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController("s3Service")
@RequestMapping(value = "s3")
@CrossOrigin(value=CrossOriginUtil.CROSS_ORIGIN_VALUE)
public class S3Controller {

    S3Service s3Service;

    @Autowired
    public S3Controller(S3Service s3Service){
        this.s3Service = s3Service;
    }

    @PostMapping("signup")
    public JsonResponse postProfilePic(@RequestParam("imageFile")MultipartFile file) throws IOException {
        JsonResponse jsonResponse;
        String fileName = file.getOriginalFilename();
        byte[] fileBytes = file.getBytes();
        this.s3Service.uploadProfilePic(fileBytes,fileName);
        return new JsonResponse(true,"success", null);

    }

    @PostMapping("post")
    public JsonResponse postPostPic(@RequestParam("imageFile")MultipartFile file) throws IOException {
        JsonResponse jsonResponse;
        String fileName = file.getOriginalFilename();
        byte[] fileBytes = file.getBytes();
        this.s3Service.uploadPostPic(fileBytes, fileName);
        return new JsonResponse(true, "success", null);
    }
}
