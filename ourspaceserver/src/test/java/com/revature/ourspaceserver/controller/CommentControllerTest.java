package com.revature.ourspaceserver.controller;

import com.revature.ourspaceserver.model.Comment;
import com.revature.ourspaceserver.model.JsonResponse;
import com.revature.ourspaceserver.model.Post;
import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.service.CommentService;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    CommentController commentController;
    @Mock
    CommentService commentService;

    @BeforeEach
    void setUp() {
        commentController = new CommentController(commentService);
    }

    @Test
    void getAllComments() {
        //Assign
        User user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        Post post = new Post(1, Timestamp.from(Instant.now()),"post description", "post image URL", "youtube URL",
                user);
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1, user, post, "test description", Timestamp.from(Instant.now())));
        JsonResponse expectedResult = new JsonResponse(true, "listing all comments", comments);
        Mockito.when(commentService.getAllComments()).thenReturn(comments);

        //Act
        JsonResponse actualResult = this.commentController.getAllComments();

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createComment() {
        //Assign
        User user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        Post post = new Post(1, Timestamp.from(Instant.now()),"post description", "post image URL", "youtube URL",
                user);
        Comment comment = new Comment(1, user, post, "test description", Timestamp.from(Instant.now()));
        JsonResponse expectedResult = new JsonResponse(true, "Comment successfully created", comment);
        Mockito.when(commentService.createComment(comment)).thenReturn(comment);

        //Act
        JsonResponse actualResult = this.commentController.createComment(comment);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getCommentsByPostId() {
        //Assign
        User user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        Post post = new Post(1, Timestamp.from(Instant.now()),"post description", "post image URL", "youtube URL",
                user);
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1, user, post, "test description", Timestamp.from(Instant.now())));
        JsonResponse expectedResult = new JsonResponse(true, "Listing comments belonging to a post", comments);
        Mockito.when(commentService.getCommentBasedOnPostId(post.getPostId())).thenReturn(comments);

        //Act
        JsonResponse actualResult = this.commentController.getCommentsByPostId(post.getPostId());

        //Assert
        assertEquals(expectedResult, actualResult);
    }
}