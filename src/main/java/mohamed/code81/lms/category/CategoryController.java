package mohamed.code81.lms.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mohamed.code81.lms.category.dto.request.CategoryRequestDto;
import mohamed.code81.lms.category.dto.response.CategoryResponseDto;
import mohamed.code81.lms.category.service.CategoryService;
import mohamed.code81.lms.common.response.PageableResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<List<CategoryResponseDto>> createCategories(
            @RequestBody @Valid List<CategoryRequestDto> requestDtos) {

        List<CategoryResponseDto> createdCategories = categoryService.createCategories(requestDtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategories);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCategories(@RequestBody List<UUID> ids) {
        categoryService.deleteCategories(ids);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageableResponseDto<CategoryResponseDto>> getCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageableResponseDto<CategoryResponseDto> response = categoryService.getCategories(page, size);
        return ResponseEntity.ok(response);
    }
}
