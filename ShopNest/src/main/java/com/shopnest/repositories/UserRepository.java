package com.shopnest.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopnest.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
