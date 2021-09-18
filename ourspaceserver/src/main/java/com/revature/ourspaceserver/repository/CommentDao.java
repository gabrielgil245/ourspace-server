package com.revature.ourspaceserver.repository;

import com.revature.ourspaceserver.model.Comment;
import com.revature.ourspaceserver.model.Like;
import com.revature.ourspaceserver.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("commentDao")
@Transactional
public interface CommentDao extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentByPost(Post post);

}
