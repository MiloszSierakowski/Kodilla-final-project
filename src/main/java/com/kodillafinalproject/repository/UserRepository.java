package com.kodillafinalproject.repository;

import com.kodillafinalproject.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByFirstNameLikeAndLastNameLike(String firstname, String lastname);

    List<User> findByFirstNameLike(String firstName);

    List<User> findByLastNameLike(String lastName);
}
