package by.asb.traningprogect.hospital.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Filter<T> {

    private String key;
    private QueryOperator queryOperator;
    private T value;
    private List<T> values;
}
