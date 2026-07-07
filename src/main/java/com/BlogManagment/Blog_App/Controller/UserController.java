package com.BlogManagment.Blog_App.Controller;


import com.BlogManagment.Blog_App.Entity.User;
import com.BlogManagment.Blog_App.Exception.UserException;
import com.BlogManagment.Blog_App.Response.ApiResponse;
import com.BlogManagment.Blog_App.Response.UserProfileResponse;
import com.BlogManagment.Blog_App.Rquest.UserSignInRequest;
import com.BlogManagment.Blog_App.Rquest.UserSignUpRequest;
import com.BlogManagment.Blog_App.Service.CustomUserDetails;
import com.BlogManagment.Blog_App.Service.UserService;
import com.BlogManagment.Blog_App.Utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/public/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserSignUpRequest userSignUpRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserException("Invalid User Input", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setEmail(userSignUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
        user.setName(userSignUpRequest.getName());

        user = userService.createUser(user);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());
        String token = jwtUtil.generateToken(user.getEmail(), claims);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "User registered successfully");
        response.put("token", token);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("/public/signin")
    public ResponseEntity<?> userSignin(@Valid @RequestBody UserSignInRequest userSignInRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserException("Invalid User Input", HttpStatus.BAD_REQUEST);
        }

        User user = userService.findUserByEmail(userSignInRequest.getEmail());

        if (user == null || !passwordEncoder.matches(userSignInRequest.getPassword(), user.getPassword())) {
            throw new UserException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());
        String token = jwtUtil.generateToken(user.getEmail(), claims);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "User signed in successfully");
        response.put("token", token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/public/profile")
    public ResponseEntity<?> getUserProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "User Profile fetched successfully");
        response.put("user", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/public/profile/{id}")
    public ResponseEntity<?> getUserProfileById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            throw new UserException("User not found", HttpStatus.NOT_FOUND);
        }
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setEmail(user.getEmail());
        userProfileResponse.setName(user.getName());
        userProfileResponse.setId(user.getId());
        userProfileResponse.setPassword(user.getPassword());
        userProfileResponse.setBlogCount((long) (user.getBlog() != null ? user.getBlog().size() : 0));
        return ResponseEntity.ok().body(new ApiResponse<>("success", "User Profile fetched successfully", userProfileResponse));
    }

    @PutMapping(value ="/secured/profile/update" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                               @RequestParam(name = "img" ,required = false)MultipartFile imageFile,
                                               @RequestParam String name,
                                               @RequestParam String isChanged,
                                               @RequestParam(required = true) Long userId)
    {
        if (customUserDetails.getUser().getId() != userId) {
            throw new UserException("Unauthorized to update this profile", HttpStatus.UNAUTHORIZED);
        }

        User findUser = userService.findUserById(userId);

        if(findUser == null){
            throw new UserException("User not found", HttpStatus.NOT_FOUND);
        }

        findUser.setName(name);
        findUser = userService.updateUser(findUser);

        return ResponseEntity.ok().body(new ApiResponse<>("success", "User Profile updated successfully", null));
    }
}