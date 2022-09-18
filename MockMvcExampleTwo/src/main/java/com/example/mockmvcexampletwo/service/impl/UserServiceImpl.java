package com.example.mockmvcexampletwo.service.impl;

import com.example.mockmvcexampletwo.dao.impl.UserDaoImpl;
import com.example.mockmvcexampletwo.model.User;
import com.example.mockmvcexampletwo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDaoImpl UserDao;

    @Override
    public int addUser(User user) throws SQLException {
        return UserDao.addUser(user);
    }

    @Override
    public int editUser(User user) throws SQLException {
        return UserDao.editUser(user);
    }

    @Override
    public User findUserById(Integer id) throws SQLException {
        return UserDao.findUserById(id);
    }

    @Override
    public List<User> findUsers() throws SQLException {
        return UserDao.findUsers();
    }

    @Override
    public List<User> findUsersByEmail(String email) throws SQLException {
        return UserDao.findUsersByEmail(email);
    }

    @Override
    public int deleteUser(Integer id) throws SQLException {
        return UserDao.deleteUser(id);
    }

    @Override
    public boolean isUserAdmin(Integer id) throws SQLException {return UserDao.isUserAdmin(id);}
}
