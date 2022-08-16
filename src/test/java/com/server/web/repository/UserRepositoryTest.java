package com.server.web.repository;

import com.server.web.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    UserRepository subj;

    @Test
    public void findByEmail_ok() {
        User user = subj.findByEmail("russkov@gmail.com");
        assertNotNull(user);
        assertEquals("russkov@gmail.com", user.getEmail());
        assertEquals("$2a$10$WwwIy.q4PlPkdhMyMDq88O5M/Mo7/kzADBjvakPolUppWzFnPQRhO", user.getPassword());
        assertEquals(1L, user.getId());
    }

    @Test
    public void findByEmail_notFound() {
        assertNull(subj.findByEmail("russkov@gmail.co"));
    }
}