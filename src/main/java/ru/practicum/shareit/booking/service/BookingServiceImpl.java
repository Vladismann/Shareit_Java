package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.dto.GetBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.repo.BookingRepo;
import ru.practicum.shareit.common.CommonMethods;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.StateValidationException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepo;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.practicum.shareit.booking.BookingMessages.*;
import static ru.practicum.shareit.booking.model.BookingStatus.*;
import static ru.practicum.shareit.common.Messages.INCORRECT_RESOURCE;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final UserRepo userRepo;
    private final ItemRepo itemRepo;
    private final BookingRepo bookingRepo;

    @Override
    public GetBookingDto addBooking(long userId, BookingDto bookingDto) {
        long itemId = bookingDto.getItemId();
        CommonMethods.checkResourceIsExists(userId, userRepo);
        CommonMethods.checkResourceIsExists(itemId, itemRepo);
        Item item = itemRepo.getReferenceById(itemId);
        if (!item.getAvailable()) {
            throw new ValidationException(INCORRECT_BOOKING);
        }
        if (bookingDto.getEnd().isBefore(bookingDto.getStart()) || bookingDto.getEnd().equals(bookingDto.getStart())) {
            throw new ValidationException(INCORRECT_BOOKING_DATE);
        }
        if (item.getOwner().getId() == userId) {
            throw new NotFoundException(INCORRECT_RESOURCE + itemId);
        }
        User booker = userRepo.getReferenceById(userId);
        Booking booking = BookingMapper.fromBookingDto(bookingDto, item, booker, WAITING);
        GetBookingDto newBooking = BookingMapper.toGetBookingDto(bookingRepo.save(booking));
        log.info(CREATE_BOOKING, newBooking);
        return newBooking;
    }

    @Override
    public GetBookingDto approveBooking(long userId, long bookingId, boolean approved) {
        CommonMethods.checkResourceIsExists(userId, userRepo);
        CommonMethods.checkResourceIsExists(bookingId, bookingRepo);
        Booking booking = bookingRepo.getReferenceById(bookingId);
        if (booking.getItem().getOwner().getId() != userId) {
            throw new NotFoundException(INCORRECT_RESOURCE + bookingId);
        } else if (booking.getStatus().equals(APPROVED)) {
            throw new ValidationException(ALREADY_APPROVED);
        }
        if (approved) {
            booking.setStatus(APPROVED);
        } else {
            booking.setStatus(REJECTED);
        }
        GetBookingDto newBooking = BookingMapper.toGetBookingDto(bookingRepo.save(booking));
        log.info(APPROVE_BOOKING, newBooking);
        return newBooking;
    }

    @Transactional(readOnly = true)
    @Override
    public GetBookingDto getBooking(long userId, long bookingId) {
        CommonMethods.checkResourceIsExists(userId, userRepo);
        CommonMethods.checkResourceIsExists(bookingId, bookingRepo);
        Booking booking = bookingRepo.getReferenceById(bookingId);
        if (booking.getItem().getOwner().getId() != userId && booking.getBooker().getId() != userId) {
            throw new NotFoundException(INCORRECT_RESOURCE + bookingId);
        }
        log.info(GET_BOOKING, bookingId);
        return BookingMapper.toGetBookingDto(booking);

    }

    private BookingState getBookingState(String state) {
        if (Arrays.stream(BookingState.values()).noneMatch(bookingState -> bookingState.name().equals(state.toUpperCase()))) {
            throw new StateValidationException(INCORRECT_BOOKING_STATE);
        }
        return BookingState.valueOf(state.toUpperCase());
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetBookingDto> getAllBookingsByBooker(long userId, String state) {
        CommonMethods.checkResourceIsExists(userId, userRepo);
        BookingState actualState = getBookingState(state);
        List<Booking> bookings = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.now();
        switch (actualState) {
            case ALL:
                bookings = bookingRepo.findByBookerIdOrderByStartDesc(userId);
                break;
            case CURRENT:
                bookings = bookingRepo
                        .findAllByBookerIdAndStartLessThanEqualAndEndGreaterThanEqualOrderByStartDesc(userId, currentDate, currentDate);
                break;
            case PAST:
                bookings = bookingRepo.findAllByBookerIdAndEndLessThanOrderByStartDesc(userId, currentDate);
                break;
            case FUTURE:
                bookings = bookingRepo.findAllByBookerIdAndStartGreaterThanOrderByStartDesc(userId, currentDate);
                break;
            case WAITING:
                bookings = bookingRepo.findByBookerIdAndStatusOrderByStartDesc(userId, WAITING);
                break;
            case REJECTED:
                bookings = bookingRepo.findByBookerIdAndStatusOrderByStartDesc(userId, REJECTED);
                break;
        }
        log.info(GET_BOOKINGS, bookings.size());
        return BookingMapper.listBookingsToGetBookingDto(bookings);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetBookingDto> getAllBookingsByOwner(long userId, String state) {
        CommonMethods.checkResourceIsExists(userId, userRepo);
        BookingState actualState = getBookingState(state);
        List<Booking> bookings = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.now();
        switch (actualState) {
            case ALL:
                bookings = bookingRepo.findAllByOwner(userId);
                break;
            case CURRENT:
                bookings = bookingRepo.findAllCurrentByOwner(userId, currentDate);
                break;
            case PAST:
                bookings = bookingRepo.findAllPastByOwner(userId, currentDate);
                break;
            case FUTURE:
                bookings = bookingRepo.findAllFutureByOwner(userId, currentDate);
                break;
            case WAITING:
                bookings = bookingRepo.findAllByStatusByOwner(userId, WAITING);
                break;
            case REJECTED:
                bookings = bookingRepo.findAllByStatusByOwner(userId, REJECTED);
                break;
        }
        log.info(GET_BOOKINGS, bookings.size());
        return BookingMapper.listBookingsToGetBookingDto(bookings);
    }


}