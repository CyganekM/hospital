package by.asb.traningprogect.hospital.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcedureDto implements Serializable {
    Integer id;
    @NotNull
    EmployeeGetCardDto employee;
    @NotNull
    ProcedureTypeDto procedureType;
    String name;
    String description;
    LocalDate date;
    @NotNull
    CardIdDto card;
}