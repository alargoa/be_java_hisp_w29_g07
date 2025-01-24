package com.bootcamp.be_java_hisp_w29_g07.exception;

/**
 * The type Not found exception.
 */
public class NotFoundException extends RuntimeException {
    /**
     * Instantiates a new Not found exception.
     *
     * @param message the message
     */
    public NotFoundException(String message) {
        super(message);
    }
}
