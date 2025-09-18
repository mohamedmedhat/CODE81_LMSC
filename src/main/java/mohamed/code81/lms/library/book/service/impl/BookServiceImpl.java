package mohamed.code81.lms.library.book.service.impl;

import lombok.RequiredArgsConstructor;
import mohamed.code81.lms.category.Category;
import mohamed.code81.lms.category.service.CategoryExplorerService;
import mohamed.code81.lms.common.response.PageableResponseDto;
import mohamed.code81.lms.library.book.Book;
import mohamed.code81.lms.library.book.BookMapper;
import mohamed.code81.lms.library.book.BookRepository;
import mohamed.code81.lms.library.book.dto.request.BookRequestDto;
import mohamed.code81.lms.library.book.dto.response.BookResponseDto;
import mohamed.code81.lms.library.book.dto.response.GetListOfCategoriesAndUsersResposneDto;
import mohamed.code81.lms.library.book.exception.BookNotFoundException;
import mohamed.code81.lms.library.book.service.BookExplorerService;
import mohamed.code81.lms.library.book.service.BookService;
import mohamed.code81.lms.log.dto.request.UserActivityLogRequestDto;
import mohamed.code81.lms.user.User;
import mohamed.code81.lms.user.enums.UserRole;
import mohamed.code81.lms.user.service.UserExplorerService;
import mohamed.code81.lms.user.service.UserRoleService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService, BookExplorerService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CategoryExplorerService categoryExplorerService;
    private final UserExplorerService userExplorerService;
    private final UserRoleService userRoleService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public BookResponseDto addBook(BookRequestDto dto, UUID userId) {
        GetListOfCategoriesAndUsersResposneDto response = getCategoriesAndUsers(dto.categories(), dto.authorsId());

        Book book = bookMapper.toBook(dto, response.categories(), response.users());
        Book savedBook = bookRepository.save(book);

        User user = userExplorerService.getById(userId);

        if (user.getRole() != UserRole.ADMINISTRATOR) {
            userRoleService.updateUserRole(user.getId(), UserRole.LIBRARIAN);
        }

        applicationEventPublisher.publishEvent(new UserActivityLogRequestDto(user, "add new book"));

        return bookMapper.toBookResponseDto(savedBook);
    }

    @Override
    public BookResponseDto updateBook(UUID id, BookRequestDto dto, UUID userId) {
        GetListOfCategoriesAndUsersResposneDto response = getCategoriesAndUsers(dto.categories(), dto.authorsId());

        Book book = getById(id);

        Book updatedBook = bookMapper.updateToBook(book, dto, response.categories(), response.users());

        Book savedBook = bookRepository.save(updatedBook);

        User user = userExplorerService.getById(userId);

        applicationEventPublisher.publishEvent(
                new UserActivityLogRequestDto(user, "update book called (" + book.getTitle() + ") to book called (" + savedBook.getTitle() + ")"));

        return bookMapper.toBookResponseDto(savedBook);
    }

    @Override
    public BookResponseDto getBook(UUID id) {
        return bookMapper.toBookResponseDto(getById(id));
    }

    @Override
    public PageableResponseDto<BookResponseDto> getBooks(int page, int size) {
        return bookMapper.toBookPageableResponse(bookRepository.findAll(
                PageRequest.of(page, size))
        );
    }

    @Override
    public void deleteBook(UUID id, UUID userId) {
        Book book = getById(id);

        User user = userExplorerService.getById(userId);

        applicationEventPublisher.publishEvent(new UserActivityLogRequestDto(user, "delete book with id: " + id));

        bookRepository.delete(book);
    }

    @Override
    public Book getById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("book with id: " + id + " not found"));
    }

    private GetListOfCategoriesAndUsersResposneDto getCategoriesAndUsers(List<String> categoriesName, List<UUID> usersId) {
        List<Category> categories = categoryExplorerService.getByNames(categoriesName);

        List<User> users = userExplorerService.getByIds(usersId);

        return new GetListOfCategoriesAndUsersResposneDto(categories, users);
    }

}
