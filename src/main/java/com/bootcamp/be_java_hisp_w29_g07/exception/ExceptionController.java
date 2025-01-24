package com.bootcamp.be_java_hisp_w29_g07.exception;

import com.bootcamp.be_java_hisp_w29_g07.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type Exception controller.
 */
@ControllerAdvice
public class ExceptionController {

    /**
     * Handle not found exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleNotFoundException(NotFoundException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle conflic exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(ConflictException.class)
    public  ResponseEntity<ExceptionDTO> handleConflicException(ConflictException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage());
        return  new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }

    /**
     * Handle bad request exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDTO> handleBadRequestException(BadRequestException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
}
