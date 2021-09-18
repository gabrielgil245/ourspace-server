package com.revature.ourspaceserver.controller;

import com.revature.ourspaceserver.model.JsonResponse;
import com.revature.ourspaceserver.model.Like;
import com.revature.ourspaceserver.model.Post;
import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    PostController postController;

    @Mock
    PostService postService;


    @BeforeEach
    void setUp() {
        this.postController = new PostController(postService);
    }

    @Test
    void createPost() {
        User user = new User(1,"test","password","service","tests","test@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        Post post = new Post(2, Timestamp.from(Instant.now()),"post Description2", "post Image URL2", "youtube URL2", user);
        JsonResponse expected = new JsonResponse(true, "Post successfully created", post);
        Mockito.when(postService.createPost(post)).thenReturn(post);
        JsonResponse actual = postController.createPost(post);
        assertEquals(expected, actual);
    }

    @Test
    void getAllPostsByUser() {
        List<Post> expected = new ArrayList<>();
        User user = new User(1,"test","password","service","tests","test@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        Post post = new Post(2, Timestamp.from(Instant.now()),"post Description2", "post Image URL2", "youtube URL2", user);
        Post post2 = new Post(3, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL", user);
        expected.add(post);
        expected.add(post2);
        Mockito.when(postService.getAllPostsByUser(1)).thenReturn(expected);
        List<Post> actual = postController.getAllPostsByUser(1);
    }

    @Test
    void getPostsByPageNumber() {
        List<Post> expected = new ArrayList<>();
        User user = new User(1,"test","password","service","tests","test@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        Post post = new Post(2, Timestamp.from(Instant.now()),"post Description2", "post Image URL2", "youtube URL2", user);
        Post post2 = new Post(3, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL", user);
        expected.add(post2);
        Mockito.when(postService.getPostsByPageNumber(1)).thenReturn(expected);
        List<Post> actual = postController.getPostsByPageNumber(1);
        assertEquals(expected,actual);
    }

    @Test
    void testGetPostsByPageNumber() {
        List<Post> expected = new ArrayList<>();
        User user = new User(1,"test","password","service","tests","test@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        User user2 = new User(2,"test2","password2","service2","tests2","test2@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        Post post = new Post(2, Timestamp.from(Instant.now()),"post Description2", "post Image URL2", "youtube URL2", user);
        Post post2 = new Post(3, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL", user2);
        expected.add(post2);
        Mockito.when(postService.getPostsByUserAndPageNumber(1,1)).thenReturn(expected);
        List<Post> actual = postController.getPostsByPageNumber(1,1);
        assertEquals(expected,actual);
    }
}