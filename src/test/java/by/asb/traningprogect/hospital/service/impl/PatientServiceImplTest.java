package by.asb.traningprogect.hospital.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyByte;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.asb.traningprogect.hospital.exception.EntityCreatedException;
import by.asb.traningprogect.hospital.exception.OperationException;
import by.asb.traningprogect.hospital.model.FieldFilter;
import by.asb.traningprogect.hospital.model.Filter;
import by.asb.traningprogect.hospital.model.QueryOperator;
import by.asb.traningprogect.hospital.model.dto.FilterDto;
import by.asb.traningprogect.hospital.model.dto.PatientDto;
import by.asb.traningprogect.hospital.model.dto.PatientResponseDto;
import by.asb.traningprogect.hospital.model.entity.Patient;
import by.asb.traningprogect.hospital.model.mapper.PatientMapper;
import by.asb.traningprogect.hospital.model.mapper.PatientResponseMapper;
import by.asb.traningprogect.hospital.reposotory.PatientCustomRepository;
import by.asb.traningprogect.hospital.reposotory.PatientRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

@ContextConfiguration(classes = {PatientServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class PatientServiceImplTest {
    @MockBean
    private PatientCustomRepository patientCustomRepository;

    @MockBean
    private PatientMapper patientMapper;

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private PatientResponseMapper patientResponseMapper;

    @Autowired
    private PatientServiceImpl patientServiceImpl;

    @Test
    void testGetPatient() throws OperationException {
        // Arrange
        Patient patient = new Patient();
        patient.setAddress("42 Main St");
        patient.setCards(new ArrayList<>());
        patient.setDob(LocalDate.of(1970, 1, 1));
        patient.setEmail("jane.doe@example.org");
        patient.setId("42");
        patient.setName("Name");
        patient.setPhone("6625550144");
        patient.setSecondName("Second Name");
        patient.setStatus((byte) 'A');
        patient.setSurname("Doe");
        Optional<Patient> ofResult = Optional.of(patient);
        when(patientRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        LocalDate dob = LocalDate.of(1970, 1, 1);
        PatientResponseDto patientResponseDto = new PatientResponseDto("42", "Name", "Doe", "Second Name", dob,
                "42 Main St", new ArrayList<>(), "6625550144", (byte) 'A', "jane.doe@example.org");

        when(patientResponseMapper.toDto(Mockito.<Patient>any())).thenReturn(patientResponseDto);

        // Act
        PatientResponseDto actualPatient = patientServiceImpl.getPatient("42");

        // Assert
        verify(patientResponseMapper).toDto(isA(Patient.class));
        verify(patientRepository).findById(eq("42"));
        assertSame(patientResponseDto, actualPatient);
    }

    @Test
    void testGetPatient2() throws OperationException {
        // Arrange
        Patient patient = new Patient();
        patient.setAddress("42 Main St");
        patient.setCards(new ArrayList<>());
        patient.setDob(LocalDate.of(1970, 1, 1));
        patient.setEmail("jane.doe@example.org");
        patient.setId("42");
        patient.setName("Name");
        patient.setPhone("6625550144");
        patient.setSecondName("Second Name");
        patient.setStatus((byte) 0);
        patient.setSurname("Doe");
        Optional<Patient> ofResult = Optional.of(patient);
        when(patientRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(OperationException.class, () -> patientServiceImpl.getPatient("42"));
        verify(patientRepository).findById(eq("42"));
    }

    @Test
    void testGetPatient3() throws OperationException {
        // Arrange
        Optional<Patient> emptyResult = Optional.empty();
        when(patientRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> patientServiceImpl.getPatient("42"));
        verify(patientRepository).findById(eq("42"));
    }

    @Test
    void testSavePatient() throws EntityCreatedException {
        // Arrange
        Patient patient = new Patient();
        patient.setAddress("42 Main St");
        patient.setCards(new ArrayList<>());
        patient.setDob(LocalDate.of(1970, 1, 1));
        patient.setEmail("jane.doe@example.org");
        patient.setId("42");
        patient.setName("Name");
        patient.setPhone("6625550144");
        patient.setSecondName("Second Name");
        patient.setStatus((byte) 'A');
        patient.setSurname("Doe");
        Optional<Patient> ofResult = Optional.of(patient);
        when(patientRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        LocalDate dob = LocalDate.of(1970, 1, 1);

        // Act and Assert
        assertThrows(EntityCreatedException.class, () -> patientServiceImpl.savePatient(new PatientDto("42", "Name", "Doe",
                "Second Name", dob, "42 Main St", new ArrayList<>(), "6625550144", "jane.doe@example.org", (byte) 'A')));
        verify(patientRepository).findById(eq("42"));
    }

    @Test
    void testSavePatient2() throws EntityCreatedException {
        // Arrange
        Patient patient = new Patient();
        patient.setAddress("42 Main St");
        patient.setCards(new ArrayList<>());
        patient.setDob(LocalDate.of(1970, 1, 1));
        patient.setEmail("jane.doe@example.org");
        patient.setId("42");
        patient.setName("Name");
        patient.setPhone("6625550144");
        patient.setSecondName("Second Name");
        patient.setStatus((byte) 'A');
        patient.setSurname("Doe");
        when(patientRepository.save(Mockito.<Patient>any())).thenReturn(patient);
        Optional<Patient> emptyResult = Optional.empty();
        when(patientRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

        Patient patient2 = new Patient();
        patient2.setAddress("42 Main St");
        patient2.setCards(new ArrayList<>());
        patient2.setDob(LocalDate.of(1970, 1, 1));
        patient2.setEmail("jane.doe@example.org");
        patient2.setId("42");
        patient2.setName("Name");
        patient2.setPhone("6625550144");
        patient2.setSecondName("Second Name");
        patient2.setStatus((byte) 'A');
        patient2.setSurname("Doe");
        LocalDate dob = LocalDate.of(1970, 1, 1);
        PatientDto patientDto = new PatientDto("42", "Name", "Doe", "Second Name", dob, "42 Main St", new ArrayList<>(),
                "6625550144", "jane.doe@example.org", (byte) 'A');

        when(patientMapper.toDto(Mockito.<Patient>any())).thenReturn(patientDto);
        when(patientMapper.toEntity(Mockito.<PatientDto>any())).thenReturn(patient2);
        LocalDate dob2 = LocalDate.of(1970, 1, 1);

        // Act
        PatientDto actualSavePatientResult = patientServiceImpl.savePatient(new PatientDto("42", "Name", "Doe",
                "Second Name", dob2, "42 Main St", new ArrayList<>(), "6625550144", "jane.doe@example.org", (byte) 'A'));

        // Assert
        verify(patientMapper).toDto(isA(Patient.class));
        verify(patientMapper).toEntity(isA(PatientDto.class));
        verify(patientRepository).findById(eq("42"));
        verify(patientRepository).save(isA(Patient.class));
        assertSame(patientDto, actualSavePatientResult);
    }

    @Test
    void testUpdatePatient() {
        // Arrange
        Patient patient = new Patient();
        patient.setAddress("42 Main St");
        patient.setCards(new ArrayList<>());
        patient.setDob(LocalDate.of(1970, 1, 1));
        patient.setEmail("jane.doe@example.org");
        patient.setId("42");
        patient.setName("Name");
        patient.setPhone("6625550144");
        patient.setSecondName("Second Name");
        patient.setStatus((byte) 'A');
        patient.setSurname("Doe");
        Optional<Patient> ofResult = Optional.of(patient);
        when(patientRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        Patient patient2 = new Patient();
        patient2.setAddress("42 Main St");
        patient2.setCards(new ArrayList<>());
        patient2.setDob(LocalDate.of(1970, 1, 1));
        patient2.setEmail("jane.doe@example.org");
        patient2.setId("42");
        patient2.setName("Name");
        patient2.setPhone("6625550144");
        patient2.setSecondName("Second Name");
        patient2.setStatus((byte) 'A');
        patient2.setSurname("Doe");
        LocalDate dob = LocalDate.of(1970, 1, 1);
        PatientDto patientDto = new PatientDto("42", "Name", "Doe", "Second Name", dob, "42 Main St", new ArrayList<>(),
                "6625550144", "jane.doe@example.org", (byte) 'A');

        when(patientMapper.toDto(Mockito.<Patient>any())).thenReturn(patientDto);
        when(patientMapper.updateWithNull(Mockito.<PatientDto>any(), Mockito.<Patient>any())).thenReturn(patient2);
        LocalDate dob2 = LocalDate.of(1970, 1, 1);

        // Act
        PatientDto actualUpdatePatientResult = patientServiceImpl.updatePatient(new PatientDto("42", "Name", "Doe",
                "Second Name", dob2, "42 Main St", new ArrayList<>(), "6625550144", "jane.doe@example.org", (byte) 'A'));

        // Assert
        verify(patientMapper).toDto(isA(Patient.class));
        verify(patientMapper).updateWithNull(isA(PatientDto.class), isA(Patient.class));
        verify(patientRepository).findById(eq("42"));
        assertSame(patientDto, actualUpdatePatientResult);
    }

    @Test
    void testUpdatePatient2() {
        // Arrange
        Optional<Patient> emptyResult = Optional.empty();
        when(patientRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);
        LocalDate dob = LocalDate.of(1970, 1, 1);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> patientServiceImpl.updatePatient(new PatientDto("42", "Name",
                "Doe", "Second Name", dob, "42 Main St", new ArrayList<>(), "6625550144", "jane.doe@example.org", (byte) 'A')));
        verify(patientRepository).findById(eq("42"));
    }

    @Test
    void testDelete() {
        // Arrange
        Patient patient = new Patient();
        patient.setAddress("42 Main St");
        patient.setCards(new ArrayList<>());
        patient.setDob(LocalDate.of(1970, 1, 1));
        patient.setEmail("jane.doe@example.org");
        patient.setId("42");
        patient.setName("Name");
        patient.setPhone("6625550144");
        patient.setSecondName("Second Name");
        patient.setStatus((byte) 'A');
        patient.setSurname("Doe");
        Optional<Patient> ofResult = Optional.of(patient);

        Patient patient2 = new Patient();
        patient2.setAddress("42 Main St");
        patient2.setCards(new ArrayList<>());
        patient2.setDob(LocalDate.of(1970, 1, 1));
        patient2.setEmail("jane.doe@example.org");
        patient2.setId("42");
        patient2.setName("Name");
        patient2.setPhone("6625550144");
        patient2.setSecondName("Second Name");
        patient2.setStatus((byte) 'A');
        patient2.setSurname("Doe");
        when(patientRepository.save(Mockito.<Patient>any())).thenReturn(patient2);
        when(patientRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        LocalDate dob = LocalDate.of(1970, 1, 1);
        PatientResponseDto patientResponseDto = new PatientResponseDto("42", "Name", "Doe", "Second Name", dob,
                "42 Main St", new ArrayList<>(), "6625550144", (byte) 'A', "jane.doe@example.org");

        when(patientResponseMapper.toDto(Mockito.<Patient>any())).thenReturn(patientResponseDto);

        // Act
        PatientResponseDto actualDeleteResult = patientServiceImpl.delete("42");

        // Assert
        verify(patientResponseMapper).toDto(isA(Patient.class));
        verify(patientRepository).findById(eq("42"));
        verify(patientRepository).save(isA(Patient.class));
        assertSame(patientResponseDto, actualDeleteResult);
    }

    @Test
    void testGetPatientByFilter() throws OperationException {
        // Arrange
        when(patientCustomRepository.getQueryResult(Mockito.<List<Filter<?>>>any(), Mockito.<Sort>any()))
                .thenReturn(new ArrayList<>());

        // Act
        List<PatientDto> actualPatientByFilter = patientServiceImpl.getPatientByFilter(new ArrayList<>(), "Field Sort",
                Sort.Direction.ASC);

        // Assert
        verify(patientCustomRepository).getQueryResult(isA(List.class), isA(Sort.class));
        assertTrue(actualPatientByFilter.isEmpty());
    }

    @Test
    void testGetPatientByFilter2() throws OperationException {
        // Arrange
        when(patientCustomRepository.getQueryResult(Mockito.<List<Filter<?>>>any(), Mockito.<Sort>any()))
                .thenReturn(new ArrayList<>());

        ArrayList<FilterDto> filterDtoList = new ArrayList<>();
        FilterDto.FilterDtoBuilder valueResult = FilterDto.builder()
                .key(FieldFilter.DOB)
                .operator(QueryOperator.GREATER_THAN)
                .value("42");
        FilterDto buildResult = valueResult.values(new ArrayList<>()).build();
        filterDtoList.add(buildResult);

        // Act
        List<PatientDto> actualPatientByFilter = patientServiceImpl.getPatientByFilter(filterDtoList, "Field Sort",
                Sort.Direction.ASC);

        // Assert
        verify(patientCustomRepository).getQueryResult(isA(List.class), isA(Sort.class));
        assertTrue(actualPatientByFilter.isEmpty());
    }

    @Test
    void testGetPatientByFilter3() throws OperationException {
        // Arrange
        when(patientCustomRepository.getQueryResult(Mockito.<List<Filter<?>>>any(), Mockito.<Sort>any()))
                .thenReturn(new ArrayList<>());

        ArrayList<FilterDto> filterDtoList = new ArrayList<>();
        FilterDto.FilterDtoBuilder valueResult = FilterDto.builder()
                .key(FieldFilter.DOB)
                .operator(QueryOperator.GREATER_THAN)
                .value("42");
        FilterDto buildResult = valueResult.values(new ArrayList<>()).build();
        filterDtoList.add(buildResult);
        FilterDto.FilterDtoBuilder valueResult2 = FilterDto.builder()
                .key(FieldFilter.DOB)
                .operator(QueryOperator.GREATER_THAN)
                .value("42");
        FilterDto buildResult2 = valueResult2.values(new ArrayList<>()).build();
        filterDtoList.add(buildResult2);

        // Act
        List<PatientDto> actualPatientByFilter = patientServiceImpl.getPatientByFilter(filterDtoList, "Field Sort",
                Sort.Direction.ASC);

        // Assert
        verify(patientCustomRepository).getQueryResult(isA(List.class), isA(Sort.class));
        assertTrue(actualPatientByFilter.isEmpty());
    }

    @Test
    void testGetPatientByFilter4() throws OperationException {
        // Arrange
        when(patientCustomRepository.getQueryResult(Mockito.<List<Filter<?>>>any(), Mockito.<Sort>any()))
                .thenReturn(new ArrayList<>());

        // Act
        List<PatientDto> actualPatientByFilter = patientServiceImpl.getPatientByFilter(new ArrayList<>(), "Field Sort",
                Sort.Direction.DESC);

        // Assert
        verify(patientCustomRepository).getQueryResult(isA(List.class), isA(Sort.class));
        assertTrue(actualPatientByFilter.isEmpty());
    }

    @Test
    void testGetPatientByFilter5() throws OperationException {
        // Arrange
        when(patientCustomRepository.getQueryResult(Mockito.<List<Filter<?>>>any(), Mockito.<Sort>any()))
                .thenThrow(new OperationException("An error occurred"));

        // Act and Assert
        assertThrows(OperationException.class,
                () -> patientServiceImpl.getPatientByFilter(new ArrayList<>(), "Field Sort", Sort.Direction.ASC));
        verify(patientCustomRepository).getQueryResult(isA(List.class), isA(Sort.class));
    }

    @Test
    void testGetAllActivePatients() {
        // Arrange
        when(patientRepository.findByStatus(anyByte())).thenReturn(new ArrayList<>());
        // Act
        List<PatientResponseDto> actualAllActivePatients = patientServiceImpl.getAllActivePatients();

        // Assert
        verify(patientRepository).findByStatus(eq((byte) 1));
        assertTrue(actualAllActivePatients.isEmpty());
    }
}
