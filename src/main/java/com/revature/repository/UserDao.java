package com.revature.repository;

import com.revature.models.User;

import java.util.List;

public interface UserDao {
    void insert(User user);
    User selectByUsername(String username);
    User selectByEmail(String email);
    void updateUserInfo(User user);
}
