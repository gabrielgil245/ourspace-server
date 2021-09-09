package com.revature.services;

import com.revature.models.User;
import com.revature.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {
    UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void insert(User user) {
        this.userDao.insert(user);
    }
    public User selectByUsername(String username) {
        return this.userDao.selectByUsername(username);
    }
    public User selectByEmail(String email) {
        return this.userDao.selectByEmail(email);
    }
    public void updateUserInfo(User user) {
        this.userDao.updateUserInfo(user);
    }
}
