package by.asb.traningprogect.hospital.model.dto;

import by.asb.traningprogect.hospital.model.entity.Patient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientDto implements Serializable {

    @NotBlank(message = "The patient's ID cannot be empty")
    String id;
    @NotBlank(message = "The patient's name cannot be empty")
    String name;
    @NotBlank(message = "The patient's surname cannot be empty")
    String surname;
    @NotBlank(message = "The patient's secondName cannot be empty")
    String secondName;
    LocalDate dob;
    @NotBlank(message = "The patient's address cannot be empty")
    String address;
    List<Integer> cardIds;
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{2}\\))|\\d{3})[-]?\\d{3}[-]?\\d{4}$",
            message = "Enter the phone number according to the template. Example +375(29)725-4529")
    String phone;
    @Email(message = "Invalid email address")
    String email;
    Byte status;
}