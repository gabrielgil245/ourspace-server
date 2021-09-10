package com.revature.ourspaceserver.service;

import com.revature.ourspaceserver.model.Like;
import com.revature.ourspaceserver.model.Post;
import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.repository.LikeDao;
import com.revature.ourspaceserver.repository.PostDao;
import com.revature.ourspaceserver.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Like createLike(Like like) {
        User user = this.userDao.findById(like.getUser().getUserId()).orElse(null);
        like.setUser(user);
        Post post = this.postDao.findById(like.getPost().getPostId()).orElse(null);
        like.setPost(post);
        return this.likeDao.save(like);
    }
}
