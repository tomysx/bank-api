package com.example.apibank.services;

import com.example.apibank.model.Transfer;
import com.example.apibank.model.User;
import com.example.apibank.model.Wallet;
import com.example.apibank.repositories.TransferRepository;
import com.example.apibank.repositories.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransferRepository transferRepository;

    public WalletService(WalletRepository walletRepository, TransferRepository transferRepository) {
        this.walletRepository = walletRepository;
        this.transferRepository = transferRepository;
    }

    public Wallet createWallet(User user) {
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


    public void transferMoney(Wallet fromWallet, Wallet toWallet, BigDecimal amount) {
        if (fromWallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance in the wallet");
        }
        fromWallet.setBalance(fromWallet.getBalance().subtract(amount));
        toWallet.setBalance(toWallet.getBalance().add(amount));
        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

        Transfer transfer = new Transfer();
        transfer.setFromWalletId(fromWallet.getId());
        transfer.setToWalletId(toWallet.getId());
        transfer.setAmount(amount);
        transferRepository.save(transfer);
    }

    public BigDecimal getBalance(Wallet wallet) {
        return wallet.getBalance();
    }

    public List<Transfer> getMovements(Wallet wallet) {
        return transferRepository.findAllByFromWalletOrToWallet(wallet, wallet);
    }

    public Wallet getWalletById(Long id) {
        return walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet not found with id: " + id));
    }
}
