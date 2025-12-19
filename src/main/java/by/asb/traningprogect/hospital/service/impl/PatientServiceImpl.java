package by.asb.traningprogect.hospital.service.impl;

import by.asb.traningprogect.hospital.exception.EntityCreatedException;
import by.asb.traningprogect.hospital.exception.MessageException;
import by.asb.traningprogect.hospital.exception.OperationException;
import by.asb.traningprogect.hospital.model.Filter;
import by.asb.traningprogect.hospital.model.dto.FilterDto;
import by.asb.traningprogect.hospital.model.dto.PatientDto;
import by.asb.traningprogect.hospital.model.dto.PatientResponseDto;
import by.asb.traningprogect.hospital.model.entity.Patient;
import by.asb.traningprogect.hospital.model.mapper.FilterMapper;
import by.asb.traningprogect.hospital.model.mapper.PatientMapper;
import by.asb.traningprogect.hospital.model.mapper.PatientResponseMapper;
import by.asb.traningprogect.hospital.reposotory.PatientCustomRepository;
import by.asb.traningprogect.hospital.reposotory.PatientRepository;
import by.asb.traningprogect.hospital.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final PatientResponseMapper patientResponseMapper;
    private final PatientCustomRepository patientCustomRepository;

    @Override
    public List<PatientDto> getPatientByFilter(List<FilterDto> filterDtoList, String fieldSort, Sort.Direction sort) throws OperationException {
        List<Filter<?>> filterList = filterDtoList.stream().map(FilterMapper::toFilter).collect(Collectors.toList());
        String[] fieldSortArray = fieldSort.split(",");
        return patientCustomRepository.getQueryResult(filterList, Sort.by(sort, fieldSortArray)).stream()
                .map(patientMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public PatientResponseDto getPatient(String patientId) throws OperationException {
        var patientOptional = patientRepository.findById(patientId);
        var patient = patientOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (patient.getStatus() == 0) {
            throw new OperationException("Пациент с id = %s удалён".formatted(patientId));
        }
        return patientResponseMapper.toDto(patientOptional.orElseThrow(() ->
               new ResponseStatusException(HttpStatus.NOT_FOUND, MessageException.NOT_FOUND_ENTITY.getName().formatted(patientId))));
    }

    @Override
    public PatientResponseDto delete(String id) {
        var optionalPatient = patientRepository.findById(id);
        var patient = optionalPatient.orElseThrow();
        patient.setStatus((byte) 0);
        return patientResponseMapper.toDto(patientRepository.save(patient));
    }

    @Override
    public PatientDto savePatient(PatientDto patientDto) throws EntityCreatedException {
        Optional<Patient> optionalPatient = patientRepository.findById(patientDto.getId());
        if (optionalPatient.isPresent()){
            throw new EntityCreatedException(MessageException.ENTITY_EXISTS.getName().formatted(patientDto.getId()));
        }
        Patient patient = patientMapper.toEntity(patientDto);
        return patientMapper.toDto(patientRepository.save(patient));
    }

    @Override
    public PatientDto updatePatient(PatientDto patientDto) {
        String patientId = patientDto.getId();
        Optional<Patient> patientOptional = patientRepository.findById(patientId);
        Patient patient = patientMapper.updateWithNull(patientDto, patientOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, MessageException.NOT_FOUND_ENTITY.getName().formatted(patientId))));
        return patientMapper.toDto(patient);
    }

    @Override
    public List<PatientResponseDto> getAllActivePatients() {
        List<Patient> patients = patientRepository.findByStatus((byte) 1);
        List<PatientResponseDto> patientResponseDtoList = patients.stream().map(patientResponseMapper::toDto).toList();
        return patientResponseDtoList;
    }
}
