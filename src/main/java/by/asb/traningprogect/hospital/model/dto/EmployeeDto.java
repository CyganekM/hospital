package by.asb.traningprogect.hospital.model.dto ;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

import java.io.Serializable;
import java.util.Collection;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto implements Serializable {
    Integer id;
    SpecializationDto specialization;
    CategoryDto category;
    @NotBlank(message = "The employee's surname cannot be empty")
    String surname;
    @NotBlank(message = "The employee's name cannot be empty")
    String name;
    @NotBlank(message = "The employee's secondName cannot be empty")
    String secondName;
    @NotBlank(message = "The employee's email cannot be empty")
    @Email(message = "The email address must be in the format user@example.com")
    String email;
    @NotBlank(message = "The employee's password cannot be empty")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,20}$",
            message = "The length of the login must be from 6 to 20 characters," +
                    " must contain at least one number," +
                    " at least one character in uppercase and lowercase")
    String password;
    Collection<RoleDto> roles;
}