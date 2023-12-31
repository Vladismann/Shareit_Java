package ru.practicum.shareit.booking.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.GetBookingDto;

import java.util.List;

public interface BookingService {

    GetBookingDto addBooking(long userId, BookingDto bookingDto);

    GetBookingDto approveBooking(long userId, long bookingId, boolean approved);

    GetBookingDto getBooking(long userId, long bookingId);

    List<GetBookingDto> getAllBookingsByBooker(long userId, String state, Pageable pageable);

    List<GetBookingDto> getAllBookingsByOwner(long userId, String state, Pageable pageable);
}
