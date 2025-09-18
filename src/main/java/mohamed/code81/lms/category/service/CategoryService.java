package mohamed.code81.lms.category.service;

import mohamed.code81.lms.category.dto.request.CategoryRequestDto;
import mohamed.code81.lms.category.dto.response.CategoryResponseDto;
import mohamed.code81.lms.common.response.PageableResponseDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryResponseDto> createCategories(List<CategoryRequestDto> dto);
    void deleteCategories(List<UUID> ids);
    PageableResponseDto<CategoryResponseDto> getCategories(int page, int size);
}
