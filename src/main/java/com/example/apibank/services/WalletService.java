package com.example.apibank.services;

import com.example.apibank.exceptions.InsufficientFundsException;
import com.example.apibank.model.Transfer;
import com.example.apibank.model.AppUser;
import com.example.apibank.model.Wallet;
import com.example.apibank.repositories.TransferRepository;
import com.example.apibank.repositories.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransferRepository transferRepository;

    public WalletService(WalletRepository walletRepository, TransferRepository transferRepository) {
        this.walletRepository = walletRepository;
        this.transferRepository = transferRepository;
    }

    public Wallet createWallet(AppUser user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(BigDecimal.ZERO);
        return walletRepository.save(wallet);
    }

    public void depositMoney(Wallet wallet, BigDecimal amount) {
        BigDecimal currentBalance = wallet.getBalance();
        BigDecimal newBalance = currentBalance.add(amount);
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);
    }

    public void transferMoney(Long fromWalletId, Long toWalletId, BigDecimal amount) {
        Wallet fromWallet = getWalletById(fromWalletId);
        Wallet toWallet = getWalletById(toWalletId);
        if (fromWallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("No dispone de saldo suficiente para realizar la operación");
        }
        fromWallet.setBalance(fromWallet.getBalance().subtract(amount));
        toWallet.setBalance(toWallet.getBalance().add(amount));
        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

        Transfer transfer = new Transfer();
        transfer.setFromWallet(fromWallet);
        transfer.setToWallet(toWallet);
        transfer.setAmount(amount);
        transferRepository.save(transfer);
    }

    public BigDecimal getBalance(Wallet wallet) {
        return wallet.getBalance();
    }

    public List<Transfer> getMovements(Wallet wallet) {
        Set<Transfer> outgoingTransfers = wallet.getOutgoingTransfers();
        Set<Transfer> incomingTransfers = wallet.getIncomingTransfers();

        List<Transfer> allTransfers = new ArrayList<>();
        allTransfers.addAll(outgoingTransfers);
        allTransfers.addAll(incomingTransfers);

        return allTransfers;
    }

    public Wallet getWalletById(Long id) {
        return walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado ninguna wallet con ID: " + id));
    }
}
