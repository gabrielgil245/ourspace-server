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

    @GetMapping("post/{userId}")
    public List<Post> getAllPostsByUser(@PathVariable Integer userId) {
        return this.postService.getAllPostsByUser(userId);
    }

    @GetMapping("post/")
    public List<Post> getPostsByPageNumber(@RequestParam Integer pageNumber) {
        return this.postService.getPostsByPageNumber(pageNumber);
    }

    //Would like pageNumber to be a RequestParam (Query) instead
    // Encountering an IllegalStateException for having a PathParam and Query together
    @GetMapping("post/{userId}/{pageNumber}")
    public List<Post> getPostsByPageNumber(@PathVariable Integer userId, @PathVariable Integer pageNumber) {
        return this.postService.getPostsByUserAndPageNumber(userId, pageNumber);
    }
}
