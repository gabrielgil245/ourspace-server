package com.revature.ourspaceserver.service;

import com.revature.ourspaceserver.model.Like;
import com.revature.ourspaceserver.model.Post;
import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.repository.LikeDao;
import com.revature.ourspaceserver.repository.PostDao;
import com.revature.ourspaceserver.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("likeService")
public class LikeService {
    LikeDao likeDao;
    UserDao userDao;
    PostDao postDao;

    @Autowired
    public LikeService(LikeDao likeDao, UserDao userDao, PostDao postDao) {
        this.likeDao = likeDao;
        this.userDao = userDao;
        this.postDao = postDao;
    }

    public List<Like> getAllLikes() {
        return this.likeDao.findAll();
    }

    //Get like based on userId and postId
    public Like getLikeBasedOnUserIdAndPostId(Like like) {
        User user = this.userDao.findById(like.getUser().getUserId()).orElse(null);
        like.setUser(user);
        Post post = this.postDao.findById(like.getPost().getPostId()).orElse(null);
        like.setPost(post);
        if(like == null) return null;
        like = this.likeDao.findLikeByUserAndPost(like.getUser(), like.getPost());
        return like;
    }

    //Get likes based on postId
    public List<Like> getLikeBasedOnPostId(Integer postId) {
        List<Like> likes = new ArrayList<>();
        Post post = this.postDao.findById(postId).orElse(null);
        if(post == null) return null;
        likes = this.likeDao.findLikeByPost(post);
        return likes;
    }

    //Liking/unliking a post
    public Like toggleLike(Like like) {
        User user = this.userDao.findById(like.getUser().getUserId()).orElse(null);
        Post post = this.postDao.findById(like.getPost().getPostId()).orElse(null);
        if(user == null || post == null) {
            return null;
        }
        Like liked = this.likeDao.findLikeByUserAndPost(user, post);
        if(liked != null) {
            this.likeDao.deleteLike(liked.getLikeId());
            return null;
        }else {
            like.setUser(user);
            like.setPost(post);
            return this.likeDao.save(like);
        }
    }
}
