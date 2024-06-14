package com.dvb.forum.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AdminRegistrationRequest extends UserRegistrationRequest {

    @NotBlank(message = "First name cannot be blank.")
    @Size(message = "First name must be at least 2 characters and no more than 48 characters.", min = 2, max = 48)
    @Pattern(message = "First name can only have alphanumeric characters, space, comma, period, single apostrophe, and hyphen.",
            regexp = "^[A-Za-z ,.'-]+$")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank.")
    @Size(message = "Last name must be at least 2 characters and no more than 48 characters.", min = 2, max = 48)
    @Pattern(message = "Last name can only have alphanumeric characters, space, comma, period, single apostrophe, and hyphen.",
            regexp = "^[A-Za-z ,.'-]+$")
    private String lastName;

}