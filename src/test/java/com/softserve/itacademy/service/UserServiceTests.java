package com.softserve.itacademy.service;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.RoleRepository;
import lombok.ToString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class UserServiceTests {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private static User user1 = new User();

    @Autowired
    public UserServiceTests(UserService userService, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @BeforeAll
    public static void setup(){
        Role role = new Role();
        role.setName("USER");
        user1.setId(1);
        user1.setFirstName("Steve");
        user1.setLastName("Aokigi");
        user1.setEmail("steve@mail.com");
        user1.setPassword("$2a$10$CJgEoobU2gm0euD4ygru4ukBf9g8fYnPrMvYk.q0GMfOcIDtUhEwC");
    }

    @Test
    public void injectedComponentNotNull(){
        assertThat(userService).isNotNull();
    }

    @Test
    @Order(1)
    public void getAllUsersTest() {
        int expectedSize = 3;
        List<User> users = userService.getAll();
        assertTrue(expectedSize <= users.size(), String.format("At least %d users shuold be in users table", expectedSize));
    }

    @Test
    @Order(2)
    public void create_shouldAddUser(){
        user1.setRole(roleRepository.findById(1L).orElse(null));
        User actual = userService.create(user1);
        assertEquals(actual.getFirstName(), user1.getFirstName());
        assertEquals(actual.getLastName(), user1.getLastName());
        assertEquals(actual.getEmail(), user1.getEmail());
    }

    @Test
    @Order(3)
    public void readById_shouldFindUser(){
        User expectedUser = new User();
        expectedUser.setFirstName("Nick");
        expectedUser.setLastName("Green");
        expectedUser.setEmail("nick@mail.com");

        User actualUser = userService.readById(5);

        assertEquals(actualUser.getFirstName(), expectedUser.getFirstName());
        assertEquals(actualUser.getLastName(), expectedUser.getLastName());
        assertEquals(actualUser.getEmail(), expectedUser.getEmail());
    }

}