package com.dvb.forum.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessRegistrationRequest extends UserRegistrationRequest {

    @NotBlank(message = "Business name cannot be blank.")
    @Size(message = "Business name must be at least 2 characters and no more than 48 characters.", min = 2, max = 48)
    private String businessName;
    @NotBlank(message = "Contact first name cannot be blank.")
    @Size(message = "Contact first name must be at least 2 characters and no more than 48 characters.", min = 2, max = 48)
    @Pattern(message = "Contact first name can only have alphanumeric characters, space, comma, period, single apostrophe, and hyphen.",
            regexp = "^[A-Za-z ,.'-]+$")
    private String contactFirstName;
    @NotBlank(message = "Contact last name cannot be blank.")
    @Size(message = "Contact last name must be at least 2 characters and no more than 48 characters.", min = 2, max = 48)
    @Pattern(message = "Contact last name can only have alphanumeric characters, space, comma, period, single apostrophe, and hyphen.",
            regexp = "^[A-Za-z ,.'-]+$")
    private String contactLastName;

}