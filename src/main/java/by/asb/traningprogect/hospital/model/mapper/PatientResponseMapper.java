package by.asb.traningprogect.hospital.model.mapper;

import by.asb.traningprogect.hospital.model.dto.PatientResponseDto;
import by.asb.traningprogect.hospital.model.entity.Card;
import by.asb.traningprogect.hospital.model.entity.Patient;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientResponseMapper {
    Patient toEntity(PatientResponseDto patientResponseDto);

    @Mapping(target = "cardsId", expression = "java(cardsToCardIds(patient.getCards()))")
    PatientResponseDto toDto(Patient patient);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Patient partialUpdate(PatientResponseDto patientResponseDto, @MappingTarget Patient patient);

    default List<Integer> cardsToCardIds(List<Card> cards) {
        return cards.stream().map(Card::getId).collect(Collectors.toList());
    }
}