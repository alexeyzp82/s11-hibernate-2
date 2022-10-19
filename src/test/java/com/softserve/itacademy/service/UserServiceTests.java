package com.softserve.itacademy.service;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.RoleRepository;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class UserServiceTests {

    private final UserServiceImpl userService;
    private final RoleRepository roleRepository;
    private static final User user1 = new User();
    private static final User user2 = new User();

    @Autowired
    public UserServiceTests(UserServiceImpl userService, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @BeforeAll
    public static void setup() {
        user1.setFirstName("Steve");
        user1.setLastName("Aokigi");
        user1.setEmail("steve@mail.com");
        user1.setPassword("$2a$10$CJgEoobU2gm0euD4ygru4ukBf9g8fYnPrMvYk.q0GMfOcIDtUhEwC");

        user2.setFirstName("Barbara");
        user2.setLastName("Smith");
        user2.setEmail("barb@mail.com");
        user2.setPassword("$CJgEoobU2gm0euD4ygru4ukBf9g8fYnPrMvYk.q0GMfO");
    }

    @Test
    public void injectedComponentNotNull() {
        assertThat(userService).isNotNull();
        assertThat(roleRepository).isNotNull();

    }

    @Test
    public void implementsUserService(){
        Class<?>[] interfaces = UserServiceImpl.class.getInterfaces();
        assertEquals("UserService", interfaces[0].getSimpleName());
    }

    @Test
    public void overridesMethods(){
        Class<?>[] interfaces = UserServiceImpl.class.getInterfaces();
        Method[] expectedMethods = interfaces[0].getMethods();
        Method[] actualMethods = UserServiceImpl.class.getMethods();
        assertTrue(actualMethods.length>=expectedMethods.length);
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
    public void create_shouldAddUser() {
        user1.setRole(roleRepository.findById(1L).orElse(null));
        User actual = userService.create(user1);
        assertEquals(actual.getFirstName(), user1.getFirstName());
        assertEquals(actual.getLastName(), user1.getLastName());
        assertEquals(actual.getEmail(), user1.getEmail());
    }

    @Test
    @Order(3)
    public void create_shouldReturnUserIfExists() {
        User actual = userService.create(user1);
        assertEquals(user1, actual);
    }

    @Test
    @Order(4)
    public void readById_shouldFindUser() {
        User expectedUser = new User();
        expectedUser.setFirstName("Nick");
        expectedUser.setLastName("Green");
        expectedUser.setEmail("nick@mail.com");

        User actualUser = userService.readById(5);

        assertEquals(actualUser.getFirstName(), expectedUser.getFirstName());
        assertEquals(actualUser.getLastName(), expectedUser.getLastName());
        assertEquals(actualUser.getEmail(), expectedUser.getEmail());
    }

    @Test
    public void readById_shouldReturnNullForUnexistingUser() {
        assertNull(userService.readById(99999));
    }

    @Test
    @Order(5)
    public void update_shouldUpdateExistingUser() {
        user1.setFirstName("James");
        user1.setLastName("Bond");
        user1.setEmail("new@mail.com");
        user1.setPassword("$2a$10$CJgEoobU2gm0euD4ygru4ukBf9g8fYnPrMvYk.q0GMfOcIDtUhEw1");
        user1.setRole(roleRepository.findById(2L).orElse(null));

        userService.update(user1);

        User actual = userService.readById(user1.getId());
        assertEquals("James", actual.getFirstName());
        assertEquals("Bond", actual.getLastName());
        assertEquals( "new@mail.com", actual.getEmail());
        assertEquals("$2a$10$CJgEoobU2gm0euD4ygru4ukBf9g8fYnPrMvYk.q0GMfOcIDtUhEw1", actual.getPassword());
    }

    @Test
    @Order(6)
    public void update_shouldAddIfNoSuchUser() {
        user2.setRole(roleRepository.findById(2L).orElse(null));

        userService.update(user2);

        User actual = userService.readById(user2.getId());

        assertEquals("Barbara", actual.getFirstName());
        assertEquals("Smith", actual.getLastName());
        assertEquals("barb@mail.com", actual.getEmail());
    }

    @Test
    @Order(7)
    public void delete_shouldRemoveRecord() {
        userService.delete(user2.getId());
        assertNull(userService.readById(user2.getId()));
    }

    @Test
    @Order(8)
    public void getByEmail_shouldFindUserByExistingEmail() {
        User actual = userService.getByEmail("new@mail.com");
        assertEquals(user1.getEmail(), actual.getEmail());
        assertEquals(user1.getFirstName(), actual.getFirstName());
        assertEquals(user1.getLastName(), actual.getLastName());
    }

}