package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void userTest() {
        User user = new User();
        user.setFullname("Alexandre Dumas");
        user.setUsername("Alex");
        user.setRole("user");

        //Save
        user = userService.save(user);
        Assert.assertNotNull(user.getId());
        Assert.assertSame("Alex", user.getUsername());

        //Update
        user.setUsername("Al");
        user=userService.save(user);
        Assert.assertSame("Al", user.getUsername());

        //Find
        List<User> userList = userService.findAll();
        Assert.assertTrue(userList.size()>0);

        //delete
        Integer id = user.getId();
        userService.delete(user);
        Optional<User> tradeList = userService.findById(id);
        Assert.assertFalse(tradeList.isPresent());
    }

//    @BeforeEach
//    void cleanDB(){
//       this.userRepository.deleteAll();
//    }


}
