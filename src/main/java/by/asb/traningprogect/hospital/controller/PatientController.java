package by.asb.traningprogect.hospital.controller;

import by.asb.traningprogect.hospital.exception.EntityCreatedException;
import by.asb.traningprogect.hospital.exception.OperationException;
import by.asb.traningprogect.hospital.model.dto.FilterDto;
import by.asb.traningprogect.hospital.model.dto.PatientDto;
import by.asb.traningprogect.hospital.model.dto.PatientResponseDto;
import by.asb.traningprogect.hospital.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
public class PatientController {

    private final PatientService patientService;
    private static final String FIELD_SORT = "surname,name";
    private static final String SORT_ORDER = "DESC";

    @Operation(summary ="Найти  пациента по идентификатору")
    @GetMapping("/{patientId}")
    public PatientResponseDto getPatient(@PathVariable String patientId) throws OperationException {
        PatientResponseDto patientDto = patientService.getPatient(patientId);
        return patientDto;
    }

    @Operation(summary ="Добавить пациента")
    @PostMapping
    public PatientDto savePatient(@Valid @RequestBody PatientDto patientDto) throws EntityCreatedException {
        return patientService.savePatient(patientDto);
    }

    @Operation(summary = "Показать всех пациентов")
    @GetMapping
    public List<PatientResponseDto> getAllActivePatients() {
        return patientService.getAllActivePatients();
    }

    @Operation(summary ="Изменить данные об пациенте")
    @PutMapping()
    public PatientDto update(@RequestBody PatientDto patientDto) throws EntityCreatedException {
        return patientService.updatePatient(patientDto);
    }

    @Operation(summary ="Найти  пациента по фильтру",
    description = "Принимает параметры fieldSort=dob&sort=ASC. Если параметры не указаны, то будет использована " +
            "сортировка по возрастанию по полям surname,name" +
            "Операторы сравнения: GREATER_THAN, LESS_THAN, EQUALS, LIKE, NOT_EQ.\n" +
            "GREATER_THAN и LESS_THAN  работают только с датами и числами" +
            "LIKE, EQUALS, NOT_EQ и IN со строками." +
            "Доступные поля:  DOB, NAME, SURNAME, SECOND_NAME, ADDRESS, PHONE, EMAIL." +
            "Для IN использовать values")
    @GetMapping("/filter")
    public List<PatientDto> getCurrentOrder(@RequestBody(required = false) List<FilterDto> filterDto,
                                            @RequestParam(defaultValue = FIELD_SORT) String fieldSort,
                                            @RequestParam(defaultValue = SORT_ORDER) Sort.Direction sort) throws OperationException {
        return patientService.getPatientByFilter(filterDto, fieldSort, sort);
    }

    @Operation(summary ="Удалить пациента")
    @DeleteMapping("/{patientId}")
    public PatientResponseDto deletePatient(@PathVariable String patientId) {
        return patientService.delete(patientId);
    }
}