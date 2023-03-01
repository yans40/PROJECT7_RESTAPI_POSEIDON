package com.nnk.springboot;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SpringWebAppTest {


    @Autowired
    private WebApplicationContext context;

//    @BeforeEach
//    public void setup(){
//        mock=MockMvcBuilders
//                .webAppContextSetup(context)
//                .build();
//    }
//
//    @Test
//    public void shouldReturnDefaultMessage() throws Exception{
//        mock.perform(get("/login"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

//    @Test
//    public void userLoginTest()throws Exception{
//        mock.perform(formLogin("/login")
//                .user("jeanne")
//                .password("azerty"))
//        .andExpect(authenticated());
//    }
//
//    @Test
//    public void userLoginFail()throws Exception{
//        mock.perform(formLogin("/login")
//                        .user("springuser")
//                        .password("password"))
//                .andExpect(unauthenticated());
//    }
//
//    @BeforeEach
//    public void setup(){
//        mock = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }
}
