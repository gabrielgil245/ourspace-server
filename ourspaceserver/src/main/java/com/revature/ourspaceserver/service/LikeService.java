package com.revature.ourspaceserver.service;

import com.revature.ourspaceserver.model.Like;
import com.revature.ourspaceserver.repository.LikeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("likeService")
public class LikeService {
    LikeDao likeDao;

    @Autowired
    public LikeService(LikeDao likeDao) {
        this.likeDao = likeDao;
    }

    public List<Like> getAllLikes() {
        return this.likeDao.findAll();
    }
}
