package com.server.web.api.controller;

import com.server.web.MockSecurityConfiguration;
import com.server.web.config.BasicAuthWebSecurityConfiguration;
import com.server.web.entity.User;
import com.server.web.repository.UserRepository;
import com.server.web.security.UserRole;
import com.server.web.service.dto.ApplicationDTO;
import com.server.web.service.impl.ApplicationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApplicationController.class)
@Import({BasicAuthWebSecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class ApplicationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ApplicationServiceImpl applicationService;

    @MockBean
    UserRepository userRepository;

    @Before
    public void setUp() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(new User()
                .setEmail("russkov@gmail.com")
                .setPassword("password")
                .setRoles(Collections.singleton(UserRole.USER)))
        );
    }

    @WithUserDetails(value = "russkov@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getAppList_ok() throws Exception {
        when(applicationService.getListApplication()).thenReturn(asList(new ApplicationDTO(1L, "app1")));

        mockMvc.perform(get("/application/list"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"applications\": [\n" +
                        "    {\n" +
                        "      \"id\": 1,\n" +
                        "      \"name\": \"app1\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}"));
    }
}