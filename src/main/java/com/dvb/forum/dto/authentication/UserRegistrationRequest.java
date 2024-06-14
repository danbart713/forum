package com.dvb.forum.dto.authentication;

import com.dvb.forum.enums.UserRoleEnum;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "userRole",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = IndividualRegistrationRequest.class, name = "Individual"),
        @JsonSubTypes.Type(value = BusinessRegistrationRequest.class, name = "Business"),
        @JsonSubTypes.Type(value = AdminRegistrationRequest.class, name = "Admin")
})
public abstract class UserRegistrationRequest {

    @NotBlank(message = "Email cannot be blank.")
    @Size(message = "Email cannot be greater than 256 characters.", max = 256)
    @Pattern(message = "Email must be in a valid email address format.",
            regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$")
    private String email;
    @NotBlank(message = "Password cannot be blank.")
    @Size(message = "Password must be at least 12 characters and no more than 72 characters.", min = 12, max = 72)
    @Pattern(message = "Password must have at least 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character.",
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).*$")
    private String password;
    @NotBlank(message = "Display name cannot be blank.")
    @Size(message = "Display name must be at least 6 characters and no more than 36 characters.", min = 6, max = 36)
    @Pattern(message = "Display name can only have alphanumeric characters, underscore, period and hyphen.",
            regexp = "^[A-Za-z0-9_.-]*$")
    private String displayName;
    @NotNull(message = "User role cannot be null.")
    private UserRoleEnum userRole;

}