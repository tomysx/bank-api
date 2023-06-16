package com.example.apibank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.apibank.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {
}

