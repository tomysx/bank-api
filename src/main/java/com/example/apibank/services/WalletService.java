package com.example.apibank.services;

import com.example.apibank.model.Transfer;
import com.example.apibank.model.User;
import com.example.apibank.model.Wallet;
import com.example.apibank.repositories.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(User user) {
        // Implementación de la creación de una wallet
        return null;
    }

    public void depositMoney(Wallet wallet, BigDecimal amount) {
        // Implementación del depósito de dinero en una wallet
    }

    public void transferMoney(Wallet fromWallet, Wallet toWallet, BigDecimal amount) {
        // Implementación de la transferencia de dinero de una wallet a otra
    }

    public BigDecimal getBalance(Wallet wallet) {
        // Implementación de la obtención del balance de una wallet
        return null;
    }

    public List<Transfer> getMovements(Wallet wallet) {
        // Implementación de la obtención de los movimientos de una wallet
        return null;
    }
}
