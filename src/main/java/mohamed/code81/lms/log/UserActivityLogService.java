package mohamed.code81.lms.log;

import mohamed.code81.lms.common.response.PageableResponseDto;
import mohamed.code81.lms.log.dto.request.UserActivityLogRequestDto;
import mohamed.code81.lms.log.dto.response.UserActivityLogResponseDto;

import java.util.UUID;

public interface UserActivityLogService {
    UserActivityLogResponseDto createActivityLog(UserActivityLogRequestDto dto);
    UserActivityLogResponseDto getActivityLog(UUID id);
    PageableResponseDto<UserActivityLogResponseDto> getActivityLogs(int page, int size, UUID userId);
    void deleteActivityLog(UUID id);
}
