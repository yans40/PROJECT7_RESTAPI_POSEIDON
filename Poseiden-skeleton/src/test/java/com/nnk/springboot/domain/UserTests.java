package com.nnk.springboot.domain;

import com.nnk.springboot.repositories.UserRepository;
import org.junit.Assert;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserTests {
    @Autowired
    UserRepository userRepository;

    @Test
    public void userTest() {

        User user = new User();
        user.setFullname("Alexandre Dumas");
        user.setUsername("Alex");
        user.setRole("user");

        //Save
        user = userRepository.save(user);
        Assert.assertNotNull(user.getId());
        Assert.assertTrue(user.getUsername()=="Alex");

        //Update
        user.setUsername("Al");
        user= userRepository.save(user);
        Assert.assertTrue(user.getUsername()=="Al");

        //Find
        List<User> userList = userRepository.findAll();
        Assert.assertTrue(userList.size()>0);
    }

}
