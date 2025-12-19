package by.asb.traningprogect.hospital.model.mapper;

import by.asb.traningprogect.hospital.model.entity.Employee;
import by.asb.traningprogect.hospital.model.dto.EmployeeRequestDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {SpecializationMapper.class, CategoryMapper.class})
public interface EmployeeRequestMapper {
    Employee toEntity(EmployeeRequestDto employeeTestDto);

    EmployeeRequestDto toDto(Employee employee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Employee partialUpdate(EmployeeRequestDto employeeRequestDto, @MappingTarget Employee employee);
}