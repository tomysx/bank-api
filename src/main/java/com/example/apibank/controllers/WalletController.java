package com.example.apibank.controllers;

import com.example.apibank.model.Transfer;
import com.example.apibank.model.User;
import com.example.apibank.model.Wallet;
import com.example.apibank.services.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody User user) {
        Wallet wallet = walletService.createWallet(user);
        return new ResponseEntity<>(wallet, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<Void> depositMoney(@PathVariable Long id, @RequestBody BigDecimal amount) {
        Wallet wallet = walletService.getWalletById(id);
        walletService.depositMoney(wallet, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/transfer/{toWalletId}")
    public ResponseEntity<Void> transferMoney(@PathVariable Long id, @PathVariable Long toWalletId, @RequestBody BigDecimal amount) {
        Wallet fromWallet = walletService.getWalletById(id);
        Wallet toWallet = walletService.getWalletById(toWalletId);
        walletService.transferMoney(fromWallet, toWallet, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long id) {
        Wallet wallet = walletService.getWalletById(id);
        BigDecimal balance = walletService.getBalance(wallet);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    @GetMapping("/{id}/movements")
    public ResponseEntity<List<Transfer>> getMovements(@PathVariable Long id) {
        Wallet wallet = walletService.getWalletById(id);
        List<Transfer> movements = walletService.getMovements(wallet);
        return new ResponseEntity<>(movements, HttpStatus.OK);
    }
}