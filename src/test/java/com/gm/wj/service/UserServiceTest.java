package com.gm.wj.service;

import com.gm.wj.dao.UserDAO;
import com.gm.wj.entity.User;
import com.gm.wj.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserDAO userDAO;

    @Test
    public void testRegister() {
        User user = new User();
        user.setUsername("testUser");
        user.setName("111");
        user.setEmail("11");
        user.setPhone("1111");
        user.setPassword("testPassword");

        when(userDAO.findByUsername(user.getUsername())).thenReturn(null);
        when(userDAO.save(Mockito.any(User.class))).thenReturn(user);

        int result = userService.register(user);

        assertEquals(1, result);
    }
    @Test
    public void testResetPassword() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        User userInDB = new User();
        userInDB.setUsername(user.getUsername());
        userInDB.setPassword("oldPassword");

        when(userDAO.findByUsername(user.getUsername())).thenReturn(userInDB);
        when(userDAO.save(Mockito.any(User.class))).thenReturn(user);

        User result = userService.resetPassword(user);

        assertEquals(user.getUsername(), result.getUsername());
        assertNotEquals("oldPassword", result.getPassword());
    }
}