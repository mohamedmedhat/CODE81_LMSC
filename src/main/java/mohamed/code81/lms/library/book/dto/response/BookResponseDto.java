package mohamed.code81.lms.library.book.dto.response;

import mohamed.code81.lms.category.dto.response.CategoryResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record BookResponseDto(
        UUID id,
        String title,
        String isbn,
        String edition,
        String lang,
        Integer publicationYear,
        String summary,
        List<CategoryResponseDto> categories,
        String coverImageUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
