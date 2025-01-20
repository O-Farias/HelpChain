package com.helpchain.controllers;

import com.helpchain.models.User;
import com.helpchain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@WithMockUser(username = "testuser", roles = {"USER"}) 
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User sampleUser;

    @BeforeEach
    public void setUp() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setName("Test User");
        sampleUser.setEmail("testuser@example.com");
        sampleUser.setPassword("password123");
        sampleUser.setRole("USER");
    }

    @Test
    public void testCreateUser() throws Exception {
        when(userService.createUser(Mockito.any(User.class))).thenReturn(sampleUser);

        String userJson = """
                {
                    \"name\": \"Test User\",
                    \"email\": \"testuser@example.com\",
                    \"password\": \"password123\",
                    \"role\": \"USER\"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleUser.getId()))
                .andExpect(jsonPath("$.name").value(sampleUser.getName()))
                .andExpect(jsonPath("$.email").value(sampleUser.getEmail()));
    }

    @Test
    public void testGetUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(sampleUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleUser.getId()))
                .andExpect(jsonPath("$.name").value(sampleUser.getName()))
                .andExpect(jsonPath("$.email").value(sampleUser.getEmail()));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        User anotherUser = new User();
        anotherUser.setId(2L);
        anotherUser.setName("Another User");
        anotherUser.setEmail("anotheruser@example.com");
        anotherUser.setPassword("password123");
        anotherUser.setRole("USER");

        when(userService.getAllUsers()).thenReturn(Arrays.asList(sampleUser, anotherUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleUser.getId()))
                .andExpect(jsonPath("$[1].id").value(anotherUser.getId()));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent());
    }
}
