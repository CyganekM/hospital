package by.asb.traningprogect.hospital.model.mapper;

import by.asb.traningprogect.hospital.model.dto.CategoryDto;
import by.asb.traningprogect.hospital.model.entity.Category;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    Category toEntity(CategoryDto categoryDto);

    CategoryDto toDto(Category category);
}