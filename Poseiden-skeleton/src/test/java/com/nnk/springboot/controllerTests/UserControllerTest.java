package com.nnk.springboot.controllerTests;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;


    @WithMockUser(username = "michel",password = "azerty",authorities = {"USER","ADMIN"})
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

    @WithMockUser(username = "yan",password = "azerty",authorities = "ADMIN")
    @Test
    public void should_update_user()throws Exception{

        this.mockMvc.perform(get("/user/update/{id}",1))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "yan",password = "azerty",authorities = "ADMIN")
    @Test
    public void should_delete_user()throws Exception{

        this.mockMvc.perform(get("/user/update/{id}",1))
                .andDo(print())
                .andExpect(status().isOk());
    }



    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

}
