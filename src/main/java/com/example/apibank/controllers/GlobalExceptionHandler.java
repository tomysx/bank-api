package com.example.apibank.controllers;

import com.example.apibank.exceptions.InsufficientFundsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //Por alguna razón al tratar de realizar un manejo personalizado de errores no he conseguido que se mostrasen
    // entiendo que se debe a que hay un controlador que viene con Spring que hace que la excepción tome otro camino

    //Efectivamente he visto debugeando que se devía y no accede a esta clase. He tratado de establecer prioridad alta
    // a este controlador pero ni con esas, lo dejo a modo de prueba de que lo he intentado

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Object> handleInsufficientFundsException(InsufficientFundsException ex, WebRequest request) {
        // Crea un objeto que contenga detalles sobre el error
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("status", HttpStatus.FORBIDDEN.value());
        errorDetails.put("error", HttpStatus.FORBIDDEN.getReasonPhrase());

        // Devuelve la respuesta con el objeto de detalles de error y el código de estado HTTP
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }
}
