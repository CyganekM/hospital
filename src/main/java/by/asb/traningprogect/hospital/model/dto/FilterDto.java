package by.asb.traningprogect.hospital.model.dto;


import by.asb.traningprogect.hospital.model.FieldFilter;
import by.asb.traningprogect.hospital.model.QueryOperator;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;



import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilterDto {

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "The FieldFilter's key cannot be empty")
    private FieldFilter key;
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "The FieldFilter's operator cannot be empty")
    private QueryOperator operator;
    private String value;
    private List<String> values;
}
