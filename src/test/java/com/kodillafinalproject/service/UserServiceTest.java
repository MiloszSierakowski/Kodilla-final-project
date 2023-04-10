package com.kodillafinalproject.service;

import com.kodillafinalproject.controller.NoRequiredPersonDataException;
import com.kodillafinalproject.controller.UserNotFoundException;
import com.kodillafinalproject.domain.User;
import com.kodillafinalproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void testFindByNameUsingOnlyFirstName() {
        User user = new User(0L, "Darek", "", "Olsztyn",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User user1 = new User(0L, "Darek", "Sierakowski", "Olsztyn",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User savedUser1 = userRepository.save(user);
        User savedUser2 = userRepository.save(user1);

        List<User> userList;
        try {
            userList = userService.findByName(user);

            assertEquals(2, userList.size());
            assertEquals(user.getLastName(), userList.get(0).getLastName());
            assertEquals(user1.getLastName(), userList.get(1).getLastName());

        } catch (Exception e) {
            System.out.println("wyskoczyl exception");//todo poprawić to tutaj
        } finally {
            userRepository.deleteById(savedUser1.getId());
            userRepository.deleteById(savedUser2.getId());
        }
    }

    @Test
    void testFindByNameUsingOnlyLastName() {
        User user = new User(0L, "", "NVIDIA", "Olsztyn",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User user1 = new User(0L, "Milosz", "NVIDIA", "Olsztyn",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User savedUser1 = userRepository.save(user);
        User savedUser2 = userRepository.save(user1);

        List<User> userList;
        try {
            userList = userService.findByName(user);

            assertEquals(2, userList.size());
            assertEquals(user.getFirstName(), userList.get(0).getFirstName());
            assertEquals(user1.getFirstName(), userList.get(1).getFirstName());

        } catch (Exception e) {
            System.out.println("wyskoczyl exception");//todo poprawić to tutaj

        } finally {
            userRepository.deleteById(savedUser1.getId());
            userRepository.deleteById(savedUser2.getId());
        }
    }

    @Test
    void testFindByNameByFirstNameAndLaseName() {
        User user = new User(0L, "Alicja", "NVIDIA", "Olsztyn",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User user1 = new User(0L, "Milosz", "NVIDIA", "Olsztyn",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User savedUser1 = userRepository.save(user);
        User savedUser2 = userRepository.save(user1);

        List<User> userList;
        try {
            userList = userService.findByName(user1);

            assertEquals(1, userList.size());
            assertEquals(user1.getFirstName(), userList.get(0).getFirstName());
            assertEquals(user1.getLastName(), userList.get(0).getLastName());

        } catch (Exception e) {
            System.out.println("wyskoczyl exception");//todo poprawić to tutaj

        } finally {
            userRepository.deleteById(savedUser1.getId());
            userRepository.deleteById(savedUser2.getId());
        }
    }

    @Test
    void testFindByNameTestedByNull() {
        User user = new User(0L, "Alicja", "NVIDIA", "Olsztyn",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User user1 = new User(0L, null, null, "Olsztyn",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User savedUser1 = userRepository.save(user);
        User savedUser2 = userRepository.save(user1);

        try {
            userService.findByName(user1);

            assertThrows(NoRequiredPersonDataException.class, () -> userService.findByName(user1));

        } catch (Exception e) {
            System.out.println("wyskoczyl exception");//todo poprawić to tutaj

        } finally {
            userRepository.deleteById(savedUser1.getId());
            userRepository.deleteById(savedUser2.getId());
        }
    }

    @Test
    void findById() throws UserNotFoundException {
        User user = new User(0L, "Darek", "Gabrowski", "Płock",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User savedUser1 = userRepository.save(user);

        User searchedUser = userService.findById(savedUser1.getId());

        try {
            assertEquals(user.getFirstName(), searchedUser.getFirstName());
            assertEquals(user.getLastName(), searchedUser.getLastName());
            assertEquals(user.getCity(), searchedUser.getCity());

        } catch (Exception e) {
            System.out.println("wyskoczyl exception");//todo poprawić to tutaj
        } finally {
            userRepository.deleteById(savedUser1.getId());
        }
    }

    @Test
    void findByIdThrowUserNotFoundException() {
        User user = new User(0L, "Darek", "Gabrowski", "Płock",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User savedUser1 = userRepository.save(user);
        userRepository.deleteById(savedUser1.getId());

        try {
            assertThrows(UserNotFoundException.class, () -> userService.findById(savedUser1.getId()));
        } catch (Exception e) {
            System.out.println("wyskoczyl exception");//todo poprawić to tutaj
        } finally {
            userRepository.deleteById(savedUser1.getId());
        }
    }

    @Test
    void saveUser() {
        User user = new User(0L, "Darek", "Gabrowski", "Płock",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User savedUser1 = userService.saveUser(user);
        Optional<User> searchedUser = userRepository.findById(savedUser1.getId());

        try {
            assertTrue(searchedUser.isPresent());
            assertEquals(user.getFirstName(), searchedUser.get().getFirstName());
            assertEquals(user.getLastName(), searchedUser.get().getLastName());
            assertEquals(user.getCity(), searchedUser.get().getCity());

        } catch (Exception e) {
            System.out.println("wyskoczyl exception");//todo poprawić to tutaj
        } finally {
            userRepository.deleteById(savedUser1.getId());
        }
    }

    @Test
    void deleteUserById() {
        User user = new User(0L, "Darek", "Gabrowski", "Płock",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User savedUser1 = userRepository.save(user);
        userService.deleteUserById(savedUser1.getId());
        Optional<User> searchedUser = userRepository.findById(savedUser1.getId());

        try {
            assertFalse(searchedUser.isPresent());
        } catch (Exception e) {
            System.out.println("wyskoczyl exception");//todo poprawić to tutaj
        } finally {
            userRepository.deleteById(savedUser1.getId());
        }
    }
}