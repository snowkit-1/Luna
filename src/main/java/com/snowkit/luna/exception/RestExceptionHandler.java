package com.snowkit.luna.exception;

import com.snowkit.luna.model.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<?> noContent(Exception e) {
        log.info(e.getMessage(), e);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ResponseMessage> badRequest(Exception e) {
        log.info(e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ResponseMessage(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> internalServerError(Exception e) {
        log.info(e.getMessage(), e);

        String errorMessage = e.getMessage();
        if (errorMessage == null) {
            errorMessage = "Internal Server Error";
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ResponseMessage(errorMessage));
    }
}
