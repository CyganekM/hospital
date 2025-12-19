package by.asb.traningprogect.hospital.service.impl;

import by.asb.traningprogect.hospital.model.dto.ProcedureDto;
import by.asb.traningprogect.hospital.model.dto.ProcedureRequestDto;
import by.asb.traningprogect.hospital.model.entity.Procedure;
import by.asb.traningprogect.hospital.model.mapper.ProcedureMapper;
import by.asb.traningprogect.hospital.model.mapper.ProcedureRequestMapper;
import by.asb.traningprogect.hospital.reposotory.EmployeeRepository;
import by.asb.traningprogect.hospital.reposotory.ProcedureRepository;
import by.asb.traningprogect.hospital.reposotory.ProcedureTypeRepository;
import by.asb.traningprogect.hospital.service.ProcedureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProcedureServiceImpl implements ProcedureService {
    private final static String TYPE_PROCEDURE = "операция";
    private final ProcedureMapper procedureMapper;
    private final ProcedureRepository procedureRepository;
    private final ProcedureRequestMapper procedureRequestMapper;
    private final EmployeeRepository employeeRepository;
    private final ProcedureTypeRepository procedureTypeRepository;


    @Override
    public ProcedureDto create(ProcedureRequestDto procedureRequestDto) {
        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var typeProcedure = procedureTypeRepository.getReferenceById(procedureRequestDto.procedureTypeId());
        if (typeProcedure.getName().equals(TYPE_PROCEDURE) && userDetails.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("ROLE_DOCTOR"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Только пользователь с ролью DOCTOR может назначать операции");
        }
        var procedure = procedureRequestMapper.toEntity(procedureRequestDto);
        var employee = employeeRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        procedure.setEmployee(employee);
        procedureRepository.save(procedure);
        var procedureDto = procedureMapper.toDto(procedure);
        return  procedureDto;
    }

    @Override
    public ProcedureDto getProcedureById(Integer procedureID) {
        var procedure = procedureRepository.findById(procedureID);
        var procedureDto = procedureMapper.toDto(procedure.orElseThrow());
        return procedureDto;
    }
}
