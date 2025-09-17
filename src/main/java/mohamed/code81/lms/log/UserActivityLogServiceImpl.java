package mohamed.code81.lms.log;

import lombok.RequiredArgsConstructor;
import mohamed.code81.lms.common.response.PageableResponseDto;
import mohamed.code81.lms.log.dto.request.UserActivityLogRequestDto;
import mohamed.code81.lms.log.dto.response.UserActivityLogResponseDto;
import mohamed.code81.lms.log.exception.UserActivityLogNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserActivityLogServiceImpl implements UserActivityLogService {
    private final UserActivityLogRepository userActivityLogRepository;
    private final UserActivityLogMapper userActivityLogMapper;

    @Override
    public UserActivityLogResponseDto createActivityLog(UserActivityLogRequestDto dto) {
        UserActivityLog userActivityLog = userActivityLogMapper.toActivityLog(dto);
        return userActivityLogMapper.toUserActivityLogResponse(
                userActivityLogRepository.save(userActivityLog)
        );
    }

    @Override
    public UserActivityLogResponseDto getActivityLog(UUID id) {
        return userActivityLogMapper.toUserActivityLogResponse(getById(id));
    }

    @Override
    public PageableResponseDto<UserActivityLogResponseDto> getActivityLogs(int page, int size, UUID userId) {
        return userActivityLogMapper.toUserActivityLogPageableResponse(
                userActivityLogRepository.findAllByUserId(userId, PageRequest.of(page, size))
        );
    }

    @Override
    public void deleteActivityLog(UUID id) {
        userActivityLogRepository.delete(getById(id));
    }

    private UserActivityLog getById(UUID id){
        return userActivityLogRepository.findById(id)
                .orElseThrow(() -> new UserActivityLogNotFoundException("user activity log with id: "+id+" not found"));
    }
}
