package mohamed.code81.lms.user;

import jakarta.persistence.*;
import lombok.*;
import mohamed.code81.lms.library.book.Book;
import mohamed.code81.lms.user.enums.UserRole;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.MEMBER;

    @Column(nullable = false)
    private Boolean active = Boolean.TRUE;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private List<Book> authoredBooks;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
