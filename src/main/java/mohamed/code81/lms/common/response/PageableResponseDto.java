package mohamed.code81.lms.common.response;

import java.util.List;

public record PageableResponseDto<T>(
        List<T> content,
        int page,
        int size,
        Long totalElements,
        int totalPages
) {
}