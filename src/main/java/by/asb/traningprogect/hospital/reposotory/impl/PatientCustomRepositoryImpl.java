package by.asb.traningprogect.hospital.reposotory.impl;

import by.asb.traningprogect.hospital.exception.OperationException;
import by.asb.traningprogect.hospital.model.Filter;
import by.asb.traningprogect.hospital.model.entity.Patient;
import by.asb.traningprogect.hospital.reposotory.PatientCustomRepository;
import by.asb.traningprogect.hospital.reposotory.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
@RequiredArgsConstructor
public class PatientCustomRepositoryImpl implements PatientCustomRepository {

    private final PatientRepository patientRepository;

    private Specification<Patient> createSpecification(Filter<?> filter) throws OperationException {

        return switch (filter.getQueryOperator()) {
            case EQUALS -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(filter.getKey()), filter.getValue());
            case NOT_EQ -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.notEqual(root.get(filter.getKey()), filter.getValue());
            case GREATER_THAN -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThan(root.get(filter.getKey()), (LocalDate) filter.getValue());
            case LESS_THAN -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThan(root.get(filter.getKey()), (LocalDate) filter.getValue());
            case LIKE -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get(filter.getKey()), "%" + filter.getValue() + "%");
            case IN -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.in(root.get(filter.getKey())).value(filter.getValues());
            default -> throw new OperationException("Operation not supported yet");
        };
    }

    private Specification<Patient> getSpecificationFromFilters(List<Filter<?>> filterList) throws OperationException {
        Specification<Patient> specification = where(createSpecification(filterList.remove(0)));
        for (Filter<?> filter : filterList) {
            specification = specification.and(createSpecification(filter));
        }
        return specification;
    }

    @Override
    public List<Patient> getQueryResult(List<Filter<?>> filterList, Sort sort) throws OperationException {
        if (!filterList.isEmpty()) {
            Specification<Patient> advertisementSpecification = getSpecificationFromFilters(filterList);
            return patientRepository.findAll(advertisementSpecification, sort);
        } else {
            return patientRepository.findAll(sort);
        }
    }
}
