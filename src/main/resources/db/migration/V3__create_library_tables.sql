CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE books (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(255) NOT NULL,
    edition VARCHAR(255) NOT NULL,
    lang VARCHAR(255) NOT NULL,
    publication_year INTEGER NOT NULL,
    summary VARCHAR(255),
    cover_image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
  );

CREATE TABLE categories (
      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
      name VARCHAR(255) NOT NULL
      );

CREATE TABLE borrowings (
       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
       book_id UUID REFERENCES books(id) ON DELETE CASCADE,
       member_id UUID REFERENCES users(id) ON DELETE CASCADE,
       borrow_date DATE,
       return_date DATE,
       due_date DATE,
       returned BOOLEAN DEFAULT FALSE
    );

CREATE TABLE book_category(
book_id UUID REFERENCES books(id),
category_id UUID REFERENCES categories(id),
 PRIMARY KEY (book_id, category_id)
);

CREATE TABLE book_author(
book_id UUID REFERENCES books(id),
author_id UUID REFERENCES users(id),
PRIMARY KEY (book_id, author_id)
);