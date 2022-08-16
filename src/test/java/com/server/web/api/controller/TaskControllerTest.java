package com.server.web.api.controller;

import com.server.web.MockSecurityConfiguration;
import com.server.web.config.BasicAuthWebSecurityConfiguration;
import com.server.web.entity.Application;
import com.server.web.entity.User;
import com.server.web.repository.UserRepository;
import com.server.web.security.UserRole;
import com.server.web.service.dto.TaskDTO;
import com.server.web.service.impl.TaskServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@Import({BasicAuthWebSecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskServiceImpl taskService;

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
    public void getTaskList_ok() throws Exception {
        when(taskService.getListTask(new User().getId())).thenReturn(asList(new TaskDTO().setId(1L).setStatus("RENDERING")));

        mockMvc.perform(get("/task/list"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"tasks\": [\n" +
                        "    {\n" +
                        "      \"id\": 1,\n" +
                        "      \"status\": \"RENDERING\",\n" +
                        "      \"endTime\": null\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}"));
    }

    @WithUserDetails(value = "russkov@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getTaskList_isEmpty() throws Exception {
        when(taskService.getListTask(new User().getId())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/task/list"))
                .andExpect(status().isNoContent());
    }

    @WithUserDetails(value = "russkov@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getTaskHistoryState_ok() throws Exception {
        when(taskService.getListTaskHistoryChangeState(new User().getId())).thenReturn(asList(new TaskDTO().setId(1L).setStatus("COMPLETE")));

        mockMvc.perform(get("/task/list/history"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"tasks\": [\n" +
                        "    {\n" +
                        "      \"id\": 1,\n" +
                        "      \"status\": \"COMPLETE\",\n" +
                        "      \"endTime\": null\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}"));
    }

    @WithUserDetails(value = "russkov@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getTaskHistoryState_isEmpty() throws Exception {
        when(taskService.getListTaskHistoryChangeState(new User().getId())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/task/list/history"))
                .andExpect(status().isNoContent());
    }

    @WithUserDetails(value = "russkov@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void addTask_ok() throws Exception {
        Application application = new Application();
        application.setId(1L);
        when(taskService.addTask(new User().getId(), application.getId())).thenReturn(new TaskDTO().setId(1L).setStatus("RENDERING"));

        mockMvc.perform(post("/task/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"applicationId\" : 1\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"status\": \"RENDERING\"\n" +
                        "}"));
    }

    @WithUserDetails(value = "russkov@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void addTask_badRequest() throws Exception {
        Application application = new Application();
        application.setId(1L);
        when(taskService.addTask(new User().getId(), application.getId())).thenReturn(null);

        mockMvc.perform(post("/task/add"))
                .andExpect(status().isBadRequest());
    }
}