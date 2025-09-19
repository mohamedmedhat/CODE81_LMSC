CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ==========================
-- USERS (Admins, Librarians, Members, Authors)

INSERT INTO users (name, email, password, role)
VALUES
  ('Alice Admin', 'alice.admin@example.com', crypt('password123', gen_salt('bf')), 'ADMINISTRATOR'),
  ('Liam Librarian', 'liam.librarian@example.com', crypt('password123', gen_salt('bf')), 'LIBRARIAN'),
  ('Sam Staff', 'sam.staff@example.com', crypt('password123', gen_salt('bf')), 'STAFF'),
  ('Mary Member', 'mary.member@example.com', crypt('password123', gen_salt('bf')), 'MEMBER'),
  ('John Author', 'john.author@example.com', crypt('password123', gen_salt('bf')), 'MEMBER'), -- used as author
  ('Sarah Author', 'sarah.author@example.com', crypt('password123', gen_salt('bf')), 'MEMBER'); -- used as author

-- ==========================
-- CATEGORIES

INSERT INTO categories (name)
VALUES
  ('Fiction'),
  ('Science Fiction'),
  ('Non-Fiction'),
  ('Technology'),
  ('History');

-- ==========================
-- BOOKS

INSERT INTO books (title, isbn, edition, lang, publication_year, summary, cover_image_url)
VALUES
  ('The Time Machine', '978-0451528551', '1st', 'English', 1895,
   'A classic science fiction novel about time travel.',
   'https://example.com/time_machine.jpg'),
  ('Spring Boot in Action', '978-1617292545', '2nd', 'English', 2021,
   'Comprehensive guide to building apps with Spring Boot.',
   'https://example.com/spring_boot.jpg'),
  ('Digital Transformation', '978-1119696486', '1st', 'English', 2020,
   'Insights on digital innovation and enterprise change.',
   'https://example.com/digital_transformation.jpg');

-- ==========================
-- BOOK-CATEGORY MAPPINGS

INSERT INTO book_category (book_id, category_id)
SELECT b.id, c.id FROM books b, categories c
WHERE (b.title = 'The Time Machine' AND c.name IN ('Fiction','Science Fiction'))
   OR (b.title = 'Spring Boot in Action' AND c.name IN ('Technology'))
   OR (b.title = 'Digital Transformation' AND c.name IN ('Technology','Non-Fiction'));

-- ==========================
-- BOOK-AUTHOR MAPPINGS
-- (Using users table for authors; here we link to "John Author" and "Sarah Author")

INSERT INTO book_author (book_id, author_id)
SELECT b.id, u.id FROM books b, users u
WHERE (b.title = 'The Time Machine' AND u.name = 'John Author')
   OR (b.title = 'Spring Boot in Action' AND u.name = 'Sarah Author')
   OR (b.title = 'Digital Transformation' AND u.name IN ('John Author','Sarah Author'));

-- ==========================
-- BORROWINGS
-- (Mary Member borrows books)

INSERT INTO borrowings (book_id, member_id, borrow_date, due_date, returned)
SELECT b.id, u.id, CURRENT_DATE - INTERVAL '5 days', CURRENT_DATE + INTERVAL '10 days', FALSE
FROM books b, users u
WHERE b.title = 'The Time Machine' AND u.name = 'Mary Member';

INSERT INTO borrowings (book_id, member_id, borrow_date, return_date, due_date, returned)
SELECT b.id, u.id, CURRENT_DATE - INTERVAL '20 days', CURRENT_DATE - INTERVAL '5 days',
       CURRENT_DATE - INTERVAL '10 days', TRUE
FROM books b, users u
WHERE b.title = 'Spring Boot in Action' AND u.name = 'Mary Member';

-- ==========================
-- USER ACTIVITY LOGS

INSERT INTO user_activity_logs (user_id, action)
SELECT u.id, 'User logged in'
FROM users u
WHERE u.name IN ('Alice Admin','Liam Librarian','Mary Member');
