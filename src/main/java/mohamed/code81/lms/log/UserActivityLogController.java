package mohamed.code81.lms.log;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mohamed.code81.lms.common.response.PageableResponseDto;
import mohamed.code81.lms.log.dto.request.UserActivityLogRequestDto;
import mohamed.code81.lms.log.dto.response.UserActivityLogResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user_activity_logs")
public class UserActivityLogController {
    private final UserActivityLogService userActivityLogService;

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<UserActivityLogResponseDto> getUserActivityLog(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(userActivityLogService.getActivityLog(id));
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<PageableResponseDto<UserActivityLogResponseDto>> getUserActivityLog(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("userId") UUID userId
    ) {
        return ResponseEntity.ok(userActivityLogService.getActivityLogs(page, size, userId));
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable("id") UUID id) {
        userActivityLogService.deleteActivityLog(id);
        return ResponseEntity.noContent().build();
    }
}
