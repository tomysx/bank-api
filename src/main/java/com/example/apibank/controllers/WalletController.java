package com.example.apibank.controllers;

import com.example.apibank.model.Transfer;
import com.example.apibank.model.AppUser;
import com.example.apibank.model.Wallet;
import com.example.apibank.services.WalletService;
import jakarta.persistence.EntityNotFoundException;
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
    public ResponseEntity<Object> createWallet(@RequestBody AppUser user) {

        // Validamos que el objeto user no sea nulo
        if (user == null) {
            return ResponseEntity.badRequest().body("El usuario no puede ser nulo");
        }

        // Validamos que el usuario tenga la información necesaria (nombre)
        if (user.getName() == null || user.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre de usuario es obligatorio");
        }

        // Validamos que el usuario tenga la información necesaria (email)
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("El email es obligatorio");
        }

        // Intentamos crear la wallet
        try {
            Wallet wallet = walletService.createWallet(user);
            return new ResponseEntity<>(wallet, HttpStatus.CREATED);
        } catch (Exception e) {
            // Lanzamos una excepción en caso de que no se cree la wallet
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la wallet");
        }
    }


    @PutMapping("/{id}/deposit")
    public ResponseEntity<String> depositMoney(@PathVariable Long id, @RequestBody BigDecimal amount) {

        // Validamos que el ID no sea nulo
        if (id == null) {
            return ResponseEntity.badRequest().body("El ID no puede ser nulo");
        }

        // Validamos que el importe no sea nulo y sea mayor que 0
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("El importe debe ser mayor que 0");
        }

        // Intentamos realizar el depósito
        try {
            Wallet wallet = walletService.getWalletById(id);
            walletService.depositMoney(wallet, amount);
            return ResponseEntity.ok("Depósito realizado con éxito");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet no encontrada");
        } catch (Exception e) {
            // Lanzamos la excepción en caso de que el depósito no se realice
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al realizar el depósito");
        }
    }

    @PutMapping("/{id}/transfer/{toWalletId}")
    public ResponseEntity<String> transferMoney(@PathVariable Long id, @PathVariable Long toWalletId, @RequestBody BigDecimal amount) {

        // Validamos que los IDs no sean nulos
        if (id == null || toWalletId == null) {
            return ResponseEntity.badRequest().body("Los IDs de las wallets no pueden ser nulos");
        }

        // Validamos que el importe no sea nulo y sea mayor que 0
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("El importe debe ser mayor que 0");
        }

        // Intentamos realizar la transferencia
        try {
            walletService.transferMoney(id, toWalletId, amount);
            return ResponseEntity.ok("Transferencia realizada con éxito");
        } catch (Exception e) {
            // Lanzamos una excepción en caso de que la transferencia falle
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al realizar la transferencia");
        }
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Object> getBalance(@PathVariable Long id) {

        // Validamos que el ID no sea nulo
        if (id == null) {
            return ResponseEntity.badRequest().body("El ID de la wallet no puede ser nulo");
        }

        //Intentamos recuperar la wallet
        try {
            Wallet wallet = walletService.getWalletById(id);
            BigDecimal balance = walletService.getBalance(wallet);
            return new ResponseEntity<>(balance, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            // Lanzamos excepción en que la wallet no se encuentre
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet no encontrada con ID: " + id);
        }
    }

    @GetMapping("/{id}/movements")
    public ResponseEntity<Object> getMovements(@PathVariable Long id) {

        // Validamos que el ID no sea nulo
        if (id == null) {
            return ResponseEntity.badRequest().body("El ID de la wallet no puede ser nulo");
        }

        try {
            // Intentamos recuperar la wallet
            Wallet wallet = walletService.getWalletById(id);

            // Intentamos recuperar los movimientos de la wallet
            List<Transfer> movements = walletService.getMovements(wallet);
            return new ResponseEntity<>(movements, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            // Manejamos el caso en que la wallet no se encuentre
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet no encontrada con ID: " + id);
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}