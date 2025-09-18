/*
 * Copyright (c) 2025 Mohamed Medhat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy at: http://www.apache.org/licenses/LICENSE-2.0
 */

package mohamed.code81.lms.library.borrow;

import lombok.RequiredArgsConstructor;
import mohamed.code81.lms.common.response.PageableResponseDto;
import mohamed.code81.lms.library.book.Book;
import mohamed.code81.lms.library.book.service.BookExplorerService;
import mohamed.code81.lms.library.borrow.dto.request.BorrowRequestDto;
import mohamed.code81.lms.library.borrow.dto.response.BorrowResponseDto;
import mohamed.code81.lms.library.borrow.dto.response.GetMemberAndBookResponseDto;
import mohamed.code81.lms.library.borrow.exception.BorrowingNotFoundException;
import mohamed.code81.lms.log.dto.request.UserActivityLogRequestDto;
import mohamed.code81.lms.user.User;
import mohamed.code81.lms.user.service.UserExplorerService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BorrowingServiceImpl implements BorrowService {
    private final BorrowingRepository borrowingRepository;
    private final BorrowingMapper borrowingMapper;
    private final UserExplorerService userExplorerService;
    private final BookExplorerService bookExplorerService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public BorrowResponseDto borrowBook(BorrowRequestDto dto, UUID userId) {
        GetMemberAndBookResponseDto response = getMemberAndClient(dto.userId(), dto.bookId());
        Borrowing borrowing = borrowingMapper.toBorrowing(dto, response.book(), response.member());
        Borrowing savedBorrowing = borrowingRepository.save(borrowing);

        User user = userExplorerService.getById(userId);
        applicationEventPublisher.publishEvent(new UserActivityLogRequestDto(user, "borrow book"));

        return borrowingMapper.borrowResponseDto(savedBorrowing);
    }

    @Override
    public BorrowResponseDto returnBorrowedBook(UUID id, UUID userId) {
        Borrowing borrowing = getById(id);
        borrowing.setReturned(Boolean.TRUE);
        Borrowing savedBorrowing = borrowingRepository.save(borrowing);

        User user = userExplorerService.getById(userId);
        applicationEventPublisher.publishEvent(new UserActivityLogRequestDto(user, "return borrowed book"));

        return borrowingMapper.borrowResponseDto(savedBorrowing);
    }

    @Override
    public BorrowResponseDto updateBorrowedBook(UUID id, BorrowRequestDto dto, UUID userId) {
        GetMemberAndBookResponseDto response = getMemberAndClient(dto.userId(), dto.bookId());
        Borrowing borrowing = getById(id);
        Borrowing updataedBorrowing = borrowingMapper.updateToBorrowing(borrowing, dto, response.book(), response.member());
        Borrowing savedBorrowing = borrowingRepository.save(updataedBorrowing);

        User user = userExplorerService.getById(userId);
        applicationEventPublisher.publishEvent(new UserActivityLogRequestDto(user, "update borrowed book"));

        return borrowingMapper.borrowResponseDto(savedBorrowing);
    }

    @Override
    public BorrowResponseDto getBorrowedBook(UUID id) {
        return borrowingMapper.borrowResponseDto(getById(id));
    }

    @Override
    public PageableResponseDto<BorrowResponseDto> getBorrowedBooks(int page, int size) {
        return borrowingMapper.toBorrowingPageableResponse(
                borrowingRepository.findAll(PageRequest.of(page, size))
        );
    }

    @Override
    public void deleteBorrowedBook(UUID id, UUID userId) {
        Borrowing borrowing = getById(id);

        User user = userExplorerService.getById(userId);
        applicationEventPublisher.publishEvent(new UserActivityLogRequestDto(user, " delete borrowed book with id: "+id));

        borrowingRepository.delete(borrowing);
    }

    private GetMemberAndBookResponseDto getMemberAndClient(UUID memberId, UUID bookId){
        User member = userExplorerService.getById(memberId);
        Book book = bookExplorerService.getById(bookId);

        return new GetMemberAndBookResponseDto(member, book);
    }

    private Borrowing getById(UUID id){
        return borrowingRepository.findById(id)
                .orElseThrow(() -> new BorrowingNotFoundException("borrowing with id: "+id+" not found"));
    }
}
