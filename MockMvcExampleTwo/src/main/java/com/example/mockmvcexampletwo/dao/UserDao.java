package com.example.mockmvcexampletwo.dao;


import com.example.mockmvcexampletwo.model.User;

import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

public interface UserDao {
    public int addUser(User User) throws SQLException, InvalidPropertiesFormatException;
    public int editUser(User User) throws SQLException;
    public User findUserById(Integer id) throws SQLException;
    public List<User> findUsersByEmail(String email) throws SQLException;
    public List<User> findUsers() throws SQLException;
    public int deleteUser(Integer id) throws SQLException;

    public boolean isUserAdmin(Integer id) throws SQLException;
}
