package mohamed.code81.lms.library.book;

import mohamed.code81.lms.category.Category;
import mohamed.code81.lms.category.dto.response.CategoryResponseDto;
import mohamed.code81.lms.common.response.PageableResponseDto;
import mohamed.code81.lms.library.book.dto.request.BookRequestDto;
import mohamed.code81.lms.library.book.dto.response.BookResponseDto;
import mohamed.code81.lms.user.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapper {
    public Book toBook(BookRequestDto dto, List<Category> categories, List<User> authors) {
        return Book.builder()
                .title(dto.title())
                .isbn(dto.isbn())
                .edition(dto.edition())
                .lang(dto.lang())
                .categories(new ArrayList<>(categories))
                .publicationYear(dto.publicationYear())
                .authors(new ArrayList<>(authors))
                .summary(dto.summary())
                .coverImageUrl(dto.coverImageUrl())
                .build();
    }

    public Book updateToBook(Book book, BookRequestDto dto, List<Category> categories, List<User> authors) {
        book.setTitle(dto.title());
        book.setIsbn(dto.isbn());
        book.setEdition(dto.edition());
        book.setLang(dto.lang());
        book.setCategories(new ArrayList<>(categories));
        book.setPublicationYear(dto.publicationYear());
        book.setAuthors(new ArrayList<>(authors));
        book.setSummary(dto.summary());
        book.setCoverImageUrl(dto.coverImageUrl());
        return book;
    }

    public BookResponseDto toBookResponseDto(Book book) {
        return new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getEdition(),
                book.getLang(),
                book.getPublicationYear(),
                book.getSummary(),
                book.getCategories().stream()
                        .map(b -> new CategoryResponseDto(b.getId(), b.getName()))
                        .toList(),
                book.getCoverImageUrl(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        );
    }

    public PageableResponseDto<BookResponseDto> toBookPageableResponse(Page<Book> books) {
        return new PageableResponseDto<>(
                books.getContent().stream()
                        .map(this::toBookResponseDto)
                        .toList(),
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages()
        );
    }
}
