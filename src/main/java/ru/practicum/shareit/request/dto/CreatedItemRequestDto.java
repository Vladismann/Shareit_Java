package ru.practicum.shareit.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static ru.practicum.shareit.common.Constants.DATE_FORMAT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatedItemRequestDto {

    private Long id;
    private String description;
    @JsonFormat(pattern = DATE_FORMAT)
    LocalDateTime created;
}
