package com.revature.ourspaceserver.controller;

import com.revature.ourspaceserver.model.JsonResponse;
import com.revature.ourspaceserver.model.Post;
import com.revature.ourspaceserver.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("postController")
@RequestMapping("/api")
@CrossOrigin(value="http://localhost:4200", allowCredentials = "true")
public class PostController {
    PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("post")
    public JsonResponse getAllPosts() {
        return new JsonResponse(true, "listing all posts", this.postService.getAllPosts());
    }

    @PostMapping("post")
    public JsonResponse createPost(@RequestBody Post post) {
        return new JsonResponse(true, "Post successfully created", this.postService.createPost(post));
    }
}
