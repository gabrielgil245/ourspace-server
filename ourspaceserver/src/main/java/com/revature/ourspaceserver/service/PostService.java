package com.revature.ourspaceserver.service;

import com.revature.ourspaceserver.model.Post;
import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.repository.PostDao;
import com.revature.ourspaceserver.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("postService")
public class PostService {
    UserDao userDao;
    PostDao postDao;

    @Autowired
    public PostService(PostDao postDao, UserDao userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

    public List<Post> getAllPosts() {
        return this.postDao.findAll();
    }

    public Post createPost(Post post) {
        User user = this.userDao.findById(post.getUser().getUserId()).orElse(null);
        post.setUser(user);
        return this.postDao.save(post);
    }
}
