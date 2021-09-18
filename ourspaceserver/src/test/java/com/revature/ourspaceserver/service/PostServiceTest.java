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
        Integer pageNumber = 1;
        List<Post> expected = new ArrayList<>();
        User user = new User(1,"test","password","service","tests","test@test.com",Date.from(Instant.now()), "description", "profile Pic URL");
        User user2 = new User(2,"test2","password2","service2","tests2","test2@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        Post post1 = new Post(1, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL", user);
        Post post2 = new Post(2, Timestamp.from(Instant.now()),"post Description2", "post Image URL2", "youtube URL2", user2);
        expected.add(post1);
        expected.add(post2);

        Long totalNumOfPosts = new Long(expected.size());
        Mockito.when(postDao.count()).thenReturn(totalNumOfPosts);
        //postsToDisplay = 5 (Will later be changed to 20)
        //postIdEnd = ((totalNumOfPosts + 1) - ((pageNumber - 1) * postsToDisplay))
        //postIdStart = ((totalNumOfPosts - postsToDisplay) - ((pageNumber - 1) * postsToDisplay))
        //At postsToDisplay = 5 (-3, 3); At postsToDisplay = 20 (-18, 3)
        Mockito.when(postDao.retrievePostsByOrderByPostSubmittedDesc(-3,3)).thenReturn(expected);

        List<Post> actual = this.postService.getPostsByPageNumber(pageNumber);

        assertEquals(expected,actual);
    }

    @Test
    void getPostsByPageNumberTestWhenListIsNull(){ //when test was made, pagination was set to 5 posts per page, that
        // value may
        // change
        Integer pageNumber = 2;
        List<Post> posts = new ArrayList<>();
        User user = new User(1,"test","password","service","tests","test@test.com",Date.from(Instant.now()), "description", "profile Pic URL");
        User user2 = new User(2,"test2","password2","service2","tests2","test2@test.com", Date.from(Instant.now()), "description", "profile Pic URL");
        Post post1 = new Post(1, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL", user);
        Post post2 = new Post(2, Timestamp.from(Instant.now()),"post Description2", "post Image URL2", "youtube URL2", user2);
        posts.add(post1);
        posts.add(post2);

        Long totalNumOfPosts = new Long(posts.size());
        List<Post> expected = null;
        Mockito.when(postDao.count()).thenReturn(totalNumOfPosts);
        //postsToDisplay = 5 (Will later be changed to 20)
        //postIdEnd = ((totalNumOfPosts + 1) - ((pageNumber - 1) * postsToDisplay))
        //postIdStart = ((totalNumOfPosts - postsToDisplay) - ((pageNumber - 1) * postsToDisplay))
        //At postsToDisplay = 5 (-8, -2); At postsToDisplay = 20 (-38, -17)
        Mockito.when(postDao.retrievePostsByOrderByPostSubmittedDesc(-8,-2)).thenReturn(expected);

        List<Post> actual = this.postService.getPostsByPageNumber(pageNumber);

        assertEquals(expected,actual);
    }

    @Test
    void getPostsByUserAndPageNumberTestWhenUserDoesNotExist(){
        //Assign
        String username = "";
        Integer pageNumber = 1;
        List<Post> expectedResult = null;
        Mockito.when(userDao.findUserByUsername(username)).thenReturn(null);

        //Act
        List<Post> actualResult = postService.getPostsByUserAndPageNumber(username, pageNumber);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getPostsByUserAndPageNumberTestWhenUserDoesExist(){
        //Assign
        Integer pageNumber = 1;

        User user = new User(1,"test","password","service","tests","test@test.com",Date.from(Instant.now()), "description", "profile Pic URL");
        Post post1 = new Post(1, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post2 = new Post(2, Timestamp.from(Instant.now()),"post Description2", "post Image URL2", "youtube URL2"
                , user);
        Post post3 = new Post(3, Timestamp.from(Instant.now()),"post Description3", "post Image URL2", "youtube URL3"
                , user);
        List<Post> expected = new ArrayList<>();
        expected.add(post1);
        expected.add(post2);
        expected.add(post3);

        Mockito.when(userDao.findUserByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(postDao.findPostByUserOrderByPostSubmittedDesc(user)).thenReturn(expected);

        //Act
        List<Post> actual = postService.getPostsByUserAndPageNumber(user.getUsername(), pageNumber);

        //Assert
        assertEquals(expected,actual);
    }

    @Test
    void getPostsByUserAndPageNumberTestWhenEndIndexIsOutOfBounds(){
        //Assign
        User user = new User(1,"test","password","service","tests","test@test.com",Date.from(Instant.now()), "description", "profile Pic URL");
        Post post1 = new Post(1, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post2 = new Post(2, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post3 = new Post(3, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post4 = new Post(4, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post5 = new Post(5, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post6 = new Post(6, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post7 = new Post(7, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post8 = new Post(8, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post9 = new Post(9, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post10 = new Post(10, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post11 = new Post(11, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post12 = new Post(12, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post13 = new Post(13, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post14 = new Post(14, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post15 = new Post(15, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post16 = new Post(16, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post17 = new Post(17, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post18 = new Post(18, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post19 = new Post(19, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post20 = new Post(20, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post21 = new Post(21, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);

        /*Initially set to display 5 posts at a time, will later be changed to 20,
        so page number will need to be set to 2 for this test (21-40)*/
        Integer pageNumber = 5;

        List<Post> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        posts.add(post5);
        posts.add(post6);
        posts.add(post7);
        posts.add(post8);
        posts.add(post9);
        posts.add(post10);
        posts.add(post11);
        posts.add(post12);
        posts.add(post13);
        posts.add(post14);
        posts.add(post15);
        posts.add(post16);
        posts.add(post17);
        posts.add(post18);
        posts.add(post19);
        posts.add(post20);
        posts.add(post21);

        List<Post> expected = new ArrayList<>();
        expected.add(post21);
        Mockito.when(userDao.findUserByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(postDao.findPostByUserOrderByPostSubmittedDesc(user)).thenReturn(posts);

        //Act
        List<Post> actual = postService.getPostsByUserAndPageNumber(user.getUsername(), pageNumber);

        //Assert
        assertEquals(expected,actual);
    }

    @Test
    void getPostsByUserAndPageNumberTestWhenStartIndexIsOutOfBounds(){
        //Assign
        User user = new User(1,"test","password","service","tests","test@test.com",Date.from(Instant.now()), "description", "profile Pic URL");
        Post post1 = new Post(1, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post2 = new Post(2, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post3 = new Post(3, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post4 = new Post(4, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post5 = new Post(5, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post6 = new Post(6, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post7 = new Post(7, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post8 = new Post(8, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post9 = new Post(9, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post10 = new Post(10, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post11 = new Post(11, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post12 = new Post(12, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post13 = new Post(13, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post14 = new Post(14, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post15 = new Post(15, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post16 = new Post(16, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post17 = new Post(17, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post18 = new Post(18, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post19 = new Post(19, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post20 = new Post(20, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);
        Post post21 = new Post(21, Timestamp.from(Instant.now()),"post Description", "post Image URL", "youtube URL",
                user);

        /*Initially set to display 5 posts at a time, will later be changed to 20,
        so page number will need to be set to 3 for this test (41-60)*/
        Integer pageNumber = 6;

        List<Post> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        posts.add(post5);
        posts.add(post6);
        posts.add(post7);
        posts.add(post8);
        posts.add(post9);
        posts.add(post10);
        posts.add(post11);
        posts.add(post12);
        posts.add(post13);
        posts.add(post14);
        posts.add(post15);
        posts.add(post16);
        posts.add(post17);
        posts.add(post18);
        posts.add(post19);
        posts.add(post20);
        posts.add(post21);

        List<Post> expected = null;
        Mockito.when(userDao.findUserByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(postDao.findPostByUserOrderByPostSubmittedDesc(user)).thenReturn(posts);

        //Act
        List<Post> actual = postService.getPostsByUserAndPageNumber(user.getUsername(), pageNumber);

        //Assert
        assertEquals(expected,actual);
    }
}