package com.revature.ourspaceserver.controller;

import com.revature.ourspaceserver.model.JsonResponse;
import com.revature.ourspaceserver.model.Like;
import com.revature.ourspaceserver.model.Post;
import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.service.LikeService;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LikeControllerTest {
    LikeController likeController;

    @Mock
    LikeService likeService;

    @BeforeEach
    void setUp() {
        this.likeController = new LikeController(likeService);
    }

    @Test
    void getAllLikes() {
        List<Like> likes = new ArrayList<>();
        User user = new User(1,"test","password","service","tests","test@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        User user2 = new User(2,"test2","password2","service2","tests2","test2@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        Post post = new Post(2, Timestamp.from(Instant.now()),"post Description2", "post Image URL2", "youtube URL2", user);
        likes.add(new Like(1,user,post));
        likes.add(new Like(2,user2,post));
        JsonResponse expected = new JsonResponse(true, "Listing all likes",likes);
        Mockito.when(likeService.getAllLikes()).thenReturn(likes);
        JsonResponse actual = likeController.getAllLikes();
        assertEquals(expected,actual);
    }

    @Test
    void toggleLikeDeletingLike() {
        JsonResponse expected = new JsonResponse(true, "Like successfully deleted", null);
        User user2 = new User(2,"test2","password2","service2","tests2","test2@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        Post post = new Post(2, Timestamp.from(Instant.now()),"post Description2", "post Image URL2", "youtube URL2", user2);
        Like like = new Like(1,user2,post);
        Mockito.when(likeService.toggleLike(like)).thenReturn(null);
        JsonResponse actual = likeController.toggleLike(like);
        assertEquals(expected,actual);
    }

    @Test
    void toggleLikeAddingLike() {
        User user2 = new User(2,"test2","password2","service2","tests2","test2@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        Post post = new Post(2, Timestamp.from(Instant.now()),"post Description2", "post Image URL2", "youtube URL2", user2);
        Like like = new Like(1,user2,post);
        JsonResponse expected = new JsonResponse(true, "Like successfully created", like);
        Mockito.when(likeService.toggleLike(like)).thenReturn(like);
        JsonResponse actual = likeController.toggleLike(like);
        assertEquals(expected,actual);
    }

    @Test
    void getLikesByPostId(){
        List<Like> likes = new ArrayList<>();
        User user = new User(1,"test","password","service","tests","test@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        User user2 = new User(2,"test2","password2","service2","tests2","test2@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        Post post = new Post(2, Timestamp.from(Instant.now()),"post Description2", "post Image URL2", "youtube URL2", user);
        likes.add(new Like(1,user,post));
        likes.add(new Like(2,user2,post));
        JsonResponse expected = new JsonResponse(true, "Listing likes belonging to a post", likes);
        Mockito.when(likeService.getLikeBasedOnPostId(2)).thenReturn(likes);
        JsonResponse actual = likeController.getLikesByPostId(2);
        assertEquals(expected,actual);
    }

    @Test
    void getLikesByUserId(){
        List<Like> expected = new ArrayList<>();
        User user = new User(1,"test","password","service","tests","test@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        Post post = new Post(2, Timestamp.from(Instant.now()),"post Description2", "post Image URL2", "youtube URL2", user);
        Post post2 = new Post(3, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL", user);
        expected.add(new Like(1,user,post));
        expected.add(new Like(2,user,post2));
        Mockito.when(likeService.getLikesBasedOnUser(1)).thenReturn(expected);
        List<Like> actual = likeController.getAllLikesByUser(1);
        assertEquals(expected,actual);
    }
}