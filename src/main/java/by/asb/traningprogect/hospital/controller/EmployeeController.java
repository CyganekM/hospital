package by.asb.traningprogect.hospital.controller;

import by.asb.traningprogect.hospital.exception.EntityCreatedException;
import by.asb.traningprogect.hospital.model.dto.EmployeeChangePasswordDto;
import by.asb.traningprogect.hospital.model.dto.EmployeeDto;
import by.asb.traningprogect.hospital.model.dto.EmployeeRequestDto;
import by.asb.traningprogect.hospital.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary ="Найти работника по идентификатору")
    @GetMapping("/{employeeId}")
    public EmployeeDto getEmployeeById(@PathVariable Integer employeeId){
        return employeeService.getEmployeeById(employeeId);
    }

    @Operation(summary ="Добавить работника")
    @PostMapping
    @Secured("ROLE_ADMIN")
    public  EmployeeDto saveEmployee(@Valid @RequestBody EmployeeDto employeeCreatorDto) throws EntityCreatedException {
        return employeeService.saveEmployee(employeeCreatorDto);
    }

    @Operation(summary ="Изменить пароль")
    @PutMapping("{employeeId}/change_password")
    public void changePassword(@PathVariable Integer employeeId, @Valid @RequestBody EmployeeChangePasswordDto employeeChangePasswordDto) throws AccessDeniedException {
        employeeService.changePasswordUser(employeeChangePasswordDto, employeeId);
    }

    @Operation(summary ="Обновить сотрудника")
    @PutMapping
    public EmployeeRequestDto updateEmployee(@Valid @RequestBody EmployeeRequestDto employeeDto) throws AccessDeniedException {
        return employeeService.updateEmployee(employeeDto);
    }
}

