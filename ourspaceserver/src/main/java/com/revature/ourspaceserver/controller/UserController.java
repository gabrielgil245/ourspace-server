package com.revature.ourspaceserver.controller;

import com.revature.ourspaceserver.model.JsonResponse;
import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.service.BCrypt;
import com.revature.ourspaceserver.service.EmailUtility;
import com.revature.ourspaceserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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

        // Uses BCrypt to hash the password.
        // Get first the given user password from front-end, then hash it and store to tempPassword
        String tempPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        // reassign the hashed password to the user to overwrite the orig value with the hashed value then save to db
        user.setPassword(tempPassword);

        User newUser = this.userService.createUser(user);
        if(newUser != null) {
            // Send email for successful registration
            EmailUtility.sendEmail(newUser.getEmail(), newUser.getUsername(), "new");
            jsonResponse = new JsonResponse(true, "User successfully created", newUser);
        } else {
            jsonResponse = new JsonResponse(false, "Username/Email entered already exists", null);
        }
        return jsonResponse;
    }

    @PostMapping("login")
    public JsonResponse login(HttpSession session, @RequestBody User user) {
        JsonResponse jsonResponse;
        User currentUser = this.userService.getUserByUsername(user.getUsername());
        // Use BCrypt checkpw() method to see if the input password matches the hashed value of the password
        if (BCrypt.checkpw(user.getPassword(), currentUser.getPassword())) {
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

    @PostMapping("reset-password")
    public JsonResponse resetPassword(HttpSession session, @RequestBody User user) {
        JsonResponse jsonResponse;

        User tempUser = this.userService.getUserByUsername(user.getUsername());
        if(tempUser == null)
            return null;

        // Use BCrypt checkpw() method to see if it matched the hashed value of the password
        if (BCrypt.checkpw(user.getPassword(), tempUser.getPassword())) {
            // If old password match to the hashed value then change to anew one.
            String newPassword = user.getEmail(); // stored temporarily the new password in email field from front-end
            // Hash the new password and set it to user password.
            String hashPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
            tempUser.setPassword(hashPassword); // set the hashed password value

            User currentUser = this.userService.resetUserPassword(tempUser);
            if (currentUser != null) {
                // Send email for successful registration
                EmailUtility.sendEmail(currentUser.getEmail(), currentUser.getUsername(), "reset");
                jsonResponse = new JsonResponse(true, "Password was successfully updated.", currentUser);
            } else {
                jsonResponse = new JsonResponse(false, "Error occurred during change of password.", null);
            }
            return jsonResponse;
        } else {
            return null;
        }
    }
}
