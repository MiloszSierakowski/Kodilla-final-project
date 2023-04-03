package com.kodillafinalproject.service;

import com.kodillafinalproject.domain.User;
import com.kodillafinalproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void findByFirstNameTest() {
        User user = new User(1L, "Milosz", "", "Olsztyn",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User user1 = new User(2L, "Milosz", "Sierakowski", "Olsztyn",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User savedUser1 = userRepository.save(user);
        User savedUser2 = userRepository.save(user1);
        System.out.println(savedUser1.getId());
        System.out.println(savedUser2.getId());
        List<User> userList;
        try {
            userList = userService.findByName(user);

            assertEquals(2, userList.size());
            assertEquals(user.getLastname(), userList.get(0).getLastname());
            assertEquals(user1.getLastname(), userList.get(1).getLastname());

        } catch (Exception e) {
            System.out.println("wyskoczyl exception");//todo poprawić to tutaj
        } finally {
            userRepository.deleteById(savedUser1.getId());
            userRepository.deleteById(savedUser2.getId());
        }
    }

    @Test
    void findByLaseNameTest() {
        User user = new User(1L, "", "NVIDIA", "Olsztyn",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User user1 = new User(2L, "Milosz", "NVIDIA", "Olsztyn",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User savedUser1 = userRepository.save(user);
        User savedUser2 = userRepository.save(user1);

        List<User> userList;
        try {
            userList = userService.findByName(user);

            assertEquals(2, userList.size());
            assertEquals(user.getFirstname(), userList.get(0).getFirstname());
            assertEquals(user1.getFirstname(), userList.get(1).getFirstname());

        } catch (Exception e) {
            System.out.println("wyskoczyl exception");//todo poprawić to tutaj

        } finally {
            userRepository.deleteById(savedUser1.getId());
            userRepository.deleteById(savedUser2.getId());
        }
    }

    @Test
    void findByFirstNameAndLaseNameTest() {
        User user = new User(1L, "Alicja", "NVIDIA", "Olsztyn",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User user1 = new User(2L, "Milosz", "NVIDIA", "Olsztyn",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        User savedUser1 = userRepository.save(user);
        User savedUser2 = userRepository.save(user1);

        List<User> userList;
        try {
            userList = userService.findByName(user1);

            assertEquals(1, userList.size());
            assertEquals(user1.getFirstname(), userList.get(0).getFirstname());
            assertEquals(user1.getLastname(), userList.get(0).getLastname());

        } catch (Exception e) {
            System.out.println("wyskoczyl exception");//todo poprawić to tutaj

        } finally {
            userRepository.deleteById(savedUser1.getId());
            userRepository.deleteById(savedUser2.getId());
        }
    }

    //todo dopisać testy to exceptionów jak już będę miał je gotowe
}