package by.asb.traningprogect.hospital.service;

import by.asb.traningprogect.hospital.exception.EntityCreatedException;
import by.asb.traningprogect.hospital.model.dto.EmployeeChangePasswordDto;
import by.asb.traningprogect.hospital.model.dto.EmployeeDto;
import by.asb.traningprogect.hospital.model.dto.EmployeeRequestDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.nio.file.AccessDeniedException;


public interface EmployeeService extends UserDetailsService {

    EmployeeDto getEmployeeById(Integer employeeId);
    EmployeeDto saveEmployee(EmployeeDto employeeCreatorDto) throws EntityCreatedException;
    void changePasswordUser(EmployeeChangePasswordDto employeeAuthDto, Integer employeeId) throws AccessDeniedException;
    EmployeeRequestDto updateEmployee(EmployeeRequestDto employeeDto) throws AccessDeniedException;
}
