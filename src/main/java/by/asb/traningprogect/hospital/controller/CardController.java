package by.asb.traningprogect.hospital.controller;

import by.asb.traningprogect.hospital.exception.EntityCreatedException;
import by.asb.traningprogect.hospital.exception.OperationException;
import by.asb.traningprogect.hospital.model.dto.CardCreatorDto;
import by.asb.traningprogect.hospital.model.dto.CardResponseDto;
import by.asb.traningprogect.hospital.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @Operation(summary ="Найти карточку пациента по идентификатору")
    @GetMapping("/{cardId}")
    public CardResponseDto getCardById(@PathVariable Integer cardId){
        CardResponseDto cardResponseDto = cardService.getCardById(cardId);
        return cardResponseDto;
    }

    @Operation(summary ="Создать карточку")
    @PostMapping
    @Secured("ROLE_DOCTOR")
    public CardCreatorDto create(@Valid @RequestBody CardCreatorDto cardCreatorDto) throws EntityCreatedException {
        return cardService.saveCard(cardCreatorDto);
    }

    @Operation(summary ="Обновить карточку")
    @PutMapping
    @Secured("ROLE_DOCTOR")
    public CardResponseDto updateCard(@RequestBody CardCreatorDto cardCreatorDto) throws OperationException {
        return cardService.updateCard(cardCreatorDto);
    }
}

