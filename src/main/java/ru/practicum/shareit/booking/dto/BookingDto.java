package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.shareit.booking.BookingMessages.*;

@AllArgsConstructor
@Data
public class BookingDto {

    @NotNull(message = EMPTY_BOOKING_ITEM)
    private long itemId;
    @NotNull(message = EMPTY_BOOKING_DATE)
    @FutureOrPresent(message = INCORRECT_BOOKING_DATE)
    private LocalDateTime start;
    @NotNull(message = EMPTY_BOOKING_DATE)
    @Future(message = INCORRECT_BOOKING_DATE)
    private LocalDateTime end;
}
