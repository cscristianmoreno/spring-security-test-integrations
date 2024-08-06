package com.app.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.assertArg;

import javax.naming.AuthenticationException;

import org.hibernate.exception.spi.ViolatedConstraintNameExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.app.test.TestApplication;
import com.app.test.dto.LoginDTO;
import com.app.test.entity.Users;
import com.app.test.roles.Roles;
import com.app.test.security.SecurityConfig;
import com.app.test.services.UserRepositoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepositoryService userRepositoryService;

    private Users users;
    
    @BeforeEach 
    void setup() {
        users = new Users();
        users.setUsername("user");
        users.setPassword("user");
        users.setAuthorities(Roles.SAVE, Roles.UPDATE);

        // userRepositoryService.save(users);
    }

    @Test
    @Order(1)
    void register() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/auth/register");
        request.contentType(MediaType.APPLICATION_JSON);
        request.content(objectMapper.writeValueAsString(users));

        
        ResultActions resultActions = mockMvc.perform(request);
        MvcResult mvcResult = resultActions.andReturn();
        
        MockHttpServletResponse response = mvcResult.getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @Order(2)
    void registerAlreadyExists() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/auth/register");
        request.contentType(MediaType.APPLICATION_JSON);
        request.content(objectMapper.writeValueAsString(users));

        assertThrows(Exception.class, () -> {
            mockMvc.perform(request);
        });
    }

    @Test
    @Order(3)
    void login() throws JsonProcessingException, Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("user");
        loginDTO.setPassword("user");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginDTO));

        ResultActions resultActions = mockMvc.perform(request);
        MvcResult result = resultActions.andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertNotNull(response);
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
    }

    @Test
    @Order(4)
    void loginFailed() throws JsonProcessingException, Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("user");
        loginDTO.setPassword("userr");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/auth/login");
        request.contentType(MediaType.APPLICATION_JSON);
        request.content(objectMapper.writeValueAsString(loginDTO));

        ResultActions resultActions = mockMvc.perform(request); 
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }
}
