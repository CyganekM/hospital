package by.asb.traningprogect.hospital.model.mapper;


import by.asb.traningprogect.hospital.model.Filter;
import by.asb.traningprogect.hospital.model.dto.FilterDto;
import by.asb.traningprogect.hospital.model.entity.Patient_;
import lombok.experimental.UtilityClass;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@UtilityClass
public class FilterMapper {

    public Filter<?> toFilter(FilterDto filterDto) {

        return switch (filterDto.getKey()) {
            case DOB:
                Filter<LocalDate> filterDate = Filter.<LocalDate>builder()
                    .key(Patient_.DOB)
                    .queryOperator(filterDto.getOperator())
                    .build();
                try{
                    filterDate.setValue(LocalDate.parse(filterDto.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

                } catch (Exception ignored){
                }
                try{
                    filterDate.setValues(filterDto.getValues().stream().map(date -> LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))).toList());
                } catch (Exception ignored){
                }
                yield filterDate;
            case NAME:
                yield Filter.<String>builder()
                    .key(Patient_.NAME)
                    .queryOperator(filterDto.getOperator())
                    .value(filterDto.getValue())
                    .values(filterDto.getValues())
                    .build();
            case SURNAME:
                yield Filter.<String>builder()
                    .key(Patient_.SURNAME)
                    .queryOperator(filterDto.getOperator())
                    .value(filterDto.getValue())
                    .build();
            case SECOND_NAME:
                yield  Filter.<String>builder()
                    .key(Patient_.SECOND_NAME)
                    .queryOperator(filterDto.getOperator())
                    .value(filterDto.getValue())
                    .build();
            case ADDRESS:
                yield Filter.<String>builder()
                    .key(Patient_.ADDRESS)
                    .queryOperator(filterDto.getOperator())
                    .value(filterDto.getValue())
                    .build();
            case PHONE:
                yield Filter.<String>builder()
                    .key(Patient_.PHONE)
                    .queryOperator(filterDto.getOperator())
                    .value(filterDto.getValue())
                    .build();
            case EMAIL:
                yield Filter.<String>builder()
                        .key(Patient_.EMAIL)
                        .queryOperator(filterDto.getOperator())
                        .value(filterDto.getValue())
                        .build();
        };
    }
}
