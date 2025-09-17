package mohamed.code81.lms.common.response;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        int value,
        String msg,
        LocalDateTime date
) {
}
