package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.shareit.booking.BookingMessages.*;
import static ru.practicum.shareit.common.Constants.DATE_FORMAT;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDto {

    @NotNull(message = EMPTY_BOOKING_ITEM)
    private long itemId;
    @NotNull(message = EMPTY_BOOKING_DATE)
    @FutureOrPresent(message = INCORRECT_BOOKING_DATE)
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime start;
    @NotNull(message = EMPTY_BOOKING_DATE)
    @Future(message = INCORRECT_BOOKING_DATE)
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime end;
}
