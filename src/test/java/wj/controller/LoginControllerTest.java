package com.gm.wj.controller;

import com.gm.wj.dao.UserDAO;
import com.gm.wj.entity.User;
import com.gm.wj.result.Result;
import com.gm.wj.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserService userService;

    @Test
    public void testLoginSuccess() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("123");

        HttpEntity<User> request = new HttpEntity<>(user);
        ResponseEntity<Result> response = restTemplate
                .exchange("/api/login", HttpMethod.POST, request, Result.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("成功", response.getBody().getMessage());
    }

    @Test
    public void testLoginFailure() {
        User user = new User();
        user.setUsername("wrongUsername");
        user.setPassword("wrongPassword");

        HttpEntity<User> request = new HttpEntity<>(user);
        ResponseEntity<Result> response = restTemplate
                .exchange("/api/login", HttpMethod.POST, request, Result.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("账号不存在", response.getBody().getMessage());
    }

    @Test
    public void testLoginNonExistentUser() {
        User user = new User();
        user.setUsername("nonExistentUser");
        user.setPassword("123");

        HttpEntity<User> request = new HttpEntity<>(user);
        ResponseEntity<Result> response = restTemplate
                .exchange("/api/login", HttpMethod.POST, request, Result.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("账号不存在", response.getBody().getMessage());
    }

    @Test
    public void testLoginDisabledUser() {
        User user = new User();
        user.setUsername("11");
        user.setPassword("123");

        HttpEntity<User> request = new HttpEntity<>(user);
        ResponseEntity<Result> response = restTemplate
                .exchange("/api/login", HttpMethod.POST, request, Result.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("该用户已被禁用", response.getBody().getMessage());
    }


    @Test
    public void testRegisterSuccess() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        HttpEntity<User> request = new HttpEntity<>(user);
        ResponseEntity<Result> response = restTemplate
                .exchange("/api/register", HttpMethod.POST, request, Result.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("注册成功", response.getBody().getMessage());
    }

    @Test
    public void testRegisterFailureEmptyUsernameOrPassword() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");

        HttpEntity<User> request = new HttpEntity<>(user);
        ResponseEntity<Result> response = restTemplate
                .exchange("/api/register", HttpMethod.POST, request, Result.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("用户名和密码不能为空", response.getBody().getMessage());
    }

    @Test
    public void testRegisterFailureUserExists() {
        User user = new User();
        user.setUsername("11");
        user.setPassword("testPassword");

        HttpEntity<User> request = new HttpEntity<>(user);
        ResponseEntity<Result> response = restTemplate
                .exchange("/api/register", HttpMethod.POST, request, Result.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("用户已存在", response.getBody().getMessage());
    }
}