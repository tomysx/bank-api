package com.example.apibank.repositories;

import com.example.apibank.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.apibank.model.Wallet;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}

