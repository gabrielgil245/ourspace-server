package com.revature.ourspaceserver.controller;

import com.revature.ourspaceserver.model.JsonResponse;
import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
