package com.kodillafinalproject.service;

import com.kodillafinalproject.controller.NoRequiredPersonDataException;
import com.kodillafinalproject.controller.UserNotFoundException;
import com.kodillafinalproject.domain.User;
import com.kodillafinalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findByName(User user) throws NoRequiredPersonDataException, UserNotFoundException {
        //todo optymalizacja zobaczyć czy da się to lepiej zrobić
        if (user.getFirstName() != null || user.getLastName() != null) {

            if (user.getFirstName() != null && !user.getFirstName().isEmpty() && user.getLastName() != null && !user.getLastName().isEmpty()) {
                return findByFirstNameLikeAndLastNameLike(user);

            } else if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
                return findByFirstNameLike(user);

            } else if (user.getLastName() != null && !user.getLastName().isEmpty()) {
                return findByLastNameLike(user);

            } else {
                throw new NoRequiredPersonDataException();
            }
        } else {
            throw new NoRequiredPersonDataException();
        }
    }

    private List<User> findByFirstNameLikeAndLastNameLike(User user) throws UserNotFoundException {
        List<User> userList = userRepository.findByFirstNameLikeAndLastNameLike(user.getFirstName(), user.getLastName());
        if (userList.size() > 0) {
            return userList;
        } else {
            throw new UserNotFoundException();
        }
    }

    private List<User> findByFirstNameLike(User user) throws UserNotFoundException {
        List<User> userList = userRepository.findByFirstNameLike(user.getFirstName());
        if (userList.size() > 0) {
            return userList;
        } else {
            throw new UserNotFoundException();
        }
    }

    private List<User> findByLastNameLike(User user) throws UserNotFoundException {
        List<User> userList = userRepository.findByLastNameLike(user.getLastName());
        if (userList.size() > 0) {
            return userList;
        } else {
            throw new UserNotFoundException();
        }
    }

    public User findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
