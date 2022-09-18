package com.example.mockmvcexampleone.controller;

import com.example.mockmvcexampleone.constant.URIConstant;
import com.example.mockmvcexampleone.model.Student;
import com.example.mockmvcexampleone.service.StudentService;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;

    @Test
    public void getStudentsEmpty() throws Exception {
        //Setup
        List<Student> students = new ArrayList<>();

        //Mock
        when(studentService.getStudents()).thenReturn(students);

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
    public void getStudentsEmpty_thenReturn() throws Exception {
        //Setup
        List<Student> students = new ArrayList<>();

        //Mock
        when(studentService.getStudents()).thenReturn(students);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URIConstant.GET).contentType(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(status().isNoContent())
                .andReturn();
        String expected = "[]";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void getStudents() throws Exception {
        //Setup
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setId(1);
        student.setName("Mike Jack");
        students.add(student);

        student = new Student();
        student.setId(2);
        student.setName("Michael Ballack");
        students.add(student);

        //Mock
        when(studentService.getStudents()).thenReturn(students);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URIConstant.GET).contentType(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        resultActions.andDo(print())
            .andExpect(header().string(CONTENT_TYPE, "application/json"))
            .andExpect(content().contentType("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(2)))
            .andExpect(jsonPath("$[0].name", Matchers.equalTo("Mike Jack")))
            .andExpect(jsonPath("$[1].name", Matchers.equalTo("Michael Ballack")));
    }

    @Test
    public void getStudents_thenReturn() throws Exception {
        //Setup
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setId(1);
        student.setName("Mike Jack");
        students.add(student);

        student = new Student();
        student.setId(2);
        student.setName("Michael Ballack");
        students.add(student);

        //Mock
        when(studentService.getStudents()).thenReturn(students);

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
        String expected = "[{\"id\":1,\"name\":\"Mike Jack\"},{\"id\":2,\"name\":\"Michael Ballack\"}]";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void getStudentByNameEmpty() throws Exception {
        String firstName = "Michael";
        //Setup
        List<Student> students = new ArrayList<>();

        //Mock
        when(studentService.getStudents()).thenReturn(students);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URIConstant.GET_BY_NAME)
                .param("name", firstName)
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
    public void getStudentByNameEmpty_thenReturn() throws Exception {
        String firstName = "Michael";
        //Setup
        List<Student> students = new ArrayList<>();

        //Mock
        when(studentService.getStudents()).thenReturn(students);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URIConstant.GET_BY_NAME)
                .param("name", firstName)
                .contentType(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(status().isNoContent())
                .andReturn();
        String expected = "[]";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void getStudentByName() throws Exception {
        String firstName = "Michael";
        //Setup
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setId(1);
        student.setName("Michael Ballack");
        students.add(student);

        //Mock
        when(studentService.getStudentByName(firstName)).thenReturn(students);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URIConstant.GET_BY_NAME)
                .param("name", firstName)
                .contentType(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("Michael Ballack")));
    }

    @Test
    public void getStudentByName_thenReturn() throws Exception {
        String firstName = "Michael";
        //Setup
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setId(1);
        student.setName("Michael Ballack");
        students.add(student);

        //Mock
        when(studentService.getStudentByName(firstName)).thenReturn(students);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URIConstant.GET_BY_NAME)
                .param("name", firstName)
                .contentType(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        MvcResult result = resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String expected = "[{\"id\":1,\"name\":\"Michael Ballack\"}]";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void getStudentById() throws Exception {
        //Setup
        Student student = new Student();
        student.setId(1);
        student.setName("Michael Ballack");

        //Mock
        when(studentService.getStudentById(1)).thenReturn(student);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URIConstant.GET_BY_ID, 1).contentType(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.equalTo("Michael Ballack")));
    }

    @Test
    public void getStudentById_thenReturn() throws Exception {
        //Setup
        Student student = new Student();
        student.setId(1);
        student.setName("Michael Ballack");

        //Mock
        when(studentService.getStudentById(1)).thenReturn(student);

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
        String expected = "{\"id\":1,\"name\":\"Michael Ballack\"}";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void saveStudent() throws Exception {
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URIConstant.POST)
                .content("{\"name\":\"Mike\"}")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", Matchers.equalTo("create new student")));
    }

    @Test
    public void saveStudent_thenReturn() throws Exception {
        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URIConstant.POST)
                .content("{\"name\":\"Mike\"}")
                .characterEncoding("utf-8")
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
        String expected = "create new student";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void editStudent() throws Exception {
        //Setup
        Student student = new Student();
        student.setId(1);
        student.setName("Michael Ballack");

        when(studentService.getStudentById(student.getId())).thenReturn(student);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content(asJsonString(student))
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.equalTo("updated student")));
    }

    @Test
    public void editStudent_thenReturn() throws Exception {
        //Setup
        Student student = new Student();
        student.setId(1);
        student.setName("Michael Ballack");

        when(studentService.getStudentById(student.getId())).thenReturn(student);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(URIConstant.PUT)
                .content(asJsonString(student))
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
        String expected = "updated student";
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void deleteStudent() throws Exception {
        //Setup
        Student student = new Student();
        student.setId(1);
        student.setName("Michael Ballack");

        when(studentService.getStudentById(student.getId())).thenReturn(student);

        //Create RequestBuilder
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(URIConstant.DELETE_BY_ID, 1)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Send GET request
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //Validate
        resultActions.andDo(print())
                .andExpect(header().string(CONTENT_TYPE, "application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.equalTo("deleted student")));
    }

    @Test
    public void deleteStudent_thenReturn() throws Exception {
        //Setup
        Student student = new Student();
        student.setId(1);
        student.setName("Michael Ballack");

        when(studentService.getStudentById(student.getId())).thenReturn(student);

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
                .andExpect(status().isOk())
                .andReturn();
        String expected = "deleted student";
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