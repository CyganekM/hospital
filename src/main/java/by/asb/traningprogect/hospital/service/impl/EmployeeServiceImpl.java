package by.asb.traningprogect.hospital.service.impl;

import by.asb.traningprogect.hospital.exception.EntityCreatedException;
import by.asb.traningprogect.hospital.model.dto.EmployeeChangePasswordDto;
import by.asb.traningprogect.hospital.model.dto.EmployeeDto;
import by.asb.traningprogect.hospital.model.entity.Employee;
import by.asb.traningprogect.hospital.model.dto.EmployeeRequestDto;
import by.asb.traningprogect.hospital.model.mapper.EmployeeMapper;
import by.asb.traningprogect.hospital.model.mapper.EmployeeRequestMapper;
import by.asb.traningprogect.hospital.reposotory.EmployeeRepository;
import by.asb.traningprogect.hospital.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final EmployeeRequestMapper employeeRequestMapper;

    @Override
    public EmployeeDto getEmployeeById(Integer employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        return employeeMapper.toDto(optionalEmployee.orElseThrow());
        }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeCreatorDto) throws EntityCreatedException {
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(employeeCreatorDto.getEmail());
        var passwordEncoder = new BCryptPasswordEncoder();
        if (employeeOptional.isPresent()) {
            throw new EntityCreatedException("The employee with email = %s is already present in the system".formatted(employeeCreatorDto.getEmail()));
        }
        if (employeeCreatorDto.getId() != null && employeeRepository.existsById(employeeCreatorDto.getId())){
            throw new EntityCreatedException("The employee with id = %s is already present in the system".formatted(employeeCreatorDto.getId()));
        }
        String passwordNew = passwordEncoder.encode(employeeCreatorDto.getPassword());
        var employee = employeeMapper.toEntity(employeeCreatorDto);
        employee.setPassword(passwordNew);
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    @Override
    public void changePasswordUser(EmployeeChangePasswordDto employeeDto, Integer employeeId) throws AccessDeniedException {
        var passwordEncoder = new BCryptPasswordEncoder();
        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Попытка изменения пароля у пользоватетя c id {}", employeeId);
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                new EntityNotFoundException("Employee with id = " + employeeId + " not found"));
        if (userDetails.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN")) ||
                userDetails.getUsername().equals(employee.getEmail()) && employeeDto.oldPassword() != null && passwordEncoder.matches(employeeDto.oldPassword(), employee.getPassword()) ) {
            employee.setPassword(new BCryptPasswordEncoder().encode(employeeDto.newPassword()));
            employeeRepository.save(employee);
            log.info("Пароль изменён успешно пользователем {}", userDetails.getUsername());
        } else {
            throw new AccessDeniedException("У вас нет доступа к изменению пароля");
        }
    }

    @Override
    public EmployeeRequestDto updateEmployee(EmployeeRequestDto employeeDto) throws AccessDeniedException {
        var employee = employeeRepository.findById(employeeDto.getId()).orElseThrow(() ->
                new EntityNotFoundException("Employee with id = " + employeeDto.getId() + " not found"));
        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ( employee.getEmail().equals(userDetails.getUsername()) || userDetails.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            employeeRequestMapper.partialUpdate(employeeDto,employee);
            return employeeRequestMapper.toDto(employeeRepository.save(employee));
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Только пользоватьель с ролью ADMIN может изменять учётные данные других пользователей");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalEmployee =employeeRepository.findByEmail(username);
            return optionalEmployee.orElseThrow(() ->
                    new UsernameNotFoundException("user not found"));
        }
}
