package com.softserve.itacademy.service;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class RoleServiceTests {
    private final RoleService roleService;
    private static final Role role1 = new Role();
    private static final Role role2 = new Role();

    @Autowired
    public RoleServiceTests(RoleService roleService) {
        this.roleService = roleService;
    }

    @BeforeAll
    public static void setup() {
        role1.setName("MANAGER");
        role2.setName("CTO");
    }

    @Test
    public void implementsRoleService() {
        Class<?>[] interfaces = RoleServiceImpl.class.getInterfaces();
        assertEquals("RoleService", interfaces[0].getSimpleName());
    }

    @Test
    public void overridesRoleServiceMethods() {
        Class<?>[] interfaces = RoleServiceImpl.class.getInterfaces();
        Method[] classMethods = RoleServiceImpl.class.getMethods();
        Method[] interfaceMethods = interfaces[0].getMethods();
        assertTrue(classMethods.length >= interfaceMethods.length);
    }

    @Test
    @Order(1)
    public void create_shouldAddUnexistingRole() {
        roleService.create(role1);
        assertTrue(role1.getId() != 0);
    }

    @Test
    @Order(2)
    public void create_shouldRetrieveInputIfExists() {
        long expectedId = role1.getId();
        long actualId = roleService.create(role1).getId();
        assertEquals(expectedId, actualId);
    }

    @Test
    @Order(3)
    public void readById_shouldFindById() {
        Role actual = roleService.readById(role1.getId());
        assertEquals(role1.getName(), actual.getName());
    }

    @Test
    @Order(4)
    public void readById_shouldReturnNullIfNoRole() {
        Role newrole = new Role();
        assertNull(roleService.readById(newrole.getId()));
    }

    @Test
    @Order(5)
    public void update_shouldUpdateFieldsForExistingRole() {
        role1.setName("CEO");
        role1.setUsers(Arrays.asList(new User(), new User(), new User()));
        roleService.update(role1);
        Role actual = roleService.readById(role1.getId());
        assertEquals("CEO", actual.getName());
        assertEquals(3, role1.getUsers().size());
    }

    @Test
    @Order(6)
    public void update_shouldSaveRoleIfNotExists() {
        long idBefore = role2.getId();
        roleService.update(role2);
        long idAfter = role2.getId();
        assertTrue(idBefore < idAfter);
    }

    @Test
    @Order(7)
    public void delete_shouldRemoveRecord() {
        roleService.delete(role2.getId());
        assertNull(roleService.readById(role2.getId()));
    }

    @Test
    @Order(8)
    public void getAll_shouldGetListOfAllRoles(){
        assertTrue(roleService.getAll().size() > 0);
        assertNotNull(roleService.getAll());
    }

}
