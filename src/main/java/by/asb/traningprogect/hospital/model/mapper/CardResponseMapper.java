package by.asb.traningprogect.hospital.model.mapper;

import by.asb.traningprogect.hospital.model.dto.CardResponseDto;
import by.asb.traningprogect.hospital.model.entity.Card;
import by.asb.traningprogect.hospital.model.entity.Employee;
import by.asb.traningprogect.hospital.model.entity.Patient;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ProcedureMapper.class})
public interface CardResponseMapper {
    @Mapping(source = "patientId", target = "patient.id")
    @Mapping(source = "employeeId", target = "employee.id")
    Card toEntity(CardResponseDto cardResponseDto);

    @AfterMapping
    default void linkProcedures(@MappingTarget Card card) {
        card.getProcedures().forEach(procedure -> procedure.setCard(card));
    }

    @InheritInverseConfiguration(name = "toEntity")
    CardResponseDto toDto(Card card);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "patientId", target = "patient")
    @Mapping(source = "employeeId", target = "employee")
    Card partialUpdate(CardResponseDto cardResponseDto, @MappingTarget Card card);

    default Employee createEmployee(Integer employeeId) {
        if (employeeId == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(employeeId);
        return employee;
    }

    default Patient createPatient(String patientId) {
        if (patientId == null) {
            return null;
        }
        Patient patient = new Patient();
        patient.setId(patientId);
        return patient;
    }
}