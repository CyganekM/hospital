package by.asb.traningprogect.hospital.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PatientResponseDto(String id, String name, String surname, String secondName, LocalDate dob,
                                 String address, List<Integer> cardsId, String phone, Byte status,
                                 String email) implements Serializable {
}