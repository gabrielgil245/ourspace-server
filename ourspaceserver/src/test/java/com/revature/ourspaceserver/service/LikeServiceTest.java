package com.revature.ourspaceserver.service;

import com.revature.ourspaceserver.model.Like;
import com.revature.ourspaceserver.model.Post;
import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.repository.LikeDao;
import com.revature.ourspaceserver.repository.PostDao;
import com.revature.ourspaceserver.repository.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LikeServiceTest {
    LikeService likeService;

    @Mock
    LikeDao likeDao;

    @Mock
    UserDao userDao;

    @Mock
    PostDao postDao;

    @BeforeEach
    void setUp() {
        likeService = new LikeService(likeDao, userDao, postDao);
    }

    @Test
    void getAllLikes() {
        //Assign
        User user;
        user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        Post post;
        post = new Post(1, null, "testing like service", null, null, user);
        Like like;
        like = new Like(1, user, post);
        List<Like> expectedResult = new ArrayList<>();
        expectedResult.add(like);
        Mockito.when(this.likeDao.findAll()).thenReturn(expectedResult);

        //Act
        List<Like> actualResult = this.likeService.getAllLikes();

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getLikeBasedOnUserIdAndPostIdWhenNotNull() {
        //Assign
        User user;
        user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        Post post;
        post = new Post(1, null, "testing like service", null, null, user);

        Like like = new Like();
        User userId = new User();
        userId.setUserId(1);
        Post postId = new Post();
        postId.setPostId(1);
        like.setUser(userId);
        like.setPost(postId);

        Like expectedResult = new Like(1, user, post);
        Mockito.when(this.userDao.findById(like.getUser().getUserId())).thenReturn(Optional.of(user));
        Mockito.when(this.postDao.findById(like.getPost().getPostId())).thenReturn(Optional.of(post));
        Mockito.when(this.likeDao.findLikeByUserAndPost(user, post)).thenReturn(expectedResult);

        //Act
        Like actualResult = this.likeService.getLikeBasedOnUserIdAndPostId(like);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getLikeBasedOnUserIdAndPostIdWhenNull() {
        //Assign
        Like like = new Like();
        User userId = new User();
        userId.setUserId(1);
        Post postId = new Post();
        postId.setPostId(1);
        like.setUser(userId);
        like.setPost(postId);

        Like expectedResult = null;
        Mockito.when(this.userDao.findById(like.getUser().getUserId())).thenReturn(Optional.empty());
        Mockito.when(this.postDao.findById(like.getPost().getPostId())).thenReturn(Optional.empty());

        //Act
        Like actualResult = this.likeService.getLikeBasedOnUserIdAndPostId(like);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void toggleLikeWhenLikeExists() {
        //Assign
        User user;
        user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        Post post;
        post = new Post(1, null, "testing like service", null, null, user);

        Like like = new Like();
        User userId = new User();
        userId.setUserId(1);
        Post postId = new Post();
        postId.setPostId(1);
        like.setUser(userId);
        like.setPost(postId);
        Like liked = new Like(1, user, post);

        Like expectedResult = null;
        Mockito.when(this.userDao.findById(like.getUser().getUserId())).thenReturn(Optional.of(user));
        Mockito.when(this.postDao.findById(like.getPost().getPostId())).thenReturn(Optional.of(post));
        Mockito.when(this.likeDao.findLikeByUserAndPost(user, post)).thenReturn(liked);

        //Act
        Like actualResult = this.likeService.toggleLike(like);

        //Assert
        assertEquals(expectedResult, actualResult);
        Mockito.verify(this.likeDao, Mockito.times(1)).deleteLike(liked.getLikeId());
    }

    @Test
    void toggleLikeWhenLikeDoesNotExist() {
        //Assign
        User user;
        user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        Post post;
        post = new Post(1, null, "testing like service", null, null, user);

        Like like = new Like();
        User userId = new User();
        userId.setUserId(1);
        Post postId = new Post();
        postId.setPostId(1);
        like.setUser(userId);
        like.setPost(postId);

        Like expectedResult = new Like(1, user, post);
        Mockito.when(this.userDao.findById(like.getUser().getUserId())).thenReturn(Optional.of(user));
        Mockito.when(this.postDao.findById(like.getPost().getPostId())).thenReturn(Optional.of(post));
        Mockito.when(this.likeDao.findLikeByUserAndPost(user, post)).thenReturn(null);
        Mockito.when(this.likeDao.save(like)).thenReturn(expectedResult);

        //Act
        Like actualResult = this.likeService.toggleLike(like);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void toggleLikeWhenUserOrPostAreNull() {
        //Assign
        Like like = new Like();
        User userId = new User();
        userId.setUserId(1);
        Post postId = new Post();
        postId.setPostId(1);
        like.setUser(userId);
        like.setPost(postId);

        Like expectedResult = null;
        Mockito.when(this.userDao.findById(like.getUser().getUserId())).thenReturn(Optional.empty());
        Mockito.when(this.postDao.findById(like.getPost().getPostId())).thenReturn(Optional.empty());

        //Act
        Like actualResult = this.likeService.toggleLike(like);

        //Assert
        assertEquals(expectedResult, actualResult);
    }
}