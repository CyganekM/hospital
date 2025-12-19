package by.asb.traningprogect.hospital.service;

import by.asb.traningprogect.hospital.exception.EntityCreatedException;
import by.asb.traningprogect.hospital.exception.OperationException;
import by.asb.traningprogect.hospital.model.dto.CardCreatorDto;
import by.asb.traningprogect.hospital.model.dto.CardResponseDto;

public interface CardService {

    CardResponseDto getCardById(Integer cardId);
    CardCreatorDto saveCard(CardCreatorDto cardCreatorDto) throws EntityCreatedException;

    CardResponseDto updateCard(CardCreatorDto cardCreatorDto) throws OperationException;
}
