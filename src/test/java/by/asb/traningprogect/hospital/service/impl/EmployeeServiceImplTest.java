package by.asb.traningprogect.hospital.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.asb.traningprogect.hospital.exception.EntityCreatedException;
import by.asb.traningprogect.hospital.model.dto.CategoryDto;
import by.asb.traningprogect.hospital.model.dto.EmployeeChangePasswordDto;
import by.asb.traningprogect.hospital.model.dto.EmployeeDto;
import by.asb.traningprogect.hospital.model.dto.SpecializationDto;
import by.asb.traningprogect.hospital.model.entity.Category;
import by.asb.traningprogect.hospital.model.entity.Employee;
import by.asb.traningprogect.hospital.model.dto.EmployeeRequestDto;
import by.asb.traningprogect.hospital.model.entity.Specialization;
import by.asb.traningprogect.hospital.model.mapper.EmployeeMapper;
import by.asb.traningprogect.hospital.model.mapper.EmployeeRequestMapper;
import by.asb.traningprogect.hospital.reposotory.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EmployeeServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class EmployeeServiceImplTest {
    @MockBean
    private EmployeeMapper employeeMapper;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private EmployeeRequestMapper employeeRequestMapper;

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;


    @Test
    void testGetEmployeeById() {
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
        Optional<Employee> ofResult = Optional.of(employee);
        when(employeeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        SpecializationDto specialization2 = new SpecializationDto(1, "Name Specialization");

        CategoryDto category2 = new CategoryDto(1, "Name Category");

        EmployeeDto employeeDto = new EmployeeDto(1, specialization2, category2, "Doe", "Name", "Second Name",
                "jane.doe@example.org", "iloveyou", new ArrayList<>());

        when(employeeMapper.toDto(Mockito.<Employee>any())).thenReturn(employeeDto);

        // Act
        EmployeeDto actualEmployeeById = employeeServiceImpl.getEmployeeById(1);

        // Assert
        verify(employeeMapper).toDto(isA(Employee.class));
        verify(employeeRepository).findById(eq(1));
        assertSame(employeeDto, actualEmployeeById);
    }

    @Test
    void testGetEmployeeById2() {
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
        Optional<Employee> ofResult = Optional.of(employee);
        when(employeeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        when(employeeMapper.toDto(Mockito.<Employee>any())).thenThrow(new UsernameNotFoundException("Msg"));

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> employeeServiceImpl.getEmployeeById(1));
        verify(employeeMapper).toDto(isA(Employee.class));
        verify(employeeRepository).findById(eq(1));
    }

    @Test
    void testSaveEmployee() throws EntityCreatedException {
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
        Optional<Employee> ofResult = Optional.of(employee);
        when(employeeRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        SpecializationDto specialization2 = new SpecializationDto(1, "Name Specialization");

        CategoryDto category2 = new CategoryDto(1, "Name Category");

        // Act and Assert
        assertThrows(EntityCreatedException.class,
                () -> employeeServiceImpl.saveEmployee(new EmployeeDto(1, specialization2, category2, "Doe", "Name",
                        "Second Name", "jane.doe@example.org", "iloveyou", new ArrayList<>())));
        verify(employeeRepository).findByEmail(eq("jane.doe@example.org"));
    }

    @Test
    void testSaveEmployee2() throws EntityCreatedException {
        // Arrange
        when(employeeRepository.existsById(Mockito.<Integer>any())).thenReturn(true);
        Optional<Employee> emptyResult = Optional.empty();
        when(employeeRepository.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);
        SpecializationDto specialization = new SpecializationDto(1, "Name Specialization");

        CategoryDto category = new CategoryDto(1, "Name Category");

        // Act and Assert
        assertThrows(EntityCreatedException.class, () -> employeeServiceImpl.saveEmployee(new EmployeeDto(1, specialization,
                category, "Doe", "Name", "Second Name", "jane.doe@example.org", "iloveyou", new ArrayList<>())));
        verify(employeeRepository).findByEmail(eq("jane.doe@example.org"));
        verify(employeeRepository).existsById(eq(1));
    }

    @Test
    void testSaveEmployee3() throws EntityCreatedException {
        // Arrange
        when(employeeRepository.findByEmail(Mockito.<String>any()))
                .thenThrow(new EntityNotFoundException("An error occurred"));
        SpecializationDto specialization = new SpecializationDto(1, "Name Specialization");

        CategoryDto category = new CategoryDto(1, "Name Category");

        // Act and Assert
        assertThrows(EntityNotFoundException.class,
                () -> employeeServiceImpl.saveEmployee(new EmployeeDto(1, specialization, category, "Doe", "Name",
                        "Second Name", "jane.doe@example.org", "iloveyou", new ArrayList<>())));
        verify(employeeRepository).findByEmail(eq("jane.doe@example.org"));
    }

    @Test
    void testUpdateEmployee() throws AccessDeniedException {
        // Arrange
        Optional<Employee> emptyResult = Optional.empty();
        when(employeeRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        SpecializationDto specialization = new SpecializationDto(1, "Name Specialization");

        CategoryDto category = new CategoryDto(1, "Name Category");

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> employeeServiceImpl.updateEmployee(new EmployeeRequestDto(1,
                specialization, category, "Doe", "Name", "Second Name", new ArrayList<>(), "jane.doe@example.org")));
        verify(employeeRepository).findById(eq(1));
    }

    @Test
    void testUpdateEmployee2() throws AccessDeniedException {
        // Arrange
        when(employeeRepository.findById(Mockito.<Integer>any())).thenThrow(new UsernameNotFoundException("Msg"));
        SpecializationDto specialization = new SpecializationDto(1, "Name Specialization");

        CategoryDto category = new CategoryDto(1, "Name Category");

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> employeeServiceImpl.updateEmployee(new EmployeeRequestDto(1,
                specialization, category, "Doe", "Name", "Second Name", new ArrayList<>(), "jane.doe@example.org")));
        verify(employeeRepository).findById(eq(1));
    }

    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
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
        Optional<Employee> ofResult = Optional.of(employee);
        when(employeeRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        UserDetails actualLoadUserByUsernameResult = employeeServiceImpl.loadUserByUsername("janedoe");

        // Assert
        verify(employeeRepository).findByEmail(eq("janedoe"));
        assertSame(employee, actualLoadUserByUsernameResult);
    }

    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        // Arrange
        Optional<Employee> emptyResult = Optional.empty();
        when(employeeRepository.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> employeeServiceImpl.loadUserByUsername("janedoe"));
        verify(employeeRepository).findByEmail(eq("janedoe"));
    }

    @Test
    void testLoadUserByUsername3() throws UsernameNotFoundException {
        // Arrange
        when(employeeRepository.findByEmail(Mockito.<String>any()))
                .thenThrow(new UsernameNotFoundException("user not found"));

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> employeeServiceImpl.loadUserByUsername("janedoe"));
        verify(employeeRepository).findByEmail(eq("janedoe"));
    }
}
