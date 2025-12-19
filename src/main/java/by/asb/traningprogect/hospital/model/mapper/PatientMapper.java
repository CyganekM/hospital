package by.asb.traningprogect.hospital.model.mapper;

import by.asb.traningprogect.hospital.model.dto.PatientDto;
import by.asb.traningprogect.hospital.model.entity.Patient;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {CardResponseMapper.class})
public interface PatientMapper {
    Patient toEntity(PatientDto patientDto);



    PatientDto toDto(Patient patient);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Patient partialUpdate(PatientDto patientDto, @MappingTarget Patient patient);

    Patient updateWithNull(PatientDto patientDto, @MappingTarget Patient patient);
}