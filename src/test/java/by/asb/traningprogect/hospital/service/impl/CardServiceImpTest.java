package by.asb.traningprogect.hospital.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.asb.traningprogect.hospital.exception.EntityCreatedException;
import by.asb.traningprogect.hospital.exception.OperationException;
import by.asb.traningprogect.hospital.model.dto.CardCreatorDto;
import by.asb.traningprogect.hospital.model.dto.CardResponseDto;
import by.asb.traningprogect.hospital.model.dto.EmployeeGetCardDto;
import by.asb.traningprogect.hospital.model.dto.PatientCardDto;
import by.asb.traningprogect.hospital.model.entity.Card;
import by.asb.traningprogect.hospital.model.entity.Category;
import by.asb.traningprogect.hospital.model.entity.Employee;
import by.asb.traningprogect.hospital.model.entity.Patient;
import by.asb.traningprogect.hospital.model.entity.Specialization;
import by.asb.traningprogect.hospital.model.mapper.CardCreatorMapper;
import by.asb.traningprogect.hospital.model.mapper.CardResponseMapper;
import by.asb.traningprogect.hospital.reposotory.CardRepository;
import by.asb.traningprogect.hospital.reposotory.PatientRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CardServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CardServiceImpTest {
    @MockBean
    private CardCreatorMapper cardCreatorMapper;

    @MockBean
    private CardRepository cardRepository;

    @MockBean
    private CardResponseMapper cardResponseMapper;

    @Autowired
    private CardServiceImpl cardServiceImpl;

    @MockBean
    private PatientRepository patientRepository;

    @Test
    void testGetCardById() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setNameCategory("Name Category");

        Specialization specialization = new Specialization();
        specialization.setId(1);
        specialization.setNameSpecialization("Name Specialization");

        Employee employee = new Employee();
        employee.setCategory(category);
        employee.setEmail("jane.doe@example.org");
        employee.setId(1);
        employee.setName("Name");
        employee.setPassword("iloveyou");
        employee.setRoles(new ArrayList<>());
        employee.setSecondName("Second Name");
        employee.setSpecialization(specialization);
        employee.setSurname("Doe");

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

        Card card = new Card();
        card.setDescription("The characteristics of someone or something");
        card.setEmployee(employee);
        card.setFinalDiagnosis("Final Diagnosis");
        card.setFinalDiagnosisDate(LocalDate.of(1970, 1, 1));
        card.setId(1);
        card.setPatient(patient);
        card.setPreliminaryDiagnosis("Preliminary Diagnosis");
        card.setPreliminaryDiagnosisDate(LocalDate.of(1970, 1, 1));
        card.setProcedures(new ArrayList<>());
        Optional<Card> ofResult = Optional.of(card);

        Category category2 = new Category();
        category2.setId(1);
        category2.setNameCategory("Name Category");

        Specialization specialization2 = new Specialization();
        specialization2.setId(1);
        specialization2.setNameSpecialization("Name Specialization");

        Employee employee2 = new Employee();
        employee2.setCategory(category2);
        employee2.setEmail("jane.doe@example.org");
        employee2.setId(1);
        employee2.setName("Name");
        employee2.setPassword("iloveyou");
        employee2.setRoles(new ArrayList<>());
        employee2.setSecondName("Second Name");
        employee2.setSpecialization(specialization2);
        employee2.setSurname("Doe");

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

        Card card2 = new Card();
        card2.setDescription("The characteristics of someone or something");
        card2.setEmployee(employee2);
        card2.setFinalDiagnosis("Final Diagnosis");
        card2.setFinalDiagnosisDate(LocalDate.of(1970, 1, 1));
        card2.setId(1);
        card2.setPatient(patient2);
        card2.setPreliminaryDiagnosis("Preliminary Diagnosis");
        card2.setPreliminaryDiagnosisDate(LocalDate.of(1970, 1, 1));
        card2.setProcedures(new ArrayList<>());
        when(cardRepository.save(Mockito.<Card>any())).thenReturn(card2);
        when(cardRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        LocalDate preliminaryDiagnosisDate = LocalDate.of(1970, 1, 1);
        LocalDate finalDiagnosisDate = LocalDate.of(1970, 1, 1);
        CardResponseDto cardResponseDto = new CardResponseDto(1, 1, "42", "Preliminary Diagnosis", preliminaryDiagnosisDate,
                "Final Diagnosis", finalDiagnosisDate, "The characteristics of someone or something", new ArrayList<>());

        when(cardResponseMapper.toDto(Mockito.<Card>any())).thenReturn(cardResponseDto);

        // Act
        CardResponseDto actualCardById = cardServiceImpl.getCardById(1);

        // Assert
        verify(cardResponseMapper).toDto(isA(Card.class));
        verify(cardRepository).findById(eq(1));
        verify(cardRepository).save(isA(Card.class));
        assertSame(cardResponseDto, actualCardById);
    }

    @Test
    void testSaveCard() throws EntityCreatedException {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setNameCategory("Name Category");

        Specialization specialization = new Specialization();
        specialization.setId(1);
        specialization.setNameSpecialization("Name Specialization");

        Employee employee = new Employee();
        employee.setCategory(category);
        employee.setEmail("jane.doe@example.org");
        employee.setId(1);
        employee.setName("Name");
        employee.setPassword("iloveyou");
        employee.setRoles(new ArrayList<>());
        employee.setSecondName("Second Name");
        employee.setSpecialization(specialization);
        employee.setSurname("Doe");

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

        Card card = new Card();
        card.setDescription("The characteristics of someone or something");
        card.setEmployee(employee);
        card.setFinalDiagnosis("Final Diagnosis");
        card.setFinalDiagnosisDate(LocalDate.of(1970, 1, 1));
        card.setId(1);
        card.setPatient(patient);
        card.setPreliminaryDiagnosis("Preliminary Diagnosis");
        card.setPreliminaryDiagnosisDate(LocalDate.of(1970, 1, 1));
        card.setProcedures(new ArrayList<>());
        when(cardRepository.save(Mockito.<Card>any())).thenReturn(card);
        when(cardRepository.findCardByPatient_IdAndFinalDiagnosisIsNull(Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        Category category2 = new Category();
        category2.setId(1);
        category2.setNameCategory("Name Category");

        Specialization specialization2 = new Specialization();
        specialization2.setId(1);
        specialization2.setNameSpecialization("Name Specialization");

        Employee employee2 = new Employee();
        employee2.setCategory(category2);
        employee2.setEmail("jane.doe@example.org");
        employee2.setId(1);
        employee2.setName("Name");
        employee2.setPassword("iloveyou");
        employee2.setRoles(new ArrayList<>());
        employee2.setSecondName("Second Name");
        employee2.setSpecialization(specialization2);
        employee2.setSurname("Doe");

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

        Card card2 = new Card();
        card2.setDescription("The characteristics of someone or something");
        card2.setEmployee(employee2);
        card2.setFinalDiagnosis("Final Diagnosis");
        card2.setFinalDiagnosisDate(LocalDate.of(1970, 1, 1));
        card2.setId(1);
        card2.setPatient(patient2);
        card2.setPreliminaryDiagnosis("Preliminary Diagnosis");
        card2.setPreliminaryDiagnosisDate(LocalDate.of(1970, 1, 1));
        card2.setProcedures(new ArrayList<>());
        LocalDate preliminaryDiagnosisDate = LocalDate.of(1970, 1, 1);
        LocalDate finalDiagnosisDate = LocalDate.of(1970, 1, 1);
        EmployeeGetCardDto employee3 = new EmployeeGetCardDto(1, "Doe", "Name", "Second Name");

        CardCreatorDto cardCreatorDto = new CardCreatorDto(1, "Preliminary Diagnosis", preliminaryDiagnosisDate,
                "Final Diagnosis", finalDiagnosisDate, "The characteristics of someone or something", employee3,
                new PatientCardDto("42", "Name", "Doe", "Second Name"));

        when(cardCreatorMapper.toDto(Mockito.<Card>any())).thenReturn(cardCreatorDto);
        when(cardCreatorMapper.toEntity(Mockito.<CardCreatorDto>any())).thenReturn(card2);

        Patient patient3 = new Patient();
        patient3.setAddress("42 Main St");
        patient3.setCards(new ArrayList<>());
        patient3.setDob(LocalDate.of(1970, 1, 1));
        patient3.setEmail("jane.doe@example.org");
        patient3.setId("42");
        patient3.setName("Name");
        patient3.setPhone("6625550144");
        patient3.setSecondName("Second Name");
        patient3.setStatus((byte) 'A');
        patient3.setSurname("Doe");
        Optional<Patient> ofResult = Optional.of(patient3);
        when(patientRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        LocalDate preliminaryDiagnosisDate2 = LocalDate.of(1970, 1, 1);
        LocalDate finalDiagnosisDate2 = LocalDate.of(1970, 1, 1);
        EmployeeGetCardDto employee4 = new EmployeeGetCardDto(1, "Doe", "Name", "Second Name");

        // Act
        CardCreatorDto actualSaveCardResult = cardServiceImpl
                .saveCard(new CardCreatorDto(1, "Preliminary Diagnosis", preliminaryDiagnosisDate2, "Final Diagnosis",
                        finalDiagnosisDate2, "The characteristics of someone or something", employee4,
                        new PatientCardDto("42", "Name", "Doe", "Second Name")));

        // Assert
        verify(cardCreatorMapper).toDto(isA(Card.class));
        verify(cardCreatorMapper).toEntity(isA(CardCreatorDto.class));
        verify(cardRepository).findCardByPatient_IdAndFinalDiagnosisIsNull(eq("42"));
        verify(patientRepository).findById(eq("42"));
        verify(cardRepository).save(isA(Card.class));
        assertSame(cardCreatorDto, actualSaveCardResult);
    }

    @Test
    void testSaveCard2() throws EntityCreatedException {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setNameCategory("Name Category");

        Specialization specialization = new Specialization();
        specialization.setId(1);
        specialization.setNameSpecialization("Name Specialization");

        Employee employee = new Employee();
        employee.setCategory(category);
        employee.setEmail("jane.doe@example.org");
        employee.setId(1);
        employee.setName("Name");
        employee.setPassword("iloveyou");
        employee.setRoles(new ArrayList<>());
        employee.setSecondName("Second Name");
        employee.setSpecialization(specialization);
        employee.setSurname("Doe");

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

        Card card = new Card();
        card.setDescription("The characteristics of someone or something");
        card.setEmployee(employee);
        card.setFinalDiagnosis("Final Diagnosis");
        card.setFinalDiagnosisDate(LocalDate.of(1970, 1, 1));
        card.setId(1);
        card.setPatient(patient);
        card.setPreliminaryDiagnosis("Preliminary Diagnosis");
        card.setPreliminaryDiagnosisDate(LocalDate.of(1970, 1, 1));
        card.setProcedures(new ArrayList<>());

        ArrayList<Card> cardList = new ArrayList<>();
        cardList.add(card);
        when(cardRepository.findCardByPatient_IdAndFinalDiagnosisIsNull(Mockito.<String>any())).thenReturn(cardList);
        LocalDate preliminaryDiagnosisDate = LocalDate.of(1970, 1, 1);
        LocalDate finalDiagnosisDate = LocalDate.of(1970, 1, 1);
        EmployeeGetCardDto employee2 = new EmployeeGetCardDto(1, "Doe", "Name", "Second Name");

        // Act and Assert
        assertThrows(EntityCreatedException.class,
                () -> cardServiceImpl.saveCard(new CardCreatorDto(1, "Preliminary Diagnosis", preliminaryDiagnosisDate,
                        "Final Diagnosis", finalDiagnosisDate, "The characteristics of someone or something", employee2,
                        new PatientCardDto("42", "Name", "Doe", "Second Name"))));
        verify(cardRepository).findCardByPatient_IdAndFinalDiagnosisIsNull(eq("42"));
    }

    @Test
    void testUpdateCard() throws OperationException {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setNameCategory("Name Category");

        Specialization specialization = new Specialization();
        specialization.setId(1);
        specialization.setNameSpecialization("Name Specialization");

        Employee employee = new Employee();
        employee.setCategory(category);
        employee.setEmail("jane.doe@example.org");
        employee.setId(1);
        employee.setName("Name");
        employee.setPassword("iloveyou");
        employee.setRoles(new ArrayList<>());
        employee.setSecondName("Second Name");
        employee.setSpecialization(specialization);
        employee.setSurname("Doe");

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

        Card card = new Card();
        card.setDescription("The characteristics of someone or something");
        card.setEmployee(employee);
        card.setFinalDiagnosis("Final Diagnosis");
        card.setFinalDiagnosisDate(LocalDate.of(1970, 1, 1));
        card.setId(1);
        card.setPatient(patient);
        card.setPreliminaryDiagnosis("Preliminary Diagnosis");
        card.setPreliminaryDiagnosisDate(LocalDate.of(1970, 1, 1));
        card.setProcedures(new ArrayList<>());
        Optional<Card> ofResult = Optional.of(card);
        when(cardRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        LocalDate preliminaryDiagnosisDate = LocalDate.of(1970, 1, 1);
        LocalDate finalDiagnosisDate = LocalDate.of(1970, 1, 1);
        EmployeeGetCardDto employee2 = new EmployeeGetCardDto(1, "Doe", "Name", "Second Name");

        // Act and Assert
        assertThrows(OperationException.class,
                () -> cardServiceImpl.updateCard(new CardCreatorDto(1, "Preliminary Diagnosis", preliminaryDiagnosisDate,
                        "Final Diagnosis", finalDiagnosisDate, "The characteristics of someone or something", employee2,
                        new PatientCardDto("42", "Name", "Doe", "Second Name"))));
        verify(cardRepository).findById(eq(1));
    }

    @Test
    void testUpdateCard2() throws OperationException {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setNameCategory("Name Category");

        Specialization specialization = new Specialization();
        specialization.setId(1);
        specialization.setNameSpecialization("Name Specialization");

        Employee employee = new Employee();
        employee.setCategory(category);
        employee.setEmail("jane.doe@example.org");
        employee.setId(1);
        employee.setName("Name");
        employee.setPassword("iloveyou");
        employee.setRoles(new ArrayList<>());
        employee.setSecondName("Second Name");
        employee.setSpecialization(specialization);
        employee.setSurname("Doe");

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

        Card card = new Card();
        card.setDescription("The characteristics of someone or something");
        card.setEmployee(employee);
        card.setFinalDiagnosis(null);
        card.setFinalDiagnosisDate(LocalDate.of(1970, 1, 1));
        card.setId(1);
        card.setPatient(patient);
        card.setPreliminaryDiagnosis("Preliminary Diagnosis");
        card.setPreliminaryDiagnosisDate(LocalDate.of(1970, 1, 1));
        card.setProcedures(new ArrayList<>());
        Optional<Card> ofResult = Optional.of(card);

        Category category2 = new Category();
        category2.setId(1);
        category2.setNameCategory("Name Category");

        Specialization specialization2 = new Specialization();
        specialization2.setId(1);
        specialization2.setNameSpecialization("Name Specialization");

        Employee employee2 = new Employee();
        employee2.setCategory(category2);
        employee2.setEmail("jane.doe@example.org");
        employee2.setId(1);
        employee2.setName("Name");
        employee2.setPassword("iloveyou");
        employee2.setRoles(new ArrayList<>());
        employee2.setSecondName("Second Name");
        employee2.setSpecialization(specialization2);
        employee2.setSurname("Doe");

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

        Card card2 = new Card();
        card2.setDescription("The characteristics of someone or something");
        card2.setEmployee(employee2);
        card2.setFinalDiagnosis("Final Diagnosis");
        card2.setFinalDiagnosisDate(LocalDate.of(1970, 1, 1));
        card2.setId(1);
        card2.setPatient(patient2);
        card2.setPreliminaryDiagnosis("Preliminary Diagnosis");
        card2.setPreliminaryDiagnosisDate(LocalDate.of(1970, 1, 1));
        card2.setProcedures(new ArrayList<>());
        when(cardRepository.save(Mockito.<Card>any())).thenReturn(card2);
        when(cardRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        LocalDate preliminaryDiagnosisDate = LocalDate.of(1970, 1, 1);
        LocalDate finalDiagnosisDate = LocalDate.of(1970, 1, 1);
        CardResponseDto cardResponseDto = new CardResponseDto(1, 1, "42", "Preliminary Diagnosis", preliminaryDiagnosisDate,
                "Final Diagnosis", finalDiagnosisDate, "The characteristics of someone or something", new ArrayList<>());

        when(cardResponseMapper.toDto(Mockito.<Card>any())).thenReturn(cardResponseDto);

        Category category3 = new Category();
        category3.setId(1);
        category3.setNameCategory("Name Category");

        Specialization specialization3 = new Specialization();
        specialization3.setId(1);
        specialization3.setNameSpecialization("Name Specialization");

        Employee employee3 = new Employee();
        employee3.setCategory(category3);
        employee3.setEmail("jane.doe@example.org");
        employee3.setId(1);
        employee3.setName("Name");
        employee3.setPassword("iloveyou");
        employee3.setRoles(new ArrayList<>());
        employee3.setSecondName("Second Name");
        employee3.setSpecialization(specialization3);
        employee3.setSurname("Doe");

        Patient patient3 = new Patient();
        patient3.setAddress("42 Main St");
        patient3.setCards(new ArrayList<>());
        patient3.setDob(LocalDate.of(1970, 1, 1));
        patient3.setEmail("jane.doe@example.org");
        patient3.setId("42");
        patient3.setName("Name");
        patient3.setPhone("6625550144");
        patient3.setSecondName("Second Name");
        patient3.setStatus((byte) 'A');
        patient3.setSurname("Doe");

        Card card3 = new Card();
        card3.setDescription("The characteristics of someone or something");
        card3.setEmployee(employee3);
        card3.setFinalDiagnosis("Final Diagnosis");
        card3.setFinalDiagnosisDate(LocalDate.of(1970, 1, 1));
        card3.setId(1);
        card3.setPatient(patient3);
        card3.setPreliminaryDiagnosis("Preliminary Diagnosis");
        card3.setPreliminaryDiagnosisDate(LocalDate.of(1970, 1, 1));
        card3.setProcedures(new ArrayList<>());
        when(cardCreatorMapper.partialUpdate(Mockito.<CardCreatorDto>any(), Mockito.<Card>any())).thenReturn(card3);
        LocalDate preliminaryDiagnosisDate2 = LocalDate.of(1970, 1, 1);
        LocalDate finalDiagnosisDate2 = LocalDate.of(1970, 1, 1);
        EmployeeGetCardDto employee4 = new EmployeeGetCardDto(1, "Doe", "Name", "Second Name");

        // Act
        CardResponseDto actualUpdateCardResult = cardServiceImpl
                .updateCard(new CardCreatorDto(1, "Preliminary Diagnosis", preliminaryDiagnosisDate2, "Final Diagnosis",
                        finalDiagnosisDate2, "The characteristics of someone or something", employee4,
                        new PatientCardDto("42", "Name", "Doe", "Second Name")));

        // Assert
        verify(cardCreatorMapper).partialUpdate(isA(CardCreatorDto.class), isA(Card.class));
        verify(cardResponseMapper).toDto(isA(Card.class));
        verify(cardRepository).findById(eq(1));
        verify(cardRepository).save(isA(Card.class));
        assertSame(cardResponseDto, actualUpdateCardResult);
    }
}
