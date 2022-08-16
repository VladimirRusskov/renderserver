package com.server.web.api.controller;

import com.server.web.MockSecurityConfiguration;
import com.server.web.config.BasicAuthWebSecurityConfiguration;
import com.server.web.service.dto.UserDTO;
import com.server.web.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({BasicAuthWebSecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;

    @Test
    public void auth_ok() throws Exception {
        UserDTO user = new UserDTO(1L, "russkov@gmail.com");
        when(userService.findUserByEmailAndPassword("russkov@gmail.com", "password")).thenReturn(user);

        mockMvc.perform(post("/auth")
                        .contentType(APPLICATION_JSON)
                        .content("{\n" +
                                "  \"email\" : \"russkov@gmail.com\",\n" +
                                "  \"password\" : \"password\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"email\": \"russkov@gmail.com\"\n" +
                        "}"));
    }

    @Test
    public void auth_userNotFound() throws Exception {
        when(userService.findUserByEmailAndPassword("russkov@gmail.com", "password")).thenReturn(null);

        mockMvc.perform(post("/auth")
                        .contentType(APPLICATION_JSON)
                        .content("{\n" +
                                "  \"email\" : \"russkov@gmail.com\",\n" +
                                "  \"password\" : \"password\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void registration_ok() throws Exception {
        UserDTO user = new UserDTO(1L, "russkov@gmail.com");
        when(userService.addUser("russkov@gmail.com", "password")).thenReturn(user);

        mockMvc.perform(post("/registration")
                        .contentType(APPLICATION_JSON)
                        .content("{\n" +
                                "  \"email\": \"russkov@gmail.com\",\n" +
                                "  \"password\": \"password\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"email\": \"russkov@gmail.com\"\n" +
                        "}"));
    }

    @Test
    public void registration_userExist() throws Exception {
        UserDTO user = new UserDTO(1L, "russkov@gmail.com");
        when(userService.findUserByEmail("russkov@gmail.com")).thenReturn(user);

        mockMvc.perform(post("/registration")
                        .contentType(APPLICATION_JSON)
                        .content("{\n" +
                                "  \"email\": \"russkov@gmail.com\",\n" +
                                "  \"password\": \"password\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isConflict());
    }
}