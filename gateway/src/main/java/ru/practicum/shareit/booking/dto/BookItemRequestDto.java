package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

import static ru.practicum.shareit.common.Constants.DATE_FORMAT;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemRequestDto {

	private long itemId;
	@JsonFormat(pattern = DATE_FORMAT)
	@FutureOrPresent
	private LocalDateTime start;
	@JsonFormat(pattern = DATE_FORMAT)
	@Future
	private LocalDateTime end;
}
