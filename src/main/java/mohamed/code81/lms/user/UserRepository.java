package mohamed.code81.lms.user;

import mohamed.code81.lms.user.projection.EmailAndPasswordAndRoleProjection;
import mohamed.code81.lms.user.projection.NameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("""
        SELECT new mohamed.code81.lms.user.projection.EmailAndPasswordAndRoleProjection(u.email, u.password, u.role)
        FROM User u
        WHERE u.email = :email
        """)
    Optional<EmailAndPasswordAndRoleProjection> findEmailAndPasswordAndRoleByEmail(@Param("email") String email);

    @Query("""
            SELECT new mohamed.code81.lms.user.projection.NameProjection(u.name)
            From User u
            Where u.email = :email
            """)
    NameProjection findNameByEmail(@Param("email") String email);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
