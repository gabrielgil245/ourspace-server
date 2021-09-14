package com.revature.ourspaceserver.service;

import com.revature.ourspaceserver.model.User;
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
class UserServiceTest {
    UserService userService;

    @Mock
    UserDao userDao;

    @BeforeEach
    void setUp() {
        userService = new UserService(userDao);
    }

    @Test
    void getAllUsers() {
        //Assign
        List<User> expectedResult = new ArrayList<>();
        expectedResult.add(new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null));
        Mockito.when(userDao.findAll()).thenReturn(expectedResult);

        //Act
        List<User> actualResult = this.userService.getAllUsers();

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getUserByIdReturnNull() {
        //Assign
        Integer id = 1;
        User expectedResult = null;
        Mockito.when(userDao.findById(id)).thenReturn(Optional.empty());

        //Act
        User actualResult = this.userService.getUserById(id);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getUserByIdReturnNotNull() {
        //Assign
        Integer id = 1;
        User expectedResult = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        Mockito.when(userDao.findById(id)).thenReturn(Optional.of(expectedResult));

        //Act
        User actualResult = this.userService.getUserById(id);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getUserByUsername() {
        //Assign
        String username = "jwick";
        User expectedResult = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        Mockito.when(userDao.findUserByUsername(username)).thenReturn(expectedResult);

        //Act
        User actualResult = this.userService.getUserByUsername(username);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getUserByEmail() {
        //Assign
        String email = "test@test.com";
        User expectedResult = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        Mockito.when(userDao.findUserByEmail(email)).thenReturn(expectedResult);

        //Act
        User actualResult = this.userService.getUserByEmail(email);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createUserWhenUserDoesNotExist() {
        //Assign
        User expectedResult = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        Mockito.when(userDao.findUserByUsername(expectedResult.getUsername())).thenReturn(null);
        Mockito.when(userDao.findUserByEmail(expectedResult.getEmail())).thenReturn(null);
        Mockito.when(userDao.save(expectedResult)).thenReturn(expectedResult);

        //Act
        User actualResult = this.userService.createUser(expectedResult);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createUserWhenUsernameExists() {
        //Assign
        User user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        Mockito.when(userDao.findUserByUsername(user.getUsername())).thenReturn(user);
        User expectedResult = null;

        //Act
        User actualResult = this.userService.createUser(user);

        //Assert
        assertEquals(expectedResult, actualResult);
        Mockito.verify(userDao, Mockito.times(0)).findUserByEmail(user.getEmail());
        Mockito.verify(userDao, Mockito.times(0)).save(user);
    }

    @Test
    void createUserWhenEmailExists() {
        //Assign
        User user = new User(1, "jwick", "password", "John", "Wick",
                "test@test.com", null, null, null);
        Mockito.when(userDao.findUserByUsername(user.getUsername())).thenReturn(null);
        Mockito.when(userDao.findUserByEmail(user.getEmail())).thenReturn(user);
        User expectedResult = null;

        //Act
        User actualResult = this.userService.createUser(user);

        //Assert
        assertEquals(expectedResult, actualResult);
        Mockito.verify(userDao, Mockito.times(0)).save(user);
    }

    @Test
    void resetUserPasswordSuccessful() {
        //Assign
        String email = "test@test.com";
        String oldPassword = "old";
        String newPassword = "new";
        User beforeUpdate = new User(1, "jwick", oldPassword, "John", "Wick",
                "test@test.com", null, null, null);
        User expectedResult = new User(1, "jwick", newPassword, "John", "Wick",
                "test@test.com", null, null, null);
        Mockito.when(userDao.save(expectedResult)).thenReturn(expectedResult);

        //Act
        User actualResult = this.userService.editUser(expectedResult);

        //Assert
        assertEquals(expectedResult, actualResult);
        //Verify
        Mockito.verify(userDao, Mockito.times(1)).save(expectedResult);
    }

    @Test
    void resetUserPasswordFailure() {
        //Assign
        String email = "test@test.com";
        String oldPassword = "old";
        String newPassword = "new";
        User beforeUpdate = new User(1, "jwick", oldPassword, "John", "Wick",
                "test@test.com", null, null, null);
        User expectedResult = new User(1, "jwick", newPassword, "John", "Wick",
                "test@test.com", null, null, null);
        Mockito.when(userDao.save(expectedResult)).thenReturn(beforeUpdate);

        //Act
        User actualResult = this.userService.editUser(expectedResult);

        //Assert
        assertNotEquals(expectedResult, actualResult);
        //Verify
        Mockito.verify(userDao, Mockito.times(1)).save(expectedResult);
    }
}