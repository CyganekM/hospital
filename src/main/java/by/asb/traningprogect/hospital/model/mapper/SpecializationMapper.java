package by.asb.traningprogect.hospital.model.mapper;

import by.asb.traningprogect.hospital.model.dto.SpecializationDto;
import by.asb.traningprogect.hospital.model.entity.Specialization;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpecializationMapper {
    Specialization toEntity(SpecializationDto specializationDto);

    SpecializationDto toDto(Specialization specialization);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    Specialization partialUpdate(SpecializationDto specializationDto, @MappingTarget Specialization specialization);
}