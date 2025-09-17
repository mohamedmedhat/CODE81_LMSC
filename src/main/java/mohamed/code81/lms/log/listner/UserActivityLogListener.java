package mohamed.code81.lms.log.listner;

import lombok.RequiredArgsConstructor;
import mohamed.code81.lms.log.UserActivityLogMapper;
import mohamed.code81.lms.log.UserActivityLogRepository;
import mohamed.code81.lms.log.dto.request.UserActivityLogRequestDto;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserActivityLogListener {
    private final UserActivityLogRepository userActivityLogRepository;
    private final UserActivityLogMapper userActivityLogMapper;

    @Async
    @EventListener
    public void handleUserActivityEvent(UserActivityLogRequestDto dto) {
        userActivityLogRepository.save(userActivityLogMapper.toActivityLog(dto));
    }
}
