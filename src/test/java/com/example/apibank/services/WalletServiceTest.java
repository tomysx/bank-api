package com.example.apibank.services;

import com.example.apibank.model.AppUser;
import com.example.apibank.model.Transfer;
import com.example.apibank.model.Wallet;
import com.example.apibank.repositories.TransferRepository;
import com.example.apibank.repositories.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransferRepository transferRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateWallet() {
        AppUser user = new AppUser();
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(BigDecimal.ZERO);

        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

        Wallet createdWallet = walletService.createWallet(user);

        assertNotNull(createdWallet);
        assertEquals(BigDecimal.ZERO, createdWallet.getBalance());
    }

    @Test
    public void testDepositMoney() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(100));

        walletService.depositMoney(wallet, BigDecimal.valueOf(50));

        assertEquals(BigDecimal.valueOf(150), wallet.getBalance());
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    public void testTransferMoney() {
        Wallet fromWallet = new Wallet();
        fromWallet.setId(1L);
        fromWallet.setBalance(BigDecimal.valueOf(100));

        Wallet toWallet = new Wallet();
        toWallet.setId(2L);
        toWallet.setBalance(BigDecimal.valueOf(50));

        when(walletRepository.findById(1L)).thenReturn(Optional.of(fromWallet));
        when(walletRepository.findById(2L)).thenReturn(Optional.of(toWallet));

        walletService.transferMoney(1L, 2L, BigDecimal.valueOf(50));

        assertEquals(BigDecimal.valueOf(50), fromWallet.getBalance());
        assertEquals(BigDecimal.valueOf(100), toWallet.getBalance());
        verify(walletRepository, times(2)).save(any(Wallet.class));
        verify(transferRepository, times(1)).save(any(Transfer.class));
    }

    @Test
    public void testGetWalletById() {
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(BigDecimal.valueOf(100));

        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));

        Wallet retrievedWallet = walletService.getWalletById(1L);

        assertNotNull(retrievedWallet);
        assertEquals(1L, retrievedWallet.getId());
        assertEquals(BigDecimal.valueOf(100), retrievedWallet.getBalance());
    }

    @Test
    public void testGetWalletByIdNotFound() {
        when(walletRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> walletService.getWalletById(1L));
    }

}
