package com.revature.ourspaceserver.repository;

import com.revature.ourspaceserver.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("postDao")
@Transactional
public interface PostDao extends JpaRepository<Post, Integer> {

}
