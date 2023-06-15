package com.example.apibank.services;

import com.example.apibank.repositories.TransferRepository;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    private final TransferRepository transferRepository;

    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

}
