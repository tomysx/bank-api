package com.example.apibank.services;

import com.example.apibank.model.Transfer;
import com.example.apibank.model.Wallet;
import com.example.apibank.repositories.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class TransferServiceTest {

    @InjectMocks
    private TransferService transferService;

    @Mock
    private TransferRepository transferRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTransfers() {
        Transfer transfer1 = new Transfer();
        Transfer transfer2 = new Transfer();
        when(transferRepository.findAll()).thenReturn(Arrays.asList(transfer1, transfer2));

        List<Transfer> result = transferService.getAllTransfers();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetTransferByIdFound() {
        Transfer transfer = new Transfer();
        when(transferRepository.findById(1L)).thenReturn(Optional.of(transfer));

        Transfer result = transferService.getTransferById(1L);

        assertEquals(transfer, result);
    }

    @Test
    public void testGetTransferByIdNotFound() {
        when(transferRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> transferService.getTransferById(1L));
    }

    @Test
    public void testGetTransfersByWallet() {
        Wallet wallet = new Wallet();
        Transfer transfer1 = new Transfer();
        Transfer transfer2 = new Transfer();
        when(transferRepository.findAllByWallet(wallet)).thenReturn(Arrays.asList(transfer1, transfer2));

        List<Transfer> result = transferService.getTransfersByWallet(wallet);

        assertEquals(2, result.size());
    }
}
