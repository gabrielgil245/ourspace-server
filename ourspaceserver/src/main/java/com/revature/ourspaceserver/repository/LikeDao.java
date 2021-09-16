package com.revature.ourspaceserver.repository;

import com.revature.ourspaceserver.model.Like;
import com.revature.ourspaceserver.model.Post;
import com.revature.ourspaceserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("likeDao")
@Transactional
public interface LikeDao extends JpaRepository<Like, Integer> {
    Like findLikeByUserAndPost(User user, Post post);
    @Modifying
    @Query("Delete from Like where likeId = :likeId")
    void deleteLike(@Param("likeId") Integer likeId);

    List<Like> findLikeByUser(User user);
}
