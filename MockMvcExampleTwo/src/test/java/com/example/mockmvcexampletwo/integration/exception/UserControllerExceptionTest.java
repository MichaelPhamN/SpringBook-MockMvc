package com.example.mockmvcexampletwo.integration.exception;

import com.example.mockmvcexampletwo.constant.URIConstant;
import com.example.mockmvcexampletwo.model.User;
import com.example.mockmvcexampletwo.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerExceptionTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userService;

    @Test
    public void getUserById_throwDataNotFoundException_thenReturn() throws Exception {
        //Mock
        when(userService.findUserById(10)).thenReturn(null);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URIConstant.GET_BY_ID, 1).contentType(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result =  resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isNotFound())
                .andReturn();
        String expected = "{\"statusCode\":404,\"message\":\"Data not found\",\"description\":\"uri=/api/user/1\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void saveUser_throwInvalidDataRequestException() throws Exception {
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URIConstant.POST)
                .content("{}")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();
        String expected = "{\"statusCode\":400,\"message\":\"User data is not null\",\"description\":\"uri=/api/user\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void saveUser_throwInvalidDataRequestException_CaseEmailIsNull() throws Exception {
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URIConstant.POST)
                .content("{\"password\":\"test\",\"isAdmin\":true}")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();
        String expected = "{\"statusCode\":400,\"message\":\"User data is not null\",\"description\":\"uri=/api/user\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void saveUser_throwInvalidDataRequestException_CasePasswordIsNull() throws Exception {
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URIConstant.POST)
                .content("{\"email\":\"test@example.com\",\"isAdmin\":true}")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();
        String expected = "{\"statusCode\":400,\"message\":\"User data is not null\",\"description\":\"uri=/api/user\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void saveUser_returnFail() throws Exception {
        //Setup
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("test");
        user.setIsAdmin(Boolean.TRUE);

        //Mock
        when(userService.addUser(user)).thenReturn(0);
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URIConstant.POST)
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
                .andExpect(status().isBadRequest())
                .andReturn();
        String expected = "Add account failure";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void editUser_throwInvalidDataRequestException() throws Exception {
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content("{}")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String expected = "{\"statusCode\":400,\"message\":\"User data is not null\",\"description\":\"uri=/api/user\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void editUser_throwInvalidDataRequestException_CaseIdIsNull() throws Exception {
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content("{\"email\":\"test@example.com\",\"password\":\"test\",\"isAdmin\":true}")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String expected = "{\"statusCode\":400,\"message\":\"User data is not null\",\"description\":\"uri=/api/user\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void editUser_throwInvalidDataRequestException_CaseEmailIsNull() throws Exception {
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content("{\"id\":1,\"password\":\"test\",\"isAdmin\":true}")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String expected = "{\"statusCode\":400,\"message\":\"User data is not null\",\"description\":\"uri=/api/user\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void editUser_throwInvalidDataRequestException_CasePasswordIsNull() throws Exception {
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content("{\"id\":1,\"password\":\"test\",\"isAdmin\":true}")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String expected = "{\"statusCode\":400,\"message\":\"User data is not null\",\"description\":\"uri=/api/user\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void editUser_throwDataNotFoundException() throws Exception {
        //Mock
        when(userService.findUserById(10)).thenReturn(null);
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content("{\"id\":10,\"email\":\"test@example.com\",\"password\":\"test\",\"isAdmin\":true}")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isNotFound())
                .andReturn();

        String expected = "{\"statusCode\":404,\"message\":\"Data not found\",\"description\":\"uri=/api/user\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void editUser_returnFail() throws Exception {
        //Setup
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setPassword("test");
        user.setIsAdmin(Boolean.TRUE);

        //Mock
        when(userService.findUserById(1)).thenReturn(user);
        when(userService.editUser(user)).thenReturn(0);
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
                .andExpect(status().isBadRequest())
                .andReturn();
        String expected = "Edit account failure";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void deleteUser_throwDataNotFoundException() throws Exception {
        //Mock
        when(userService.findUserById(10)).thenReturn(null);
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(URIConstant.DELETE_BY_ID, 10)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isNotFound())
                .andReturn();
        String expected = "{\"statusCode\":404,\"message\":\"Data not found\",\"description\":\"uri=/api/user/10\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void deleteUser_returnFail() throws Exception {
        //Setup
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setPassword("test");
        user.setIsAdmin(Boolean.TRUE);

        //Mock
        when(userService.findUserById(1)).thenReturn(user);
        when(userService.deleteUser(1)).thenReturn(0);
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
                .andExpect(status().isBadRequest())
                .andReturn();
        String expected = "Delete account failure";
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