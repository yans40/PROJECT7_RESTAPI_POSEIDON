package com.nnk.springboot.controllerTests;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

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


    @WithMockUser(username = "michel",password = "azerty",authorities = "ADMIN")
    @Test
    public void should_return_user_list()throws Exception{

        this.mockMvc.perform(get("/user/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "jean",password = "azerty",authorities = "USER")
    @Test
    public void should_return_Error_Page()throws Exception{

        this.mockMvc.perform(get("/user/list"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void should_validate_user_inscription()throws Exception {
        MultiValueMap<String,String> formData =  new LinkedMultiValueMap<>();
        formData.add("fullname","john doe");
        formData.add("username","john");
        formData.add("password","testpassword");

        mockMvc.perform(post("/user/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(formData)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testShowUserUpdateForm() throws Exception {
        // Given
        int userId = 1;
        User user = new User();
        user.setId(userId);
        Mockito.when(userService.findById(userId)).thenReturn(Optional.of(user));

        // When and Then
        mockMvc.perform(get("/user/update/" + userId))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attribute("user", user));
    }



    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

}
