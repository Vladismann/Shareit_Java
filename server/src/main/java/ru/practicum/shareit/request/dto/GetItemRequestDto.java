package ru.practicum.shareit.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.GetItemRequestItemDto;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.shareit.common.Constants.DATE_FORMAT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetItemRequestDto {

    private long id;
    private String description;
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime created;
    private List<GetItemRequestItemDto> items;
}
