package com.revature.ourspaceserver.controller;

import com.revature.ourspaceserver.model.JsonResponse;
import com.revature.ourspaceserver.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("likeController")
@RequestMapping("/api")
@CrossOrigin(value="http://localhost:4200", allowCredentials = "true")
public class LikeController {
    LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("like")
    public JsonResponse getAllPosts() {
        return new JsonResponse(true, "listing all likes", this.likeService.getAllLikes());
    }
}
