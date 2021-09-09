package com.revature.controller;

import com.revature.models.JsonResponse;
import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userController")
@CrossOrigin(value="http://localhost:4200", allowCredentials = "true")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user")
    public JsonResponse createUser(@RequestBody User user) {
        this.userService.insert(user);
        return new JsonResponse(true, "User was successfully created", null);
    }

    @GetMapping("user/username")
    public JsonResponse getUserByUsername(@RequestBody User user) {
        System.out.println(user);
        String username = user.getUsername();
        JsonResponse jsonResponse;
        User currentUser = this.userService.selectByUsername(username);
        if(currentUser != null)
            jsonResponse = new JsonResponse(
                    true,
                    "Welcome back " + user.getFirstName() + "!",
                    currentUser
            );
        else
            jsonResponse = new JsonResponse(
                    false,
                    "Username, " + username + ", was not found",
                    null
            );
        return jsonResponse;
    }
}
