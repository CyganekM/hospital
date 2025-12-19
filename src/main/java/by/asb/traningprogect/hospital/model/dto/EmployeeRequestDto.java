package by.asb.traningprogect.hospital.model.dto;

import by.asb.traningprogect.hospital.model.entity.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;
import java.util.Collection;

/**
 * DTO for {@link Employee}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeRequestDto implements Serializable {
    Integer id;
    SpecializationDto specialization;
    CategoryDto category;
    String surname;
    String name;
    String secondName;
    Collection<RoleDto> roles;
    String email;
}