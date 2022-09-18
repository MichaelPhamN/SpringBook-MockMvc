package com.example.mockmvcexampletwo.dao.impl;

import com.example.mockmvcexampletwo.config.Config;
import com.example.mockmvcexampletwo.exception.InvalidDataRequestException;
import com.example.mockmvcexampletwo.model.User;
import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;


@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest_UT {
    @InjectMocks
    private UserDaoImpl userDao;
    @Mock
    private Config config;
    @Mock
    private Connection conn;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    private List<User> users;
    private static final String ADD_QUERY = "INSERT INTO Account(email, password, isAdmin) VALUES (?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE Account as acc SET acc.email = ?, acc.password = ?, acc.isAdmin = ? WHERE acc.id = ?";
    private static final String SELECT_USER_BY_ID_QUERY = "SELECT acc.id, acc.email, acc.password, acc.isAdmin FROM Account as acc WHERE acc.id = ?";
    private static final String SELECT_USERS_QUERY = "SELECT acc.id, acc.email, acc.password, acc.isAdmin FROM Account as acc";
    private static final String DELETE_USER_QUERY = "DELETE FROM Account as acc WHERE acc.id = ?";
    private static final String SELECT_USER_ADMIN_QUERY = "SELECT acc.id, acc.email, acc.password, acc.isAdmin FROM Account as acc WHERE acc.id = ? AND acc.isAdmin = 1";
    private static final String SELECT_USERS_BY_EMAIL = "SELECT acc.id, acc.email, acc.password, acc.isAdmin FROM Account as acc WHERE acc.email like ?";


    @Before
    public void setUp() throws SQLException {
        Faker faker = new Faker();
        users = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(faker.random().nextInt(100));
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(faker.internet().password());
            user.setIsAdmin(faker.random().nextBoolean());
            users.add(user);
        }
        Assert.assertNotNull(config);
        when(config.getConnection()).thenReturn(conn);
    }

    @Test
    public void nullCreateThrowsException() {
        Exception exception = assertThrows(InvalidDataRequestException.class, () -> {
            userDao.addUser(null);
        });
        String expectedException = "User object is not null";
        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    public void sqlCreateThrowsException() throws SQLException {
        when(conn.prepareStatement(ADD_QUERY)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        Exception exception = assertThrows(SQLException.class, () ->
                userDao.addUser(users.get(9))
        );
        String expectedException = "Internal exception has been occurred";
        assertEquals(expectedException, exception.getMessage());
        verify(conn).rollback();
    }

    @Test
    public void addUser() throws SQLException {
        when(conn.prepareStatement(ADD_QUERY)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        int expectedResponse = 1;
        Assert.assertEquals(expectedResponse, userDao.addUser(users.get(9)));
        verify(conn, times(1)).setAutoCommit(false);
        verify(conn, times(1)).prepareStatement(ADD_QUERY);
        verify(preparedStatement, times(1)).setString(eq(1), anyString());
        verify(preparedStatement, times(1)).setString(eq(2), anyString());
        verify(preparedStatement, times(1)).setBoolean(eq(3), anyBoolean());
        verify(conn).commit();
        verify(preparedStatement).close();
    }

    @Test
    public void nullEditThrowsException() {
        Exception exception = assertThrows(InvalidDataRequestException.class, () -> {
            userDao.editUser(null);
        });
        String expectedException = "User object is not null";
        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    public void sqlEditThrowsException() throws SQLException {
        when(conn.prepareStatement(UPDATE_QUERY)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        Exception exception = assertThrows(SQLException.class, () ->
                userDao.editUser(users.get(9))
        );
        String expectedException = "Internal exception has been occurred";
        assertEquals(expectedException, exception.getMessage());
        verify(conn).rollback();
    }

    @Test
    public void editUser() throws SQLException {
        when(conn.prepareStatement(UPDATE_QUERY)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        int expectedResponse = 1;
        Assert.assertEquals(expectedResponse, userDao.editUser(users.get(9)));
        verify(conn, times(1)).prepareStatement(UPDATE_QUERY);
        verify(preparedStatement, times(1)).setString(eq(1), anyString());
        verify(preparedStatement, times(1)).setString(eq(2), anyString());
        verify(preparedStatement, times(1)).setBoolean(eq(3), anyBoolean());
        verify(preparedStatement, times(1)).setInt(eq(4), anyInt());
        verify(conn).commit();
        verify(preparedStatement).close();
    }

    @Test
    public void nullFindUserByIdThrowsException() {
        Exception exception = assertThrows(InvalidDataRequestException.class, () -> {
            userDao.findUserById(null);
        });
        String expectedException = "User id is not null";
        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    public void sqlFindUserByIdThrowsException() throws SQLException {
        when(conn.prepareStatement(SELECT_USER_BY_ID_QUERY)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        Exception exception = assertThrows(SQLException.class, () ->
                userDao.findUserById(users.get(9).getId())
        );
        String expectedException = "Internal exception has been occurred";
        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    public void findUserById() throws SQLException {
        when(conn.prepareStatement(SELECT_USER_BY_ID_QUERY)).thenReturn(preparedStatement);
        preparedStatement.setInt(eq(1), anyInt());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);;
        when(resultSet.getInt("id")).thenReturn(users.get(9).getId());
        when(resultSet.getString("email")).thenReturn(users.get(9).getEmail());
        when(resultSet.getString("password")).thenReturn(users.get(9).getPassword());
        when(resultSet.getBoolean("isAdmin")).thenReturn(users.get(9).getIsAdmin());

        User returnedUser = userDao.findUserById(users.get(9).getId());
        assertNotNull(returnedUser);
        assertEquals(users.get(9).getId(), returnedUser.getId());
        assertEquals(users.get(9).getEmail(), returnedUser.getEmail());
        assertEquals(users.get(9).getPassword(), returnedUser.getPassword());
        assertEquals(users.get(9).getIsAdmin(), returnedUser.getIsAdmin());
        verify(conn, times(1)).prepareStatement(SELECT_USER_BY_ID_QUERY);
        verify(preparedStatement, times(1)).setInt(eq(1), anyInt());
        verify(resultSet).close();
        verify(preparedStatement).close();
    }

    @Test
    public void sqlFindUsersByEmailThrowsException() throws SQLException {
        when(conn.prepareStatement(SELECT_USERS_BY_EMAIL)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        Exception exception = assertThrows(SQLException.class, () ->
                userDao.findUsersByEmail(users.get(9).getEmail())
        );
        String expectedException = "Internal exception has been occurred";
        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    public void findUsersByEmail() throws SQLException {
        when(conn.prepareStatement(SELECT_USERS_BY_EMAIL)).thenReturn(preparedStatement);
        preparedStatement.setString(eq(1), anyString());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);;
        when(resultSet.getInt("id")).thenReturn(users.get(9).getId());
        when(resultSet.getString("email")).thenReturn(users.get(9).getEmail());
        when(resultSet.getString("password")).thenReturn(users.get(9).getPassword());
        when(resultSet.getBoolean("isAdmin")).thenReturn(users.get(9).getIsAdmin());
        List<User> returnedUsers = userDao.findUsersByEmail(users.get(9).getEmail());
        assertNotNull(returnedUsers);
        assertEquals(1, returnedUsers.size());
        verify(conn, times(1)).prepareStatement(SELECT_USERS_BY_EMAIL);
        verify(resultSet).close();
        verify(preparedStatement).close();
    }

    @Test
    public void sqlFindUsersThrowsException() throws SQLException {
        when(conn.prepareStatement(SELECT_USERS_QUERY)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        Exception exception = assertThrows(SQLException.class, () ->
                userDao.findUsers()
        );
        String expectedException = "Internal exception has been occurred";
        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    public void findUsers() throws SQLException {
        when(conn.prepareStatement(SELECT_USERS_QUERY)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);;
        when(resultSet.getInt("id")).thenReturn(users.get(9).getId());
        when(resultSet.getString("email")).thenReturn(users.get(9).getEmail());
        when(resultSet.getString("password")).thenReturn(users.get(9).getPassword());
        when(resultSet.getBoolean("isAdmin")).thenReturn(users.get(9).getIsAdmin());

        List<User> returnedUsers = userDao.findUsers();
        assertNotNull(returnedUsers);
        assertEquals(1, returnedUsers.size());
        verify(conn, times(1)).prepareStatement(SELECT_USERS_QUERY);
        verify(resultSet).close();
        verify(preparedStatement).close();
    }

    @Test
    public void nullDeleteUserThrowsException() {
        Exception exception = assertThrows(InvalidDataRequestException.class, () -> {
            userDao.deleteUser(null);
        });
        String expectedException = "User id is not null";
        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    public void sqlDeleteUserThrowsException() throws SQLException {
        when(conn.prepareStatement(DELETE_USER_QUERY)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        Exception exception = assertThrows(SQLException.class, () ->
                userDao.deleteUser(users.get(9).getId())
        );
        String expectedException = "Internal exception has been occurred";
        assertEquals(expectedException, exception.getMessage());
        verify(conn).rollback();
    }

    @Test
    public void deleteUser() throws SQLException {
        when(conn.prepareStatement(DELETE_USER_QUERY)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        int expectedResponse = 1;
        Assert.assertEquals(expectedResponse, userDao.deleteUser(users.get(9).getId()));
        verify(conn, times(1)).prepareStatement(DELETE_USER_QUERY);
        verify(preparedStatement, times(1)).setInt(eq(1), anyInt());
        verify(conn).commit();
        verify(preparedStatement).close();
    }

    @Test
    public void nullIsUserAdminThrowsException() {
        Exception exception = assertThrows(InvalidDataRequestException.class, () -> {
            userDao.isUserAdmin(null);
        });
        String expectedException = "User id is not null";
        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    public void sqlIsUserAdminThrowsException() throws SQLException {
        when(conn.prepareStatement(SELECT_USER_ADMIN_QUERY)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        Exception exception = assertThrows(SQLException.class, () ->
                userDao.isUserAdmin(users.get(9).getId())
        );
        String expectedException = "Internal exception has been occurred";
        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    public void isUserAdmin() throws SQLException {
        when(conn.prepareStatement(SELECT_USER_ADMIN_QUERY)).thenReturn(preparedStatement);
        preparedStatement.setInt(eq(1), anyInt());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);;
        when(resultSet.getInt("id")).thenReturn(users.get(9).getId());
        when(resultSet.getString("email")).thenReturn(users.get(9).getEmail());
        when(resultSet.getString("password")).thenReturn(users.get(9).getPassword());
        when(resultSet.getBoolean("isAdmin")).thenReturn(users.get(9).getIsAdmin());

        Boolean returnedUser = userDao.isUserAdmin(users.get(9).getId());
        assertTrue(returnedUser);
        verify(conn, times(1)).prepareStatement(SELECT_USER_ADMIN_QUERY);
        verify(preparedStatement, times(1)).setInt(eq(1), anyInt());
        verify(resultSet).close();
        verify(preparedStatement).close();
    }
}