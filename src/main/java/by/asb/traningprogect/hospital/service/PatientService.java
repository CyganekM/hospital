package by.asb.traningprogect.hospital.service;

import by.asb.traningprogect.hospital.exception.EntityCreatedException;
import by.asb.traningprogect.hospital.exception.OperationException;
import by.asb.traningprogect.hospital.model.dto.FilterDto;
import by.asb.traningprogect.hospital.model.dto.PatientDto;
import by.asb.traningprogect.hospital.model.dto.PatientResponseDto;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface PatientService {

    PatientResponseDto getPatient(String patientId) throws OperationException;
    PatientDto savePatient(PatientDto patientDto) throws EntityCreatedException;

    PatientDto updatePatient(PatientDto patientDto);


    PatientResponseDto delete(String id);

    List<PatientDto> getPatientByFilter(List<FilterDto> filterDtoList, String fieldSort, Sort.Direction sort) throws OperationException;

    List<PatientResponseDto> getAllActivePatients();
}
