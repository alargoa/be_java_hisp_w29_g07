package com.bootcamp.be_java_hisp_w29_g07.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidationExceptionDTO {
    private LocalDate timestamp;
    private String message;

    public ValidationExceptionDTO(String message) {
        this.message = message;
        this.timestamp = LocalDate.now();
    }
}
