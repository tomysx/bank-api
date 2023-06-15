package com.example.apibank.services;

import com.example.apibank.model.Transfer;
import com.example.apibank.repositories.TransferRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransferService {

    private final TransferRepository transferRepository;

    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public List<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }

    public Transfer getTransferById(Long id) {
        Optional<Transfer> optionalTransfer = transferRepository.findById(id);
        if (optionalTransfer.isPresent()) {
            return optionalTransfer.get();
        } else {
            throw new RuntimeException("Transfer not found for id :: " + id);
        }
    }
}
