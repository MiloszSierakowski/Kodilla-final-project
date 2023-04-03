package com.kodillafinalproject.service;

import com.kodillafinalproject.domain.User;
import com.kodillafinalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findByName(User user) throws Exception {
        //todo optymalizacja zobaczyć czy da się to lepiej zrobić
        if (user.getFirstname() != null || user.getLastname() != null) {
            if (user.getFirstname() != null && !user.getFirstname().isEmpty() && user.getLastname() != null && !user.getLastname().isEmpty()) {
                return userRepository.findByFirstnameLikeAndLastnameLike(user.getFirstname(), user.getLastname());
            } else if (user.getFirstname() != null && !user.getFirstname().isEmpty()) {
                return userRepository.findByFirstnameLike(user.getFirstname()); // todo dodać gdy lista jest pusta ? i albo lista albo throw exeption userNotFound
            } else if (user.getLastname() != null && !user.getLastname().isEmpty()) {
                return userRepository.findByLastnameLike(user.getLastname());
            } else {
                System.out.println(user.getLastname());
                throw new Exception("wpisano puste znaki przy szukaniu urzytkownika");
                //todo tutaj ma rzucic wyjatkiem ze urzytkownik chce szukac ale nie podał imienia i nazwiska
            }
        } else {
            throw new Exception("Mamy dwa nulle przy szukaniu urzytkownika");
            //todo dorobić wyjątek że nie został podany ani firstname ani lastname
        }

    }

}
