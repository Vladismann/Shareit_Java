package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.GetItemRequestItemDto;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetItemRequestDto {

    private long id;
    private String description;
    private LocalDateTime created;
    private List<GetItemRequestItemDto> items;
}
