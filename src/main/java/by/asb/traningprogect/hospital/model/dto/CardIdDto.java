package by.asb.traningprogect.hospital.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CardIdDto(Integer id) implements Serializable {
}