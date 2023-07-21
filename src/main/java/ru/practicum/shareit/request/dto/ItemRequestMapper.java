package ru.practicum.shareit.request.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.request.model.ItemRequest;

import java.time.LocalDateTime;

@UtilityClass
public class ItemRequestMapper {

    public ItemRequest toItemRequest(long userId, CreateItemRequestDto createItemRequestDto) {
        return ItemRequest.builder()
                .description(createItemRequestDto.getDescription())
                .requestor(userId)
                .created(LocalDateTime.now())
                .build();
    }

    public GetItemRequestDto toGetItemRequest(ItemRequest itemRequest) {
        return GetItemRequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .build();
    }
}
