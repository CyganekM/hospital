package by.asb.traningprogect.hospital.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EmployeeChangePasswordDto(
        String oldPassword,
        @NotBlank(message = "The employee's password cannot be empty")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,20}$",
                message = "The length of the login must be from 6 to 20 characters," +
                        " must contain at least one number," +
                        " at least one character in uppercase and lowercase")
        String newPassword
        )
{
}
