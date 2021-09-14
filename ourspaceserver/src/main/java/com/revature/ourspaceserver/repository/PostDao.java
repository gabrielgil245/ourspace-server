package com.revature.ourspaceserver.repository;

import com.revature.ourspaceserver.model.Post;
import com.revature.ourspaceserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository("postDao")
@Transactional
public interface PostDao extends JpaRepository<Post, Integer> {
    //For when the user selects another user to see their profile
    List<Post> findPostByUser(User user);
    //To filter the number of posts in a feed based on the page number
    @Query("From Post where postId > :postIdStart and postId < :postIdEnd")
    List<Post> retrievePostByPageNumber(
            @Param("postIdStart") Integer postIdStart,
            @Param("postIdEnd") Integer postIdEnd);

    //To filter the number of posts in another user's feed based on the page number
    @Query("From Post where user = :user and postId > :postIdStart and postId < :postIdEnd")
    List<Post> retrievePostByUserAndPageNumber(
            @Param("user") User user,
            @Param("postIdStart") Integer postIdStart,
            @Param("postIdEnd") Integer postIdEnd);
}
