package com.example.mockmvcexampletwo.component;

import com.example.mockmvcexampletwo.constant.URIConstant;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseIntegrationTest{
    @Autowired
    @Qualifier("mockMvc")
    private MockMvc mockMvc;

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findUsers_HappyPath() throws Exception {
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URIConstant.GET)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Create RequestBuilder
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                            .andExpect(header().string(CONTENT_TYPE, "application/json"))
                            .andExpect(content().contentType("application/json"))
                            .andExpect(status().isOk())
                            .andReturn();
        String expected = "[{\"id\":1,\"email\":\"admin@example.com\",\"password\":\"admin\",\"isAdmin\":true}," +
                "{\"id\":2,\"email\":\"user@gmail.com\",\"password\":\"user\",\"isAdmin\":false}," +
                "{\"id\":3,\"email\":\"test@example.com\",\"password\":\"test\",\"isAdmin\":false}]";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert_nocontent.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findUsers_NoContent_HappyPath() throws Exception {
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URIConstant.GET)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Create RequestBuilder
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findUserById_HappyPath() throws Exception {
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URIConstant.GET_BY_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Create RequestBuilder
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                            .andExpect(header().string(CONTENT_TYPE, "application/json"))
                            .andExpect(content().contentType("application/json"))
                            .andExpect(status().isOk())
                            .andReturn();
        String expected = "{\"id\":1,\"email\":\"admin@example.com\",\"password\":\"admin\",\"isAdmin\":true}";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findUsersByEmail_HappyPath() throws Exception {
        String email = "example.com";
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URIConstant.GET_BY_EMAIL)
                .param("email", email)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Create RequestBuilder
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String expected = "[{\"id\":1,\"email\":\"admin@example.com\",\"password\":\"admin\",\"isAdmin\":true},{\"id\":3,\"email\":\"test@example.com\",\"password\":\"test\",\"isAdmin\":false}]";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void isUserAdmin_HappyPath() throws Exception {
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URIConstant.IS_ADMIN_BY_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Create RequestBuilder
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                            .andExpect(header().string(CONTENT_TYPE, "application/json"))
                            .andExpect(content().contentType("application/json"))
                            .andExpect(status().isOk())
                            .andReturn();

        String expected = "true";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteUser_HappyPath() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(URIConstant.DELETE_BY_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();

        String expected = "Delete account successful";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addUser_HappyPath() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URIConstant.POST)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{ \"email\":\"sample@example.com\",\"password\":\"sample\",\"isAdmin\":false}");
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        String expected = "Add account successful";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void editUser_HappyPath() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{ \"id\":1,\"email\":\"test1@example.com\",\"password\":\"test1@example.com\",\"isAdmin\":true}");
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String expected = "Edit account successful";
        assertEquals(expected, result.getResponse().getContentAsString());
    }
}
//https://github.com/briansjavablog/rest-controller-testing-mock-mvc