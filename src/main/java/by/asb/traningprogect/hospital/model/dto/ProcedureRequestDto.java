package by.asb.traningprogect.hospital.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;


@JsonIgnoreProperties(ignoreUnknown = true)
public record ProcedureRequestDto(Integer procedureTypeId,
                                  @NotNull
                                  @NotEmpty
                                  String name,
                                  String description,
                                  LocalDate date,
                                  Integer cardId) implements Serializable {
}
