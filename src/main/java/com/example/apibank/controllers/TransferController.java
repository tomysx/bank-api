package com.example.apibank.controllers;

import com.example.apibank.model.Transfer;
import com.example.apibank.services.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllTransfers() {
        try {
            List<Transfer> transfers = transferService.getAllTransfers();
            return new ResponseEntity<>(transfers, HttpStatus.OK);
        } catch (Exception e) {
            //Lanzamos excepción en caso de que no se recuperen las transferencias
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al recuperar las transferencias");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTransferById(@PathVariable Long id) {

        //Comprobamos que el ID de la transferencia no es nulo
        if (id == null) {
            return ResponseEntity.badRequest().body("El ID de la transferencia no puede ser nulo");
        }

        //Intentamos recuperar la transferencia con el ID proporcionado
        try {
            Transfer transfer = transferService.getTransferById(id);
            return new ResponseEntity<>(transfer, HttpStatus.OK);
        } catch (RuntimeException e) {
            //Lanzamos excepción en caso de que no sea posible recuperarla con el ID proporcionado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transferencia no encontrada con ID: " + id);
        } catch (Exception e) {
            //Lanzamos excepción genérica si se dan otros errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al recuperar la transferencia");
        }
    }
}
