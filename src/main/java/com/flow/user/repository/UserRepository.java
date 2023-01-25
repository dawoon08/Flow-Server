package com.flow.user.repository;

import com.flow.user.domain.AuthProvider;
import com.flow.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndProvider(String email, AuthProvider authProvider);
}
