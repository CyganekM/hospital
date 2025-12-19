package by.asb.traningprogect.hospital.model.mapper;

import by.asb.traningprogect.hospital.model.dto.CardCreatorDto;
import by.asb.traningprogect.hospital.model.entity.Card;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardCreatorMapper {
    Card toEntity(CardCreatorDto cardCreatorDto);

    CardCreatorDto toDto(Card card);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Card partialUpdate(CardCreatorDto cardCreatorDto, @MappingTarget Card card);
}