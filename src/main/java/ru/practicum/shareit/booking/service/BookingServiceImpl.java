package ru.practicum.shareit.booking.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.dto.GetBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repo.BookingRepo;
import ru.practicum.shareit.common.CommonMethods;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepo;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepo;

import static ru.practicum.shareit.booking.BookingMessages.INCORRECT_BOOKING;
import static ru.practicum.shareit.booking.BookingMessages.INCORRECT_BOOKING_DATE;
import static ru.practicum.shareit.booking.model.BookingStatus.*;
import static ru.practicum.shareit.common.Messages.INCORRECT_RESOURCE;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    UserRepo userRepo;
    ItemRepo itemRepo;
    BookingRepo bookingRepo;

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
        User booker = userRepo.getReferenceById(userId);
        Booking booking = BookingMapper.fromBookingDto(bookingDto, item, booker, WAITING);
        return BookingMapper.toGetBookingDto(bookingRepo.save(booking));
    }

    @Override
    public GetBookingDto approveBooking(long userId, long bookingId, boolean approved) {
        CommonMethods.checkResourceIsExists(userId, userRepo);
        CommonMethods.checkResourceIsExists(bookingId, bookingRepo);
        Booking booking = bookingRepo.getReferenceById(bookingId);
        if (booking.getItem().getOwner().getId() != userId) {
            throw new NotFoundException(INCORRECT_RESOURCE + bookingId);
        }
        if (approved) {
            booking.setStatus(APPROVED);
        } else {
            booking.setStatus(REJECTED);
        }
        return BookingMapper.toGetBookingDto(bookingRepo.save(booking));
    }
}
