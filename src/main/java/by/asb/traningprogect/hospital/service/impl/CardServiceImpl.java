package by.asb.traningprogect.hospital.service.impl;

import by.asb.traningprogect.hospital.exception.EntityCreatedException;
import by.asb.traningprogect.hospital.exception.MessageException;
import by.asb.traningprogect.hospital.exception.OperationException;
import by.asb.traningprogect.hospital.model.dto.CardCreatorDto;
import by.asb.traningprogect.hospital.model.dto.CardResponseDto;
import by.asb.traningprogect.hospital.model.entity.Card;
import by.asb.traningprogect.hospital.model.entity.Patient;
import by.asb.traningprogect.hospital.model.mapper.CardCreatorMapper;
import by.asb.traningprogect.hospital.model.mapper.CardResponseMapper;
import by.asb.traningprogect.hospital.reposotory.CardRepository;
import by.asb.traningprogect.hospital.reposotory.PatientRepository;
import by.asb.traningprogect.hospital.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final CardResponseMapper cardMapper;

    private final CardCreatorMapper cardCreatorMapper;
    private final PatientRepository patientRepository;

    @Override
    public CardResponseDto getCardById(Integer cardId) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        Card card = cardRepository.save(optionalCard.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, MessageException.NOT_FOUND_ENTITY.getName().formatted(cardId))));
        return cardMapper.toDto(card);
    }

    @Override
    public CardCreatorDto saveCard(CardCreatorDto cardCreatorDto) throws EntityCreatedException {
        List<Card> cards = cardRepository.findCardByPatient_IdAndFinalDiagnosisIsNull(cardCreatorDto.getPatient().id() );
        if (!cards.isEmpty()) {
            throw new EntityCreatedException("The patient with id = %s has an open card".formatted(cardCreatorDto.getPatient().id()));
        }
        Patient patient = patientRepository.findById(cardCreatorDto.getPatient().id()).orElseThrow();
        if(patient.getStatus() == 0){
            new OperationException("Пациент с id = %s удалён".formatted(cardCreatorDto.getPatient().id()));
        }
        Card card = cardCreatorMapper.toEntity(cardCreatorDto);
        card = cardRepository.save(card);
        cardCreatorDto = cardCreatorMapper.toDto(card);
        return cardCreatorDto;
    }

    @Override
    public CardResponseDto updateCard(CardCreatorDto cardCreatorDto) throws OperationException {
        Optional<Card> optionalCard = cardRepository.findById(cardCreatorDto.getId());
        Card card = optionalCard.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, MessageException.NOT_FOUND_ENTITY.getName()));
        if(card.getFinalDiagnosis() != null){
            throw new OperationException("Карта с id = %s закрыта".formatted(cardCreatorDto.getId()));
        }
        cardCreatorMapper.partialUpdate(cardCreatorDto, card);
        return cardMapper.toDto(cardRepository.save(card));
    }
}
