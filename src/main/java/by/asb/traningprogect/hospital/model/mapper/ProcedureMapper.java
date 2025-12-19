package by.asb.traningprogect.hospital.model.mapper;

import by.asb.traningprogect.hospital.model.dto.ProcedureDto;
import by.asb.traningprogect.hospital.model.entity.Procedure;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcedureMapper {
    Procedure toEntity(ProcedureDto procedureDto);

    ProcedureDto toDto(Procedure procedure);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Procedure partialUpdate(ProcedureDto procedureDto, @MappingTarget Procedure procedure);
}