package com.revature.ourspaceserver.controller;

import com.revature.ourspaceserver.model.JsonResponse;
import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.service.BCrypt;
import com.revature.ourspaceserver.service.EmailUtility;
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
        System.out.println("test2");
        System.out.println(user);

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

    @PatchMapping("reset-password")
    public JsonResponse resetPassword(HttpSession session, @RequestBody User user) {
        JsonResponse jsonResponse;

        User tempUser = (User) session.getAttribute("userInSession");
        if(tempUser == null){
            if(user.getEmail() == null || !user.getEmail().isEmpty()) {
                tempUser = this.userService.getUserByEmail(user.getEmail());
            } else {
                return null;
            }
        }

        String newPassword = user.getPassword();
        // Hash the new password and set it to user password.
        String hashPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
        tempUser.setPassword(hashPassword); // set the hashed password value

        User currentUser = this.userService.editUser(tempUser);
        if (currentUser != null) {
            // Send email for successful registration
            EmailUtility.sendEmail(currentUser.getEmail(), currentUser.getUsername(), "reset");
            jsonResponse = new JsonResponse(true, "Password was successfully updated.", currentUser);
        } else {
            jsonResponse = new JsonResponse(false, "Error occurred during change of password.", null);
        }
        return jsonResponse;
    }

    @GetMapping("forgot-password/{email}")
    public JsonResponse forgotPassword(@PathVariable String email) {
        JsonResponse jsonResponse;
        try{
            User user = this.userService.getUserByEmail(email);
            //Send forgot-password email
            System.out.println("EMAIL Sent to : " + user.getEmail());
            EmailUtility.sendEmail(user.getEmail(), user.getUsername(), "forgot");
            return jsonResponse = new JsonResponse(true, "Forgot password email sent", user);
        } catch (Exception ex) {
            jsonResponse = new JsonResponse(false, "Error found" + ex, null);
            return jsonResponse;
        }
    }

    @PatchMapping("user")
    public JsonResponse editUser(HttpSession session, @RequestBody User user) {
        JsonResponse jsonResponse;

        System.out.println("USER FROM CLIENT");
        System.out.println(user);
        User tempUser = (User) session.getAttribute("userInSession");
        if(tempUser == null)
            return null;

        System.out.println(tempUser);
        //Edit the editable fields
        tempUser.setAboutMe(user.getAboutMe());
        tempUser.setBirthday(user.getBirthday());
        tempUser.setFirstName(user.getFirstName());
        tempUser.setLastName(user.getLastName());
        tempUser.setProfilePic(user.getProfilePic());

        User currentUser = this.userService.editUser(tempUser);
        System.out.println(currentUser);
        if (currentUser != null) {
            // Send email for successful registration
            EmailUtility.sendEmail(currentUser.getEmail(), currentUser.getUsername(), "update");
            jsonResponse = new JsonResponse(true, "Profile was successfully updated.", currentUser);
        } else {
            jsonResponse = new JsonResponse(false, "Error occurred during the profile update.", null);
        }
        return jsonResponse;
    }

    @GetMapping("user/{username}")
    public JsonResponse getUserByUsername(HttpSession session, @PathVariable String username) {
        JsonResponse jsonResponse;
        User currentUser = this.userService.getUserByUsername(username);

        if (currentUser != null){
            jsonResponse = new JsonResponse(true, "User found with the associated username "+ username, currentUser);
        }else {
            jsonResponse = new JsonResponse(false, "Username does not exist in the system", null);
        }
        return jsonResponse;
    }

    @GetMapping("user/{email}")
    public JsonResponse getUserByEmail(HttpSession session, @PathVariable String email) {
        JsonResponse jsonResponse;
        User currentUser = this.userService.getUserByEmail(email);

        if (currentUser != null){
            jsonResponse = new JsonResponse(true, "User found with the associated email "+ email, currentUser);
        }else {
            jsonResponse = new JsonResponse(false, "Email does not exist in the system", null);
        }
        return jsonResponse;
    }
}

