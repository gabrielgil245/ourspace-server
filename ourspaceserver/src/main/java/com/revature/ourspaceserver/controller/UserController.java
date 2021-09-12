package com.revature.ourspaceserver.controller;

import com.revature.ourspaceserver.model.JsonResponse;
import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController("userController")
@RequestMapping(value="api")
@CrossOrigin(value="http://localhost:4200", allowCredentials = "true")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("user")
    public JsonResponse getAllUsers() {
        return new JsonResponse(true, "Listing all users", this.userService.getAllUsers());
    }

    @PostMapping("user")
    public JsonResponse createUser(@RequestBody User user) {
        JsonResponse jsonResponse;
        User newUser = this.userService.createUser(user);
        if(newUser != null)
            jsonResponse = new JsonResponse(true, "User successfully created", newUser);
        else
            jsonResponse = new JsonResponse(false, "Username/Email entered already exists", null);
        return jsonResponse;
    }

    @PostMapping("login")
    public JsonResponse login(HttpSession session, @RequestBody User user) {
        JsonResponse jsonResponse;
        User currentUser = this.userService.getUserByUsername(user.getUsername());
        if(user.getPassword().equals(currentUser.getPassword())) {
            session.setAttribute("userInSession", currentUser);
            jsonResponse = new JsonResponse(true, "Log in successful, session created", currentUser);
        }else {
            jsonResponse = new JsonResponse(false, "Invalid Username or Password", null);
        }
        return jsonResponse;
    }

    @GetMapping("check-session")
    public JsonResponse checkSession(HttpSession session) {
        JsonResponse jsonResponse;
        User user = (User) session.getAttribute("userInSession");
        if(user != null)
            jsonResponse = new JsonResponse(true, "Session found", user);
        else
            jsonResponse = new JsonResponse(false, "Session not found", null);
        return jsonResponse;
    }

    @GetMapping("logout")
    public JsonResponse logout(HttpSession session) {
        session.setAttribute("userInSession", null);
        return new JsonResponse(true, "Session terminated", null);
    }

}
