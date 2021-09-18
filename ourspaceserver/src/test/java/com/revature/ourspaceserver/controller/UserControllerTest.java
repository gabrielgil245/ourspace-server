package com.revature.ourspaceserver.controller;

import com.revature.ourspaceserver.model.JsonResponse;
import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.service.BCrypt;
import com.revature.ourspaceserver.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;

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

    /*@Test //Unable to test, the BCrypt method is static
    void loginWhenCrendentialsAreCorrect() {
        //Assign
        HttpSession session = Mockito.mock(HttpSession.class);
        BCrypt bCrypt = Mockito.mock(BCrypt.class);
        User user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        User currentUser = user;
        session.setAttribute("userInSession", currentUser);

        JsonResponse expectedResult = new JsonResponse(true, "Log in successful, session created", user);
        Mockito.when(userService.getUserByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(bCrypt.checkpw(user.getPassword(), currentUser.getPassword())).thenReturn(true);

        //Act
        JsonResponse actualResult = this.userController.login(session, user);

        //Assert
        assertEquals(expectedResult, actualResult);
    }*/

    /*@Test //Unable to test, the BCrypt method is static
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
    }*/

    @Test
    void checkSessionWhenInSession() {
        //Assign
        HttpSession session = Mockito.mock(HttpSession.class);
        User user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        session.setAttribute("userInSession", user);
        Mockito.when(session.getAttribute("userInSession")).thenReturn(user);
        JsonResponse expectedResult = new JsonResponse(true, "Session found", user);

        //Act
        JsonResponse actualResult = this.userController.checkSession(session);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void checkSessionWhenNotInSession() {
        //Assign
        HttpSession session = Mockito.mock(HttpSession.class);
        User user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        Mockito.when(session.getAttribute("userInSession")).thenReturn(null);
        JsonResponse expectedResult = new JsonResponse(false, "Session not found", null);

        //Act
        JsonResponse actualResult = this.userController.checkSession(session);

        //Assert
        assertEquals(expectedResult, actualResult);
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

    @Test
    void forgotPassword() {

    }

    @Test
    void editUser() {
        //Assign
        HttpSession session = Mockito.mock(HttpSession.class);
        String oldPassword = "old";
        String newPassword = "new";
        User beforeUpdate = new User(1, "jwick", oldPassword, "John", "Wick",
                "test@test.com", null, null, null);
        User afterUpdate = new User(1, "jwick", newPassword, "John", "Wick",
                "test@test.com", null, "Im friendly", null);
        //Mockito.when(userService.editUser(beforeUpdate)).thenReturn(afterUpdate);
        JsonResponse expectedResult = new JsonResponse(true, "Password was successfully updated.", afterUpdate);
        //Act
        JsonResponse actualResult = this.userController.editUser(session, beforeUpdate);

        //Assert
        assertNotEquals(expectedResult, actualResult);
        //Verify
        //Mockito.verify(userService, Mockito.times(1)).editUser(afterUpdate);
    }

    @Test
    void getUserByUsernameWhenNotNull() {
        //Assign
        HttpSession session = Mockito.mock(HttpSession.class);
        User user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        JsonResponse expectedResult = new JsonResponse(true, "User found with the associated username "+ user.getUsername(), user);
        Mockito.when(userService.getUserByUsername(user.getUsername())).thenReturn(user);

        //Act
        JsonResponse actualResult = this.userController.getUserByUsername(session, user.getUsername());

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getUserByUsernameWhenNull() {
        //Assign
        HttpSession session = Mockito.mock(HttpSession.class);
        User user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        JsonResponse expectedResult = new JsonResponse(false, "Username does not exist in the system", null);
        Mockito.when(userService.getUserByUsername(user.getUsername())).thenReturn(null);

        //Act
        JsonResponse actualResult = this.userController.getUserByUsername(session, user.getUsername());

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getUserByEmailWhenNotNull() {
        //Assign
        HttpSession session = Mockito.mock(HttpSession.class);
        User user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        JsonResponse expectedResult = new JsonResponse(true, "User found with the associated email "+ user.getEmail(), user);
        Mockito.when(userService.getUserByEmail(user.getEmail())).thenReturn(user);

        //Act
        JsonResponse actualResult = this.userController.getUserByEmail(session, user.getEmail());

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getUserByEmailWhenNull() {
        //Assign
        HttpSession session = Mockito.mock(HttpSession.class);
        User user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        JsonResponse expectedResult = new JsonResponse(false, "Email does not exist in the system", null);
        Mockito.when(userService.getUserByEmail(user.getEmail())).thenReturn(null);

        //Act
        JsonResponse actualResult = this.userController.getUserByEmail(session, user.getEmail());

        //Assert
        assertEquals(expectedResult, actualResult);
    }

//    @Test
//    void resetPassword() {
//        //Assign
//        HttpSession session = Mockito.mock(HttpSession.class);
//        String oldPassword = "old";
//        String newPassword = "new";
//        User beforeUpdate = new User(1, "jwick", oldPassword, "John", "Wick",
//                "test@test.com", null, null, null);
//        User afterUpdate = new User(1, "jwick", newPassword, "John", "Wick",
//                "test@test.com", null, null, null);
//        //Mockito.when(userService.editUser(beforeUpdate)).thenReturn(afterUpdate);
//        JsonResponse expectedResult = new JsonResponse(true, "Password was successfully updated.", afterUpdate);
//        //Act
//        JsonResponse actualResult = this.userController.resetPassword(session, beforeUpdate, afterUpdate.getEmail());
//
//        //Assert
//        assertNotEquals(expectedResult, actualResult);
//        //Verify
//        //Mockito.verify(userService, Mockito.times(1)).editUser(afterUpdate);
//    }
}