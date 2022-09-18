package com.example.mockmvcexampletwo.controller;

import com.example.mockmvcexampletwo.constant.URIConstant;
import com.example.mockmvcexampletwo.exception.DataNotFoundException;
import com.example.mockmvcexampletwo.model.User;
import com.example.mockmvcexampletwo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @GetMapping(URIConstant.GET)
    public ResponseEntity<List<User>> findUsers() throws SQLException {
        List<User> users = userService.findUsers();
        if(users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(URIConstant.GET_BY_ID)
    public ResponseEntity<User> findUserById(@PathVariable int id) throws SQLException {
        User user = userService.findUserById(id);
        if(user == null) {
            throw new DataNotFoundException("Data not found");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(URIConstant.GET_BY_EMAIL)
    public ResponseEntity<List<User>> findUsersByName(@RequestParam("email") String email) throws SQLException {
        List<User> users = userService.findUsersByEmail(email);
        if(users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(URIConstant.IS_ADMIN_BY_ID)
    public ResponseEntity<Boolean> isUserAdmin(@PathVariable int id) throws SQLException {
        User user = userService.findUserById(id);
        if(user == null) {
            throw new DataNotFoundException("Data not found");
        }
        boolean isAdmin = userService.isUserAdmin(id);
        return new ResponseEntity<>(isAdmin, HttpStatus.OK);
    }

    @DeleteMapping(URIConstant.DELETE_BY_ID)
    public ResponseEntity<String> deleteUser(@PathVariable int id) throws SQLException {
        User user = userService.findUserById(id);
        if(user == null) {
            throw new DataNotFoundException("Data not found");
        }
        int executed = userService.deleteUser(id);
        return executed != 0 ? new ResponseEntity<>("Delete account successful", HttpStatus.NO_CONTENT)
                : new ResponseEntity<>("Delete account failure", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(URIConstant.POST)
    public ResponseEntity<String> addUser(@RequestBody User user) throws SQLException {
        if(user == null || user.getEmail() ==  null || user.getPassword() == null) {
            throw new IllegalArgumentException("User data is not null");
        }
        int executed = userService.addUser(user);
        return executed != 0 ? new ResponseEntity<>("Add account successful", HttpStatus.CREATED)
                : new ResponseEntity<>("Add account failure", HttpStatus.BAD_REQUEST);
    }

    @PutMapping(URIConstant.PUT)
    public ResponseEntity<String> editUser(@RequestBody User user) throws SQLException {
        if(user == null || user.getId() ==  null || user.getEmail() ==  null || user.getPassword() == null) {
            throw new IllegalArgumentException("User data is not null");
        }

        User getUser = userService.findUserById(user.getId());
        if(getUser == null) {
            throw new DataNotFoundException("Data not found");
        }
        getUser.setId(user.getId());
        getUser.setEmail(user.getEmail());
        getUser.setPassword(user.getPassword());
        getUser.setIsAdmin(user.getIsAdmin());
        int executed = userService.editUser(user);
        return executed != 0 ? new ResponseEntity<>("Edit account successful", HttpStatus.OK)
                : new ResponseEntity<>("Edit account failure", HttpStatus.BAD_REQUEST);
    }
}
