package com.revature.ourspaceserver.controller;

import com.revature.ourspaceserver.model.JsonResponse;
import com.revature.ourspaceserver.model.Like;
import com.revature.ourspaceserver.model.Post;
import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("likeController")
@RequestMapping("/api")
@CrossOrigin(value=CrossOriginUtil.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class LikeController {
    LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("like")
    public JsonResponse getAllLikes() {
        return new JsonResponse(true, "Listing all likes", this.likeService.getAllLikes());
    }

    @GetMapping("like/post/{postId}")
    public JsonResponse getLikesByPostId(@PathVariable Integer postId) {
        return new JsonResponse(true, "Listing likes belonging to a post",
                this.likeService.getLikeBasedOnPostId(postId));
    }

    @PostMapping("like")
    public JsonResponse toggleLike(@RequestBody Like like) {
        JsonResponse jsonResponse;
        Like temp = this.likeService.toggleLike(like);
        if(temp != null)
            return new JsonResponse(true, "Like successfully created", temp);
        else
            return new JsonResponse(true, "Like successfully deleted", null);
    }

    @GetMapping("like/{userId}")
    public List<Like> getAllLikesByUser(@PathVariable Integer userId) {
        return likeService.getLikesBasedOnUser(userId);
    }
}
