package mohamed.code81.lms.log;

import mohamed.code81.lms.auth.user.User;
import mohamed.code81.lms.common.response.PageableResponseDto;
import mohamed.code81.lms.log.dto.request.UserActivityLogRequestDto;
import mohamed.code81.lms.log.dto.response.UserActivityLogResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class UserActivityLogMapper {
    public UserActivityLog toActivityLog(UserActivityLogRequestDto dto) {
        return UserActivityLog.builder()
                .user(dto.user())
                .action(dto.action())
                .build();
    }

    public UserActivityLogResponseDto toUserActivityLogResponse(UserActivityLog userActivityLog) {
        return new UserActivityLogResponseDto(
                userActivityLog.getId(),
                userActivityLog.getUser().getId(),
                userActivityLog.getAction(),
                userActivityLog.getCreatedAt()
        );
    }


    public PageableResponseDto<UserActivityLogResponseDto> toUserActivityLogPageableResponse(Page<UserActivityLog> logs) {
        return new PageableResponseDto<>(
                logs.stream()
                        .map(this::toUserActivityLogResponse)
                        .toList(),
                logs.getNumber(),
                logs.getSize(),
                logs.getTotalElements(),
                logs.getTotalPages()

        );
    }
}
