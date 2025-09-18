package mohamed.code81.lms.library.book.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record BookRequestDto(
        @NotBlank
        String title,
        @NotBlank
        String isbn,
        @NotBlank
        String edition,
        @NotBlank
        String lang,
        @NotNull
        Integer publicationYear,
        String summary,
        String coverImageUrl,
        @NotNull
        List<String> categories,
        List<UUID> authorsId
) {
}
