package com.kunal.journalApp.controller;

import com.kunal.journalApp.entity.JournalEntry;
import com.kunal.journalApp.entity.User;
import com.kunal.journalApp.service.JournalEntryService;
import com.kunal.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveEntry(user);
    }

    @PutMapping("/{userName}")
    public User updateUser(@RequestBody User user, @PathVariable String userName){
        User oldUser = userService.findByUserName(userName);
        if(oldUser != null){
            oldUser.setUserName(user.getUserName());
            oldUser.setPassword(user.getPassword());
            userService.saveEntry(oldUser);
        }
        return oldUser;
    }

}
