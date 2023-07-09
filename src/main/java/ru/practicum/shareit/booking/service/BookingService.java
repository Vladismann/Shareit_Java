package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.GetBookingDto;

public interface BookingService {

    GetBookingDto addBooking(long userId, BookingDto bookingDto);

    GetBookingDto approveBooking(long userId, long bookingId, boolean approved);
}
