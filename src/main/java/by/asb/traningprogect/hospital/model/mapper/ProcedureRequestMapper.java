package by.asb.traningprogect.hospital.model.mapper;

import by.asb.traningprogect.hospital.model.dto.ProcedureRequestDto;
import by.asb.traningprogect.hospital.model.entity.Card;
import by.asb.traningprogect.hospital.model.entity.Procedure;
import by.asb.traningprogect.hospital.model.entity.ProcedureType;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcedureRequestMapper {
    @Mapping(source = "cardId", target = "card.id")
    @Mapping(source = "procedureTypeId", target = "procedureType.id")
    Procedure toEntity(ProcedureRequestDto procedureRequestDto);

    @InheritInverseConfiguration(name = "toEntity")
    ProcedureRequestDto toDto(Procedure procedure);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "cardId", target = "card")
    @Mapping(source = "procedureTypeId", target = "procedureType")
    Procedure partialUpdate(ProcedureRequestDto procedureRequestDto, @MappingTarget Procedure procedure);

    default ProcedureType createProcedureType(Integer procedureTypeId) {
        if (procedureTypeId == null) {
            return null;
        }
        ProcedureType procedureType = new ProcedureType();
        procedureType.setId(procedureTypeId);
        return procedureType;
    }

    default Card createCard(Integer cardId) {
        if (cardId == null) {
            return null;
        }
        Card card = new Card();
        card.setId(cardId);
        return card;
    }
}