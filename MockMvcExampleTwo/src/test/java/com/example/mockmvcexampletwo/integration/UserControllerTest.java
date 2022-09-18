package com.example.mockmvcexampletwo.integration;

import com.example.mockmvcexampletwo.constant.URIConstant;
import com.example.mockmvcexampletwo.model.User;
import com.example.mockmvcexampletwo.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userService;

    @Test
    public void getUsersEmpty() throws Exception {
        //Setup
        List<User> users = new ArrayList<>();

        //Mock
        when(userService.findUsers()).thenReturn(users);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URIConstant.GET).contentType(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void getUsers() throws Exception {
        //Setup
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setPassword("test");
        user.setIsAdmin(Boolean.TRUE);
        users.add(user);

        user.setId(1);
        user.setEmail("user@example.com");
        user.setPassword("user");
        user.setIsAdmin(Boolean.FALSE);
        users.add(user);

        //Mock
        when(userService.findUsers()).thenReturn(users);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URIConstant.GET).contentType(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String expected = "[{\"id\":1,\"email\":\"user@example.com\",\"password\":\"user\",\"isAdmin\":false},{\"id\":1,\"email\":\"user@example.com\",\"password\":\"user\",\"isAdmin\":false}]";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void getUsersByNameEmailEmpty() throws Exception {
        String email = "example.com";
        //Setup
        List<User> users = new ArrayList<>();

        //Mock
        when(userService.findUsersByEmail(email)).thenReturn(users);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URIConstant.GET_BY_EMAIL)
                .param("email", email)
                .contentType(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void getUsersByNameEmail() throws Exception {
        String email = "example.com";
        //Setup
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setPassword("test");
        user.setIsAdmin(Boolean.TRUE);
        users.add(user);

        user.setId(1);
        user.setEmail("user@example.com");
        user.setPassword("user");
        user.setIsAdmin(Boolean.FALSE);
        users.add(user);

        //Mock
        when(userService.findUsersByEmail(email)).thenReturn(users);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URIConstant.GET_BY_EMAIL)
                .param("email", email)
                .contentType(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String expected = "[{\"id\":1,\"email\":\"user@example.com\",\"password\":\"user\",\"isAdmin\":false},{\"id\":1,\"email\":\"user@example.com\",\"password\":\"user\",\"isAdmin\":false}]";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void getUserById() throws Exception {
        //Setup
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setPassword("test");
        user.setIsAdmin(Boolean.TRUE);

        //Mock
        when(userService.findUserById(1)).thenReturn(user);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URIConstant.GET_BY_ID, 1).contentType(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String expected = "{\"id\":1,\"email\":\"test@example.com\",\"password\":\"test\",\"isAdmin\":true}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void saveUser() throws Exception {
        //Setup
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("test");
        user.setIsAdmin(Boolean.TRUE);

        //Mock
        when(userService.addUser(user)).thenReturn(1);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URIConstant.POST)
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isCreated())
                .andReturn();
        String expected = "Add account successful";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void editUser() throws Exception {
        //Setup
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setPassword("test");
        user.setIsAdmin(Boolean.TRUE);

        when(userService.findUserById(user.getId())).thenReturn(user);
        when(userService.editUser(user)).thenReturn(1);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content(asJsonString(user))
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String expected = "Edit account successful";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void deleteUser() throws Exception {
        //Setup
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setPassword("test");
        user.setIsAdmin(Boolean.TRUE);

        when(userService.findUserById(user.getId())).thenReturn(user);
        when(userService.deleteUser(user.getId())).thenReturn(1);
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(URIConstant.DELETE_BY_ID, 1)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isNoContent())
                .andReturn();
        String expected = "Delete account successful";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}