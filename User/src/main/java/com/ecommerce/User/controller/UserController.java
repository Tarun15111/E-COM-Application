package com.ecommerce.User.controller;

import com.ecommerce.User.dto.UserRequest;
import com.ecommerce.User.dto.UserResponse;
import com.ecommerce.User.model.User;
import com.ecommerce.User.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
//    @RequestMapping(method = RequestMethod.GET)
    public  ResponseEntity<List<UserResponse>> getUsers(){
        List<User> users = userService.findAllUsers();
        List<UserResponse> userResponses = users.stream()
                                                .map(user -> modelMapper.map(user, UserResponse.class)).toList();
        return  new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @PostMapping
    public  String createUser(@RequestBody UserRequest userRequest){
            User user = modelMapper.map(userRequest, User.class);
            userService.createUser(user);
            return  "User created Successfully";
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id){
        User user = userService.getUser(id);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return  ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<User> updateUser(@PathVariable  Long id, @RequestBody UserRequest userRequest){
            User user = modelMapper.map(userRequest, User.class);
            if(userService.updateUser(id, user)){
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


}
