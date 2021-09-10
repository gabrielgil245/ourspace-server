package com.revature.ourspaceserver.service;

import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserService {
    UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return this.userDao.findAll();
    }

    public User getUserById(Integer id) {
        return this.userDao.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return this.userDao.findUserByUsername(username);
    }

    public User getUserByEmail(String email) {
        return this.userDao.findUserByEmail(email);
    }

    public User createUser(User user) {
        User temp = getUserByUsername(user.getUsername());
        if(temp != null) return null;
        temp = getUserByEmail(user.getEmail());
        if(temp != null) return null;
        return this.userDao.save(user);
    }

}
