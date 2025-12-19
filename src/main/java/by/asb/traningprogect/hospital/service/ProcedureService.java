package by.asb.traningprogect.hospital.service;

import by.asb.traningprogect.hospital.model.dto.ProcedureDto;
import by.asb.traningprogect.hospital.model.dto.ProcedureRequestDto;

public interface ProcedureService {

    ProcedureDto create(ProcedureRequestDto procedureDto);
    ProcedureDto getProcedureById(Integer procedureId);
}
