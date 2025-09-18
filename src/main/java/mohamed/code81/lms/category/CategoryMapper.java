package mohamed.code81.lms.category;

import mohamed.code81.lms.category.dto.request.CategoryRequestDto;
import mohamed.code81.lms.category.dto.response.CategoryResponseDto;
import mohamed.code81.lms.common.response.PageableResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toCategory(CategoryRequestDto dto) {
        return Category.builder()
                .name(dto.name())
                .build();
    }

    public CategoryResponseDto categoryResponseDto(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getName()
        );
    }

    public PageableResponseDto<CategoryResponseDto> toPageableCategoryResponse(Page<Category> categories) {
        return new PageableResponseDto<>(
                categories.getContent().stream()
                        .map(this::categoryResponseDto)
                        .toList(),
                categories.getNumber(),
                categories.getSize(),
                categories.getTotalElements(),
                categories.getTotalPages()
        );
    }
}
