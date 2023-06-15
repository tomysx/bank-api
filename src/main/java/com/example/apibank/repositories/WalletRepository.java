package com.example.apibank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.apibank.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}

