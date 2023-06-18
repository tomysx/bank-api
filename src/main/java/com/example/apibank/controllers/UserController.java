package com.example.apibank.controllers;

import com.example.apibank.model.AppUser;
import com.example.apibank.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {

        //Validamos que el ID del usuario no sea nulo
        if (id == null) {
            return ResponseEntity.badRequest().body("El ID del usuario no puede ser nulo");
        }

        //Intentamos recuperar el usuario
        try {
            AppUser user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            //Lanzamos excepción si no encontramos al usuario con el ID proporcionado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con ID: " + id);
        } catch (Exception e) {
            //Lanzamos excepción en caso de que no se recupere el usuario solicitado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al recuperar el usuario");
        }
    }

    @PostMapping
    public ResponseEntity<Object> registerUser(@RequestBody AppUser user) {

        //Validamos que el ID del usuario no sea nulo
        if (user == null) {
            return ResponseEntity.badRequest().body("El usuario no puede ser nulo");
        }

        //Intentamos crear el usuario
        try {
            AppUser createdUser = userService.registerUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            //Lanzamos excepción en caso de que no se cree el nuevo usuario
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario");
        }
    }
}