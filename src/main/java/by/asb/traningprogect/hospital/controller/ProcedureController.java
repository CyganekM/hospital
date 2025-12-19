package by.asb.traningprogect.hospital.controller;

import by.asb.traningprogect.hospital.model.dto.ProcedureDto;
import by.asb.traningprogect.hospital.model.dto.ProcedureRequestDto;
import by.asb.traningprogect.hospital.service.ProcedureService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/procedure")
@RequiredArgsConstructor
public class ProcedureController {

    private final ProcedureService procedureService;

    @Operation(summary ="Добавить процедуру")
    @PostMapping
    public ProcedureDto create(@RequestBody ProcedureRequestDto procedureDto){

        return procedureService.create(procedureDto);
    }


    @Operation(summary ="Поиск процедуры по id")
    @GetMapping("/{procedureId}")
    public ProcedureDto getProcedureById(@PathVariable Integer procedureId){
        return procedureService.getProcedureById(procedureId);
    }
}

