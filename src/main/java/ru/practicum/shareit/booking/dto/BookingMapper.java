package ru.practicum.shareit.booking.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.GetBookingItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.GetBookingUserDto;
import ru.practicum.shareit.user.model.User;

@UtilityClass
public class BookingMapper {

    public GetBookingDto toGetBookingDto(Booking booking) {
        return GetBookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .status(booking.getStatus())
                .booker(new GetBookingUserDto(booking.getBooker().getId()))
                .item(new GetBookingItemDto(booking.getItem().getId(), booking.getItem().getName()))
                .build();
    }

    public Booking fromBookingDto(BookingDto bookingDto, Item item, User booker, BookingStatus status) {
        return Booking.builder()
                .start(bookingDto.getStart())
                .end(bookingDto.getEnd())
                .item(item)
                .booker(booker)
                .status(status)
                .build();
    }
}
