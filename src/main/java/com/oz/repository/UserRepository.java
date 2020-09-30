package com.oz.repository;

import com.oz.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String username);

    Boolean existsByEmail(String username);

    boolean existsByEmailAndPassword(String email, String password);

}
