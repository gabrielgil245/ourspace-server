package com.revature.ourspaceserver.service;

import com.revature.ourspaceserver.model.Post;
import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.repository.PostDao;
import com.revature.ourspaceserver.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        System.out.println(post.getUser());
        User user = this.userDao.findById(post.getUser().getUserId()).orElse(null);
        if(user != null) {
            post.setUser(user);
            return this.postDao.save(post);
        }
        else
            return null;
    }

    public List<Post> getAllPostsByUser(Integer userId) {
        User user = this.userDao.findById(userId).orElse(null);
        if(user != null)
            return this.postDao.findPostByUser(user);

        return null;
    }

    public List<Post> getPostsByPageNumber(Integer pageNumber) {
        Integer postsToDisplay = 5;
        Long totalNumberOfPosts = this.postDao.count();
        Long postEnd = ((totalNumberOfPosts + 1) - ((pageNumber - 1) * postsToDisplay));
        Long postStart = ((totalNumberOfPosts - postsToDisplay)  - ((pageNumber - 1) * postsToDisplay));
        List<Post> posts = this.postDao.
                retrievePostsByOrderByPostSubmittedDesc(postStart.intValue(), postEnd.intValue());
        return posts;
    }

    public List<Post> getPostsByUserAndPageNumber(Integer userId, Integer pageNumber) {
        User user = this.userDao.findById(userId).orElse(null);
        if(user != null) {
            Integer postsToDisplay = 5;
            Long totalNumberOfPosts = this.postDao.count();
            Long postEnd = ((totalNumberOfPosts + 1) - ((pageNumber - 1) * postsToDisplay));
            Long postStart = ((totalNumberOfPosts - postsToDisplay) - ((pageNumber - 1) * postsToDisplay));
            List<Post> posts = this.postDao.
                    retrievePostByUserAndPageNumberOrderByPostSubmittedDesc(
                            user, postStart.intValue(), postEnd.intValue());
            return posts;
        }
        return null;
    }
}
