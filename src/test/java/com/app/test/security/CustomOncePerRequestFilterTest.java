package com.app.test.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.app.test.entity.Users;
import com.app.test.roles.Roles;
import com.app.test.services.TokenService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CustomOncePerRequestFilterTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenService tokenService;

    private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

    @BeforeEach
    void setup() {
        usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            "user",
            "user",
            AuthorityUtils.createAuthorityList(Roles.SAVE.name(), Roles.UPDATE.name())  
        );
    }

    @Test
    void doFilterInternal() throws Exception {
        String token = tokenService.encode(usernamePasswordAuthenticationToken);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/");
        request.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        ResultActions result = mockMvc.perform(request);
        MvcResult mvcResult = result.andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(usernamePasswordAuthenticationToken);

        assertNotNull(response);
        assertNotNull(securityContext.getAuthentication());
    }

    @Test
    void doFilterInternalFailed() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/");
        request.header(HttpHeaders.AUTHORIZATION, "Bearer test");
        
        assertThrows(JwtException.class, () -> {
            mockMvc.perform(request);
        });

    }
}
