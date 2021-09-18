package com.revature.ourspaceserver.controller;

import com.revature.ourspaceserver.model.Comment;
import com.revature.ourspaceserver.model.JsonResponse;
import com.revature.ourspaceserver.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("commentController")
@RequestMapping("/api")
@CrossOrigin(value="http://localhost:4200", allowCredentials = "true")
public class CommentController {
    CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("comment")
    public JsonResponse getAllComments() {
        return new JsonResponse(true, "listing all comments", this.commentService.getAllComments());
    }

    @PostMapping("comment")
    public JsonResponse createComment(@RequestBody Comment comment) {
        return new JsonResponse(true, "Comment successfully created", this.commentService.createComment(comment));
    }

    @GetMapping("comment/{postId}")
    public JsonResponse getCommentsByPostId(@PathVariable Integer postId) {
        return new JsonResponse(true, "Listing comments belonging to a post",
                this.commentService.getCommentBasedOnPostId(postId));
    }
}
