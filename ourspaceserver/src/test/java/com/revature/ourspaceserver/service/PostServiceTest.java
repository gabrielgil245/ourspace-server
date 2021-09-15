package com.revature.ourspaceserver.service;

import com.revature.ourspaceserver.model.Post;
import com.revature.ourspaceserver.model.User;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    PostService postService;

    @Mock
    UserDao userDao;
    @Mock
    PostDao postDao;

    @BeforeEach
    void setUp() {
        this.postService = new PostService(postDao, userDao);
    }

    @Test
    void getAllPostsTest() {
        List<Post> expected = new ArrayList<>();
        User user = new User(1,"test","password","service","tests","test@test.com",Date.from(Instant.now()), "description", "profile Pic URL");
        User user2 = new User(2,"test2","password2","service2","tests2","test2@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        expected.add(new Post(1, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL", user));
        expected.add(new Post(2, Timestamp.from(Instant.now()),"post Description2", "post Image URL2", "youtube URL2", user2));

        Mockito.when(postDao.findAll()).thenReturn(expected);
        List<Post> actual = this.postService.getAllPosts();
        assertEquals(expected,actual);
    }

   @Test
    void createPostTestUserDoesNotExist() {
       User user = new User(2, "test", "password", "service", "tests", "test@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        Post post2 = new Post(2, Timestamp.from(Instant.now()),"post Description2", "post Image URL2", "youtube URL2", user);
        System.out.println(post2);
        Mockito.when(userDao.findById(2)).thenReturn(Optional.empty());
        Post actualPost = postService.createPost(post2);
        assertNull(actualPost);
    }

    @Test
    void createPostTestUserDoesExist() {
        User user = new User(2, "test", "password", "service", "tests", "test@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        Post post2 = new Post(2, Timestamp.from(Instant.now()), "post Description2",
                "post Image URL2", "youtube URL2", user);
        System.out.println(post2);
        Mockito.when(userDao.findById(2)).thenReturn(Optional.of(user));
        Mockito.when(postDao.save(post2)).thenReturn(post2);
        Post actualPost = postService.createPost(post2);
        assertEquals(post2,actualPost);
    }

    @Test
    void getAllPostsByUserTestUserDoesNotExist(){
        Mockito.when(userDao.findById(1)).thenReturn(Optional.empty());
        List<Post> actual = this.postService.getAllPostsByUser(1);
        assertNull(actual);
    }

    @Test
    void getAllPostsByUserTestUserDoesExist(){
        List<Post> expected = new ArrayList<>();
         User user2 = new User(2,"test2","password2","service2","tests2","test2@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        expected.add(new Post(1, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL", user2));
        expected.add(new Post(2, Timestamp.from(Instant.now()),"post Description2", "post Image URL2", "youtube URL2", user2));
        Mockito.when(userDao.findById(2)).thenReturn(Optional.of(user2));
        Mockito.when(postDao.findPostByUser(user2)).thenReturn(expected);
        List<Post> actual = this.postService.getAllPostsByUser(2);
        assertEquals(expected,actual);
    }

    @Test
    void getPostsByPageNumberTest(){ //when test was made, pagination was set to 5 posts per page, that value may change
        List<Post> expected = new ArrayList<>();
        User user = new User(1,"test","password","service","tests","test@test.com",Date.from(Instant.now()), "description", "profile Pic URL");
        User user2 = new User(2,"test2","password2","service2","tests2","test2@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        Post post3 = new Post(3, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL", user);
        Post post2 = new Post(2, Timestamp.from(Instant.now()),"post Description2", "post Image URL2", "youtube URL2", user2);
        expected.add(post2);
        expected.add(post3);
        Mockito.when(postDao.count()).thenReturn(7L);
        Mockito.when(postDao.retrievePostsByOrderByPostSubmittedDesc(2,9)).thenReturn(expected);
        List<Post> actual = this.postService.getPostsByPageNumber(1);
        assertEquals(expected,actual);
    }

    @Test
    void getPostsByUserAndPageNumberTestUserDoesNotExist(){
        Mockito.when(userDao.findById(2)).thenReturn(Optional.empty());
        List<Post> actual = postService.getPostsByUserAndPageNumber(2,1);
        assertNull(actual);
    }

    @Test
    void getPostsByUserAndPageNumberTestUserDoesExist(){
        List<Post> expected = new ArrayList<>();
        User user = new User(1,"test","password","service","tests","test@test.com",Date.from(Instant.now()), "description", "profile Pic URL");
        User user2 = new User(2,"test2","password2","service2","tests2","test2@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        Post post3 = new Post(3, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL", user);
        Post post2 = new Post(2, Timestamp.from(Instant.now()),"post Description2", "post Image URL2", "youtube URL2", user2);
        expected.add(post2);
        Mockito.when(userDao.findById(2)).thenReturn(Optional.of(user2));
        Mockito.when(postDao.count()).thenReturn(6L);
        Mockito.when(postDao.retrievePostByUserAndPageNumberOrderByPostSubmittedDesc(user2,1,7)).thenReturn(expected);
        List<Post> actual = postService.getPostsByUserAndPageNumber(2,1);
        assertEquals(expected,actual);
    }
}