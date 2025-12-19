package by.asb.traningprogect.hospital.reposotory;


import by.asb.traningprogect.hospital.exception.OperationException;
import by.asb.traningprogect.hospital.model.Filter;
import by.asb.traningprogect.hospital.model.entity.Patient;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PatientCustomRepository {
    List<Patient> getQueryResult(List<Filter<?>> filterDtoList, Sort sort) throws OperationException;
}
