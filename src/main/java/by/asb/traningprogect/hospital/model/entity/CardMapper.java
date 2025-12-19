package by.asb.traningprogect.hospital.model.entity;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardMapper {
    @Mapping(source = "employeeName", target = "employee.name")
    @Mapping(source = "employeeId", target = "employee.id")
    Card toEntity(CardTestDto cardTestDto);

    @InheritInverseConfiguration(name = "toEntity")
    CardTestDto toCardTestDto(Card card);
}