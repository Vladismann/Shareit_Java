package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.GetBookingItemDto;
import ru.practicum.shareit.user.dto.GetBookingUserDto;

import java.time.LocalDateTime;

@Data
@Builder
public class GetBookingDto {

    private long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private BookingStatus status;
    private GetBookingUserDto booker;
    private GetBookingItemDto item;
}
