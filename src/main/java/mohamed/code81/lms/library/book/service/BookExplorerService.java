package mohamed.code81.lms.library.book.service;

import mohamed.code81.lms.library.book.Book;

import java.util.UUID;

public interface BookExplorerService {
    Book getById(UUID id);
}
