package mohamed.code81.lms.log;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserActivityLogRepository extends JpaRepository<UserActivityLog, UUID> {

    @Query("""
            SELECT u FROM UserActivityLog u
            WHERE u.user.id = :userId
            """)
    Page<UserActivityLog> findAllByUserId(@Param("userId") UUID userId, Pageable pageable);
}
