package com.nnk.springboot;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)

@SpringBootTest
@AutoConfigureMockMvc
public class SpringWebAppTest {


    @Autowired
    private WebApplicationContext context;

    @Autowired
    MockMvc mock;


    @Test
    public void shouldReturnDefaultMessage() throws Exception{
        mock.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void userLoginFail()throws Exception{
        mock.perform(formLogin("/login")
                        .user("springuser")
                        .password("password"))
                .andExpect(unauthenticated());
    }

    @BeforeEach
    public void setup(){
        mock = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

}
