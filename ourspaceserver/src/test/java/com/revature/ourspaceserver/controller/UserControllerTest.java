package com.revature.ourspaceserver.controller;

import com.revature.ourspaceserver.model.JsonResponse;
import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    UserController userController;

    @Mock
    UserService userService;

    @BeforeEach
    void setUp() {
        userController = new UserController(userService);
    }

    @Test
    void getAllUsers() {
        //Assign
        List<User> users = new ArrayList<>();
        users.add(new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null));
        JsonResponse expectedResult = new JsonResponse(true, "Listing all users", users);
        Mockito.when(userService.getAllUsers()).thenReturn(users);

        //Act
        JsonResponse actualResult = this.userController.getAllUsers();

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createUserWhenNotNull() {
        //Assign
        User user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        JsonResponse expectedResult = new JsonResponse(true, "User successfully created", user);
        Mockito.when(userService.createUser(user)).thenReturn(user);

        //Act
        JsonResponse actualResult = this.userController.createUser(user);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createUserWhenNull() {
        //Assign
        User user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        JsonResponse expectedResult = new JsonResponse(false, "Username/Email entered already exists", null);
        Mockito.when(userService.createUser(user)).thenReturn(null);

        //Act
        JsonResponse actualResult = this.userController.createUser(user);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void loginWhenCrendentialsAreCorrect() {
        //Assign
        HttpSession session = Mockito.mock(HttpSession.class);
        User user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        JsonResponse expectedResult = new JsonResponse(true, "Log in successful, session created", user);
        Mockito.when(userService.getUserByUsername(user.getUsername())).thenReturn(user);

        //Act
        JsonResponse actualResult = this.userController.login(session, user);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void loginWhenCrendentialsAreIncorrect() {
        //Assign
        HttpSession session = Mockito.mock(HttpSession.class);
        User invalidUser = new User(1, "jwick", "pass123", "John", "Wick",
                "test@test.com", null, null, null);
        User user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        JsonResponse expectedResult = new JsonResponse(false, "Invalid Username or Password", null);
        Mockito.when(userService.getUserByUsername(invalidUser.getUsername())).thenReturn(user);

        //Act
        JsonResponse actualResult = this.userController.login(session, invalidUser);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void checkSession() {
        /*//Assign
        User user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        this.session.setAttribute("userInSession", user);
        JsonResponse expectedResult = new JsonResponse(true, "Session found", user);

        //Act
        JsonResponse actualResult = this.userController.checkSession(session);

        //Assert
        assertEquals(expectedResult, actualResult);*/
    }

    @Test
    void logout() {
        //Assign
        HttpSession session = Mockito.mock(HttpSession.class);
        JsonResponse expectedResult = new JsonResponse(true, "Session terminated", null);

        //Act
        JsonResponse actualResult = this.userController.logout(session);

        //Assert
        assertEquals(expectedResult, actualResult);
    }
}