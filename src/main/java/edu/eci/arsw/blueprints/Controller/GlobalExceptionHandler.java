package edu.eci.arsw.blueprints.Controller;

import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de excepciones para blueprint no encontrado (404)
    @ExceptionHandler(BlueprintNotFoundException.class)
    public ResponseEntity<String> handleBlueprintNotFound(BlueprintNotFoundException ex) {
        return new ResponseEntity<>("Blueprint no encontrado: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Manejo de excepciones para errores de persistencia (409)
    @ExceptionHandler(BlueprintPersistenceException.class)
    public ResponseEntity<String> handleBlueprintPersistence(BlueprintPersistenceException ex) {
        return new ResponseEntity<>("Error de persistencia: " + ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Manejo de cualquier otro error no específico (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralError(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
