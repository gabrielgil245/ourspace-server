package com.revature.ourspaceserver.service;

import com.revature.ourspaceserver.model.*;
import com.revature.ourspaceserver.repository.CommentDao;
import com.revature.ourspaceserver.repository.PostDao;
import com.revature.ourspaceserver.repository.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    CommentService commentService;
    @Mock
    CommentDao commentDao;
    @Mock
    UserDao userDao;
    @Mock
    PostDao postDao;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(commentDao, userDao, postDao);
    }

    @Test
    void getAllComments() {
        //Assign
        User user1 = new User(1, "jwick", "password", "John", "Wick",
                "test1@test.com", null, null, null);
        User user2 = new User(2, "wbrady", "password", "Wayne", "Brady",
                "test2@test.com", null, null, null);
        Post post1 = new Post(1, Timestamp.from(Instant.now()),"post description", "post image URL", "youtube URL",
                user1);
        Post post2 = new Post(2, Timestamp.from(Instant.now()),"post description", "post image URL", "youtube URL",
                user2);
        List<Comment> expectedResult = new ArrayList<>();
        expectedResult.add(new Comment(1, user1, post1, "test description", Timestamp.from(Instant.now())));
        expectedResult.add(new Comment(2, user2, post2, "test description", Timestamp.from(Instant.now())));
        Mockito.when(commentService.getAllComments()).thenReturn(expectedResult);

        //Act
        List<Comment> actualResult = this.commentService.getAllComments();

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createCommentWhenUserAndPostExist() {
        //Assign
        User user1 = new User(1, "jwick", "password", "John", "Wick",
                "test1@test.com", null, null, null);
        Post post1 = new Post(1, Timestamp.from(Instant.now()),"post description", "post image URL", "youtube URL",
                user1);
        Comment comment = new Comment(1, user1, post1, "test description", Timestamp.from(Instant.now()));

        Comment expectedResult = comment;
        Mockito.when(userDao.findById(comment.getUser().getUserId())).thenReturn(Optional.of(user1));
        Mockito.when(postDao.findById(comment.getPost().getPostId())).thenReturn(Optional.of(post1));
        Mockito.when(commentDao.save(comment)).thenReturn(expectedResult);

        //Act
        Comment actualResult = this.commentService.createComment(comment);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createCommentWhenPostDoesNotExist() {
        //Assign
        User user1 = new User(1, "jwick", "password", "John", "Wick",
                "test1@test.com", null, null, null);
        Post post1 = new Post(1, Timestamp.from(Instant.now()),"post description", "post image URL", "youtube URL",
                user1);
        Comment comment = new Comment(1, user1, post1, "test description", Timestamp.from(Instant.now()));

        Comment expectedResult = null;
        Mockito.when(userDao.findById(comment.getUser().getUserId())).thenReturn(Optional.of(user1));
        Mockito.when(postDao.findById(comment.getPost().getPostId())).thenReturn(Optional.empty());

        //Act
        Comment actualResult = this.commentService.createComment(comment);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createCommentWhenUserAndPostDoNotExist() {
        //Assign
        User user1 = new User(1, "jwick", "password", "John", "Wick",
                "test1@test.com", null, null, null);
        Post post1 = new Post(1, Timestamp.from(Instant.now()),"post description", "post image URL", "youtube URL",
                user1);
        Comment comment = new Comment(1, user1, post1, "test description", Timestamp.from(Instant.now()));

        Comment expectedResult = null;
        Mockito.when(userDao.findById(comment.getUser().getUserId())).thenReturn(Optional.empty());
        Mockito.when(postDao.findById(comment.getPost().getPostId())).thenReturn(Optional.empty());

        //Act
        Comment actualResult = this.commentService.createComment(comment);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getCommentsBasedOnPostIdWhenPostNotNull() {
        //Assign
        Integer postId = 1;
        User user1 = new User(1, "jwick", "password", "John", "Wick",
                "test1@test.com", null, null, null);
        User user2 = new User(2, "wbrady", "password", "Wayne", "Brady",
                "test2@test.com", null, null, null);
        Post post1 = new Post(1, Timestamp.from(Instant.now()),"post description", "post image URL", "youtube URL",
                user1);
        List<Comment> expectedResult = new ArrayList<>();
        expectedResult.add(new Comment(1, user1, post1, "test description", Timestamp.from(Instant.now())));
        expectedResult.add(new Comment(2, user2, post1, "test description", Timestamp.from(Instant.now())));
        Mockito.when(postDao.findById(postId)).thenReturn(Optional.of(post1));
        Mockito.when(commentDao.findCommentByPost(post1)).thenReturn(expectedResult);

        //Act
        List<Comment> actualResult = this.commentService.getCommentBasedOnPostId(postId);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getCommentsBasedOnPostIdWhenPostNull() {
        //Assign
        Integer postId = 1;
        List<Comment> expectedResult = null;
        Mockito.when(postDao.findById(postId)).thenReturn(Optional.empty());

        //Act
        List<Comment> actualResult = this.commentService.getCommentBasedOnPostId(postId);

        //Assert
        assertEquals(expectedResult, actualResult);
    }
}