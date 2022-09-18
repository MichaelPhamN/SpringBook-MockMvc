package com.example.mockmvcexampletwo.component.exception;

import com.example.mockmvcexampletwo.MockMvcExampleTwoApplication;
import com.example.mockmvcexampletwo.constant.URIConstant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

//Another way is BaseIntegrationTest
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes={ MockMvcExampleTwoApplication.class })
public class UserControllerExceptionTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findUserById_ReturnFail() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URIConstant.GET_BY_ID, 4)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isNotFound())
                .andReturn();
        String expected = "{\"statusCode\":404,\"message\":\"Data not found\",\"description\":\"uri=/api/user/4\"}";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void isUserAdmin_ReturnFail() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URIConstant.IS_ADMIN_BY_ID, 4)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isNotFound())
                .andReturn();
        String expected = "{\"statusCode\":404,\"message\":\"Data not found\",\"description\":\"uri=/api/user/isAdmin/4\"}";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteUser_ReturnFail() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(URIConstant.DELETE_BY_ID, 4)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isNotFound())
                .andReturn();
        String expected = "{\"statusCode\":404,\"message\":\"Data not found\",\"description\":\"uri=/api/user/4\"}";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addUser_EmptyData_ReturnFail() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URIConstant.POST)
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String expected = "{\"statusCode\":400,\"message\":\"User data is not null\",\"description\":\"uri=/api/user\"}";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addUser_EmptyPassword_ReturnFail() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URIConstant.POST)
                .content("{\"email\":\"data@gmail.com\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String expected = "{\"statusCode\":400,\"message\":\"User data is not null\",\"description\":\"uri=/api/user\"}";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addUser_EmptyEmail_ReturnFail() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URIConstant.POST)
                .content("{\"password\":\"data\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String expected = "{\"statusCode\":400,\"message\":\"User data is not null\",\"description\":\"uri=/api/user\"}";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void editUser_EmptyData_ReturnFail() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String expected = "{\"statusCode\":400,\"message\":\"User data is not null\",\"description\":\"uri=/api/user\"}";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void editUser_EmptyId_ReturnFail() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content("{\"email\":\"data@gmail.com\",\"password\":\"data\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String expected = "{\"statusCode\":400,\"message\":\"User data is not null\",\"description\":\"uri=/api/user\"}";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void editUser_EmptyPassword_ReturnFail() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content("{\"id\": 4,\"email\":\"data@gmail.com\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String expected = "{\"statusCode\":400,\"message\":\"User data is not null\",\"description\":\"uri=/api/user\"}";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void editUser_EmptyEmail_ReturnFail() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content("{\"id\": 4,\"password\":\"data\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String expected = "{\"statusCode\":400,\"message\":\"User data is not null\",\"description\":\"uri=/api/user\"}";
        assertEquals(expected, result.getResponse().getContentAsString());
    }
}