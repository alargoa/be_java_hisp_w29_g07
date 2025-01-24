package com.bootcamp.be_java_hisp_w29_g07.exception;

/**
 * The type Bad request exception.
 */
public class BadRequestException extends RuntimeException {
    /**
     * Instantiates a new Bad request exception.
     *
     * @param message the message
     */
    public BadRequestException(String message) {
        super(message);
    }
}
