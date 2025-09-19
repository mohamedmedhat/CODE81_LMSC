-- ==== USERS ====
CREATE UNIQUE INDEX idx_users_email ON users (email);

CREATE INDEX idx_users_role ON users (role);

-- ==== BOOKS ====
CREATE UNIQUE INDEX idx_books_isbn ON books (isbn);

CREATE INDEX idx_books_title_lower ON books (LOWER(title));

-- ==== BORROWINGS ====

CREATE INDEX idx_borrowings_book_id ON borrowings (book_id);
CREATE INDEX idx_borrowings_member_id ON borrowings (member_id);

-- Filter overdue/active loans quickly
CREATE INDEX idx_borrowings_due_date ON borrowings (due_date);
CREATE INDEX idx_borrowings_returned ON borrowings (returned);

-- =================================================================================
CREATE OR REPLACE VIEW vw_overdue_borrowings AS
SELECT
    br.id AS borrowing_id,
    u.name AS member_name,
    b.title AS book_title,
    br.due_date,
    CURRENT_DATE - br.due_date AS days_overdue
FROM borrowings br
JOIN users u ON br.member_id = u.id
JOIN books b ON br.book_id = b.id
WHERE br.returned = FALSE AND br.due_date < CURRENT_DATE;
