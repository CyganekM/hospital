package by.asb.traningprogect.hospital.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.asb.traningprogect.hospital.model.dto.CardIdDto;
import by.asb.traningprogect.hospital.model.dto.EmployeeGetCardDto;
import by.asb.traningprogect.hospital.model.dto.ProcedureDto;
import by.asb.traningprogect.hospital.model.dto.ProcedureTypeDto;
import by.asb.traningprogect.hospital.model.entity.Card;
import by.asb.traningprogect.hospital.model.entity.Category;
import by.asb.traningprogect.hospital.model.entity.Employee;
import by.asb.traningprogect.hospital.model.entity.Patient;
import by.asb.traningprogect.hospital.model.entity.Procedure;
import by.asb.traningprogect.hospital.model.entity.ProcedureType;
import by.asb.traningprogect.hospital.model.entity.Specialization;
import by.asb.traningprogect.hospital.model.mapper.ProcedureMapper;
import by.asb.traningprogect.hospital.model.mapper.ProcedureRequestMapper;
import by.asb.traningprogect.hospital.reposotory.EmployeeRepository;
import by.asb.traningprogect.hospital.reposotory.ProcedureRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import by.asb.traningprogect.hospital.service.ProcedureService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProcedureServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProcedureServiceImplTest {
    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private ProcedureMapper procedureMapper;

    @MockBean
    private ProcedureRepository procedureRepository;

    @MockBean
    private ProcedureRequestMapper procedureRequestMapper;

    @Autowired
    private ProcedureService procedureService;


//    @Test
//    void testGetProcedureById() {
//        // Arrange
//        EmployeeGetCardDto employee = new EmployeeGetCardDto(1, "Doe", "Name", "Second Name");
//
//        ProcedureTypeDto procedureType = new ProcedureTypeDto(1, "Name");
//
//        LocalDate date = LocalDate.of(1970, 1, 1);
//        ProcedureDto procedureDto = new ProcedureDto(1, employee, procedureType, "Name",
//                "The characteristics of someone or something", date, new CardIdDto(1));
//
//        when(procedureMapper.toDto(Mockito.<Procedure>any())).thenReturn(procedureDto);
//
//        Category category = new Category();
//        category.setId(1);
//        category.setNameCategory("Name Category");
//
//        Specialization specialization = new Specialization();
//        specialization.setId(1);
//        specialization.setNameSpecialization("Name Specialization");
//
//        Employee employee2 = new Employee();
//        employee2.setCategory(category);
//        employee2.setEmail("jane.doe@example.org");
//        employee2.setId(1);
//        employee2.setName("Name");
//        employee2.setPassword("iloveyou");
//        employee2.setRoles(new ArrayList<>());
//        employee2.setSecondName("Second Name");
//        employee2.setSpecialization(specialization);
//        employee2.setSurname("Doe");
//
//        Patient patient = new Patient();
//        patient.setAddress("42 Main St");
//        patient.setCards(new ArrayList<>());
//        patient.setDob(LocalDate.of(1970, 1, 1));
//        patient.setEmail("jane.doe@example.org");
//        patient.setId("42");
//        patient.setName("Name");
//        patient.setPhone("6625550144");
//        patient.setSecondName("Second Name");
//        patient.setStatus((byte) 'A');
//        patient.setSurname("Doe");
//
//        Card card = new Card();
//        card.setDescription("The characteristics of someone or something");
//        card.setEmployee(employee2);
//        card.setFinalDiagnosis("Final Diagnosis");
//        card.setFinalDiagnosisDate(LocalDate.of(1970, 1, 1));
//        card.setId(1);
//        card.setPatient(patient);
//        card.setPreliminaryDiagnosis("Preliminary Diagnosis");
//        card.setPreliminaryDiagnosisDate(LocalDate.of(1970, 1, 1));
//        card.setProcedures(new ArrayList<>());
//
//        Category category2 = new Category();
//        category2.setId(1);
//        category2.setNameCategory("Name Category");
//
//        Specialization specialization2 = new Specialization();
//        specialization2.setId(1);
//        specialization2.setNameSpecialization("Name Specialization");
//
//        Employee employee3 = new Employee();
//        employee3.setCategory(category2);
//        employee3.setEmail("jane.doe@example.org");
//        employee3.setId(1);
//        employee3.setName("Name");
//        employee3.setPassword("iloveyou");
//        employee3.setRoles(new ArrayList<>());
//        employee3.setSecondName("Second Name");
//        employee3.setSpecialization(specialization2);
//        employee3.setSurname("Doe");
//
//        ProcedureType procedureType2 = new ProcedureType();
//        procedureType2.setId(1);
//        procedureType2.setName("Name");
//
//        Procedure procedure = new Procedure();
//        procedure.setCard(card);
//        procedure.setDate(LocalDate.of(1970, 1, 1));
//        procedure.setDescription("The characteristics of someone or something");
//        procedure.setEmployee(employee3);
//        procedure.setId(1);
//        procedure.setName("Name");
//        procedure.setProcedureType(procedureType2);
//        Optional<Procedure> ofResult = Optional.of(procedure);
//        when(procedureRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
//
//        // Act
//        ProcedureDto actualProcedureById = procedureService.getProcedureById(1);
//
//        // Assert
//        verify(procedureMapper).toDto(isA(Procedure.class));
//        verify(procedureRepository).findById(eq(1));
//        assertSame(procedureDto, actualProcedureById);
//    }
}
