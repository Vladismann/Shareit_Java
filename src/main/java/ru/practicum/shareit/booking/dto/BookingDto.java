package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import static ru.practicum.shareit.booking.BookingMessages.EMPTY_BOOKING_DATE;
import static ru.practicum.shareit.booking.BookingMessages.INCORRECT_BOOKING_DATE;

@AllArgsConstructor
@Data
public class BookingDto {

    @NotBlank(message = EMPTY_BOOKING_DATE)
    long itemId;
    @NotBlank(message = EMPTY_BOOKING_DATE)
    @FutureOrPresent(message = INCORRECT_BOOKING_DATE)
    LocalDateTime start;
    @NotBlank(message = EMPTY_BOOKING_DATE)
    @FutureOrPresent(message = INCORRECT_BOOKING_DATE)
    LocalDateTime end;
}
