package com.bootcamp.be_java_hisp_w29_g07.dto.request;

import com.bootcamp.be_java_hisp_w29_g07.constants.ValidationValues;
import com.bootcamp.be_java_hisp_w29_g07.validation.ValidID;
import com.bootcamp.be_java_hisp_w29_g07.validation.ValidProductStringField;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
    /**
     * The unique identifier for the product.
     */
    @JsonProperty("product_id")
    @ValidID
    private Integer id;
    /**
     * The name of the product.
     */
    @JsonProperty("product_name")

    @Max(ValidationValues.MAX_PRODUCT_NAME_LENGTH)
    @ValidProductStringField
    private String name;
    /**
     * The type of the product.
     */


    @Max(ValidationValues.MAX_PRODUCT_TYPE_LENGTH)
    @ValidProductStringField
    private String type;
    /**
     * The brand of the product.
     */


    @ValidProductStringField
    @Max(ValidationValues.MAX_PRODUCT_BRAND_LENGTH)
    private String brand;
    /**
     * The color of the product.
     */

    @NotNull
    @NotBlank
    @Max(ValidationValues.MAX_PRODUCT_COLOR_LENGTH)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Solo se permiten letras y números, sin espacios ni caracteres especiales.")
    private String color;
    /**
     * Additional notes or description for the product.
     */

    @NotNull
    @NotBlank
    @Max(ValidationValues.MAX_PRODUCT_NOTES_LENGTH)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Solo se permiten letras y números, sin espacios ni caracteres especiales.")
    private String notes;
}
