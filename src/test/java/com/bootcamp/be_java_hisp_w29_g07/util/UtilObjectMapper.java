package com.bootcamp.be_java_hisp_w29_g07.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;

/**
 * Utility class for creating and providing a singleton instance of {@link ObjectMapper}.
 */
public class UtilObjectMapper {

    /**
     * Singleton instance of {@link ObjectMapper} configured with necessary modules.
     * This instance is created and configured once and can be accessed globally.
     */
    @Getter
    private static final ObjectMapper objectMapper = createObjectMapper();

    /**
     * Creates and configures an instance of {@link ObjectMapper}.
     * This method registers the {@link JavaTimeModule} and configures the mapper
     * to not write dates as timestamps.
     *
     * @return a configured {@link ObjectMapper} instance.
     */
    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }
}