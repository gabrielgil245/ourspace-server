package com.revature.ourspaceserver.service;

import com.revature.ourspaceserver.model.Comment;
import com.revature.ourspaceserver.model.Like;
import com.revature.ourspaceserver.model.Post;
import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.repository.CommentDao;
import com.revature.ourspaceserver.repository.PostDao;
import com.revature.ourspaceserver.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("commentService")
public class CommentService {
    CommentDao commentDao;
    UserDao userDao;
    PostDao postDao;

    @Autowired
    public CommentService(CommentDao commentDao, UserDao userDao, PostDao postDao) {
        this.commentDao = commentDao;
        this.userDao = userDao;
        this.postDao = postDao;
    }

    public List<Comment> getAllComments() {
        return this.commentDao.findAll();
    }

    public Comment createComment(Comment comment) {
        User user = this.userDao.findById(comment.getUser().getUserId()).orElse(null);
        Post post = this.postDao.findById(comment.getPost().getPostId()).orElse(null);
        if(user == null || post == null) {
            return null;
        }
        comment.setUser(user);
        comment.setPost(post);
        return this.commentDao.save(comment);
    }

    public List<Comment> getCommentBasedOnPostId(Integer postId) {
        List<Comment> comments = new ArrayList<>();
        Post post = this.postDao.findById(postId).orElse(null);
        if(post == null) return null;
        comments = this.commentDao.findCommentByPost(post);
        return comments;
    }
}
