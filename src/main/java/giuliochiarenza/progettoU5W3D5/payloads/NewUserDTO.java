package giuliochiarenza.progettoU5W3D5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewUserDTO(
        @NotEmpty(message = "Name required")
        String name,
        @NotEmpty(message = "Surname required")
        String surname,
        @NotEmpty(message = "Email required")
        @Email(message = "The email entered is invalid")
        String email,
        @NotEmpty(message = "Password required")
        @Size(min = 4, message = "The password must have at least 4 characters")
        String password) {
}