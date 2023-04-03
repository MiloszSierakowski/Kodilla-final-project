package com.kodillafinalproject.repository;

import com.kodillafinalproject.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByFirstnameLike(String firstname);

    List<User> findByFirstnameLikeAndLastnameLike(String firstname, String lastname);

    List<User> findByLastnameLike(String lastname);
}
