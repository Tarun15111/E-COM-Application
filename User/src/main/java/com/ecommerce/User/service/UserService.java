package com.ecommerce.User.service;

import com.ecommerce.User.exception.ResourceNotFoundException;
import com.ecommerce.User.model.User;
import com.ecommerce.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public  void createUser(User user){
        userRepository.save(user);
    }

    public  List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public  User getUser(Long id){
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException(("User not found with id: " + id)));
    }

    public  boolean  updateUser(Long id, User user){
        User user1 = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", String.valueOf(id)));
        return userRepository.findById(id)
                             .map(existingUser ->{
                            existingUser.setFirstName(user.getFirstName());
                            existingUser.setLastName(user.getLastName());
                            userRepository.save(existingUser);
                            return  true;
        }).orElse(false);
    }

}
