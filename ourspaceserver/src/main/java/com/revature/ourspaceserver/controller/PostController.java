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


    @PostMapping("post")
    public JsonResponse createPost(@RequestBody Post post) {
        return new JsonResponse(true, "Post successfully created", this.postService.createPost(post));
    }

    @GetMapping("post/{userId}")
    public List<Post> getAllPostsByUser(@PathVariable Integer userId) {
        return this.postService.getAllPostsByUser(userId);
    }

    @GetMapping("post/")
    public List<Post> getPostsByPageNumber(@RequestParam Integer pageNumber) {
        return this.postService.getPostsByPageNumber(pageNumber);
    }

    @GetMapping("post/{username}/{pageNumber}")
    public List<Post> getPostsByUserAndPageNumber(@PathVariable String username, @PathVariable Integer pageNumber) {
        return this.postService.getPostsByUserAndPageNumber(username, pageNumber);
    }
}
