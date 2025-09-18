package mohamed.code81.lms.library.borrow;

import jakarta.persistence.*;
import lombok.*;
import mohamed.code81.lms.library.book.Book;
import mohamed.code81.lms.user.User;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "borrowings")
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private User member;

    @Column(nullable = false)
    private LocalDate borrowDate;

    private LocalDate returnDate;

    private LocalDate dueDate;

    private Boolean returned = false;
}
