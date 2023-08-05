package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDto {

    @NotNull(message = "Заполните id предмета")
    private long itemId;
    @NotNull(message = "Заполните начало бронирования")
    @FutureOrPresent(message = "Проверьте корректность даты начала бронирования")

    private LocalDateTime start;
    @NotNull(message = "Заполните конец бронирования")
    @Future(message = "Проверьте корректность даты конца бронирования")

    private LocalDateTime end;
}
