package com.nnk.springboot.controllerTest;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    @MockBean
    private UserService userService;


    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testHome() throws Exception {

        List<User> userList = new ArrayList<>();
        User user = new User();

        user.setFullname("john doe");
        user.setUsername("john");
        user.setPassword("testpassword");

        userList.add(user);
        when(userService.findAll()).thenReturn(userList);


        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk());
    }
    @WithMockUser(authorities = "ADMIN")
    @Test
    public void OAuth2GitClienthomeTest() throws Exception {

        this.mockMvc.perform(get("/bidList/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @WithMockUser(username = "michel", password = "azerty", authorities = "ADMIN")
    @Test
    public void should_return_user_list() throws Exception {

        this.mockMvc.perform(get("/user/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "jean", password = "azerty", authorities = "USER")
    @Test
    public void should_return_Error_Page() throws Exception {

        this.mockMvc.perform(get("/user/list"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void should_return_Add_Page() throws Exception {

        this.mockMvc.perform(get("/user/add"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @WithMockUser(authorities = "ADMIN")
    @Test
    public void should_validate_user_inscription() throws Exception {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("fullname", "john doe");
        formData.add("username", "john");
        formData.add("password", "Azerty2023!");

        mockMvc.perform(post("/user/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(formData)
                        .with(csrf()))
                .andExpect(status().isOk());
    }


    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testShowUserUpdateForm() throws Exception {
        // Given
        int userId = 1;
        User user = new User();
        user.setId(userId);
        when(userService.findById(userId)).thenReturn(Optional.of(user));

        // When and Then
        mockMvc.perform(get("/user/update/" + userId))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attribute("user", user));
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testUserUpdate() throws Exception {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("fullname", "john doe");
        formData.add("username", "john");
        formData.add("password", "testpassword");

        String tradeId = String.valueOf(1);
        mockMvc.perform(post("/user/update/" + tradeId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(formData)
                        .with(csrf()))
                .andExpect(status().isOk());


    }


    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testDeleteByAdmin() throws Exception {

        User user = new User();
        user.setId(1);

        when(userService.findById(1)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        // Vérification que l'utilisateur a été supprimé
        verify(userService).delete(user);
    }

    @WithMockUser(authorities = "USER")
    @Test
    public void testDeleteByUserFail() throws Exception {

        User user = new User();
        user.setId(1);

        when(userService.findById(1)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/delete/{id}", 1))
                .andExpect(status().isForbidden());
    }


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

}
