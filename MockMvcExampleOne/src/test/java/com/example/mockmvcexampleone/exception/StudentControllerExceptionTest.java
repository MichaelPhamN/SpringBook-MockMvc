package com.example.mockmvcexampleone.exception;

import com.example.mockmvcexampleone.constant.URIConstant;
import com.example.mockmvcexampleone.service.StudentService;
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

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerExceptionTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;

    @Test
    public void getStudentById_throwDataNotFoundException() throws Exception {
        //Mock
        when(studentService.getStudentById(10)).thenReturn(null);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URIConstant.GET_BY_ID, 1).contentType(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", Matchers.equalTo("Data not found")));
    }

    @Test
    public void getStudentById_throwDataNotFoundException_thenReturn() throws Exception {
        //Mock
        when(studentService.getStudentById(10)).thenReturn(null);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URIConstant.GET_BY_ID, 1).contentType(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result =  resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String expected = "{\"statusCode\":404,\"message\":\"Data not found\",\"description\":\"uri=/ex1/student/1\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void saveStudent_throwInvalidDataRequestException() throws Exception {
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
        resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.equalTo("Student data is not null")));
    }

    @Test
    public void saveStudent_throwInvalidDataRequestException_thenReturn() throws Exception {
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
                .andReturn();
        String expected = "{\"statusCode\":400,\"message\":\"Student data is not null\",\"description\":\"uri=/ex1/student\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void editStudent_throwInvalidDataRequestException_CaseBothIdAndNameIsNull() throws Exception {
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
        resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.equalTo("Student data is not null")));
    }

    @Test
    public void editStudent_throwInvalidDataRequestException_CaseBothIdAndNameIsNull_thenReturn() throws Exception {
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
                .andReturn();

        String expected = "{\"statusCode\":400,\"message\":\"Student data is not null\",\"description\":\"uri=/ex1/student\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void editStudent_throwInvalidDataRequestException_CaseIdIsNull() throws Exception {
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content("{\"name\":\"Michael Ballack\"}")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.equalTo("Student data is not null")));
    }

    @Test
    public void editStudent_throwInvalidDataRequestException_CaseIdIsNull_thenReturn() throws Exception {
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content("{\"name\":\"Michael Ballack\"}")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andReturn();

        String expected = "{\"statusCode\":400,\"message\":\"Student data is not null\",\"description\":\"uri=/ex1/student\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void editStudent_throwInvalidDataRequestException_CaseNameIsNull() throws Exception {
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content("{\"id\":1}")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.equalTo("Student data is not null")));
    }

    @Test
    public void editStudent_throwInvalidDataRequestException_CaseNameIsNull_thenReturn() throws Exception {
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content("{\"id\":1}")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andReturn();

        String expected = "{\"statusCode\":400,\"message\":\"Student data is not null\",\"description\":\"uri=/ex1/student\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void editStudent_throwDataNotFoundException() throws Exception {
        //Mock
        when(studentService.getStudentById(10)).thenReturn(null);
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content("{\"id\":10,\"name\":\"Michael Ballack\"}\"}")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", Matchers.equalTo("Data not found")));
    }

    @Test
    public void editStudent_throwDataNotFoundException_thenReturn() throws Exception {
        //Mock
        when(studentService.getStudentById(10)).thenReturn(null);
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content("{\"id\":10,\"name\":\"Michael Ballack\"}\"}")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andReturn();

        String expected = "{\"statusCode\":404,\"message\":\"Data not found\",\"description\":\"uri=/ex1/student\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void deleteStudent_throwDataNotFoundException() throws Exception {
        //Mock
        when(studentService.getStudentById(10)).thenReturn(null);
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(URIConstant.DELETE_BY_ID, 10)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", Matchers.equalTo("Data not found")));
    }

    @Test
    public void deleteStudent_throwDataNotFoundException_thenReturn() throws Exception {
        //Mock
        when(studentService.getStudentById(10)).thenReturn(null);
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
                .andReturn();
        String expected = "{\"statusCode\":404,\"message\":\"Data not found\",\"description\":\"uri=/ex1/student/10\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

}