package com.nnk.springboot.domainTest;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.Assert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void userTest() {

        User user = new User();
        user.setFullname("Alexandre Dumas");
        user.setUsername("Alex");
        user.setRole("user");

        //Save
        user = userRepository.save(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertSame("Alex", user.getUsername());

        //Update
        user.setUsername("Al");
        user= userRepository.save(user);
        Assertions.assertSame("Al", user.getUsername());

        //Find
        List<User> userList = userRepository.findAll();
        Assertions.assertTrue(userList.size()>0);

        // Delete
        Integer id = user.getId();
        userRepository.delete(user);
        Optional<User> user1 = userRepository.findById(id);
        Assertions.assertFalse(user1.isPresent());
    }

}
