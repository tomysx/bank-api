package com.example.apibank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.apibank.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

