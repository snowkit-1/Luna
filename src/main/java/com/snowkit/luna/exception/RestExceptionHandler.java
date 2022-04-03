package com.snowkit.luna.exception;

import com.snowkit.luna.model.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<?> noContent(Exception e) {
        log.info(e.getMessage());

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ResponseMessage> badRequest(Exception e) {
        log.info(e.getMessage());

        return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> notValidException(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError ->
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseMessage> forbidden(Exception e) {
        log.info(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ResponseMessage(e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseMessage> dataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.internalServerError().body(new ResponseMessage("SQL Error"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> internalServerError(Exception e) {
        log.info(e.getMessage());

        String errorMessage = e.getMessage();
        if (errorMessage == null) {
            errorMessage = "Internal Server Error";
        }

        return ResponseEntity.internalServerError().body(new ResponseMessage(errorMessage));
    }
}
