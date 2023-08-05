package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.shareit.common.Constants.DATE_FORMAT;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDto {

    @NotNull(message = "Заполните id предмета")
    private long itemId;
    @NotNull(message = "Заполните начало бронирования")
    @FutureOrPresent(message = "Проверьте корректность даты начала бронирования")
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime start;
    @NotNull(message = "Заполните конец бронирования")
    @Future(message = "Проверьте корректность даты конца бронирования")

    private LocalDateTime end;
}
