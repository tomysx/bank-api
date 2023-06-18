package com.example.apibank.repositories;

import com.example.apibank.model.Transfer;
import com.example.apibank.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findAllByWallet(Wallet wallet);
}
