package com.revature.ourspaceserver.service;

import com.revature.ourspaceserver.model.Comment;
import com.revature.ourspaceserver.repository.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("commentService")
public class CommentService {
    CommentDao commentDao;

    @Autowired
    public CommentService(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public List<Comment> getAllComments() {
        return this.commentDao.findAll();
    }
}
