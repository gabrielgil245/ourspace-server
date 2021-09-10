package com.revature.ourspaceserver.repository;

import com.revature.ourspaceserver.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("commentDao")
@Transactional
public interface CommentDao extends JpaRepository<Comment, Integer> {

}
