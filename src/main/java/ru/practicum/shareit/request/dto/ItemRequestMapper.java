package ru.practicum.shareit.request.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.GetItemRequestItemDto;
import ru.practicum.shareit.request.model.ItemRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ItemRequestMapper {

    public ItemRequest toItemRequest(long userId, AddItemRequestDto addItemRequestDto) {
        return ItemRequest.builder()
                .description(addItemRequestDto.getDescription())
                .requestor(userId)
                .created(LocalDateTime.now())
                .build();
    }

    public CreatedItemRequestDto toGetItemRequest(ItemRequest itemRequest) {
        return CreatedItemRequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .build();
    }

    public GetItemRequestDto toGetItemRequestDto(ItemRequest itemRequest, List<GetItemRequestItemDto> getItemRequestItemDto) {
        if (getItemRequestItemDto == null) {
            getItemRequestItemDto = new ArrayList<>();
        }
        return GetItemRequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .items(getItemRequestItemDto)
                .build();
    }

    public List<GetItemRequestDto> toGetItemRequestDto(List<ItemRequest> itemRequests, List<GetItemRequestItemDto> getItemRequestItemDto) {
        List<GetItemRequestDto> getItemRequestDto = new ArrayList<>();
        for (ItemRequest itemRequest: itemRequests) {
            List<GetItemRequestItemDto> itemsForActualRequest = getItemRequestItemDto.stream()
                    .filter(item -> item.getRequestId() == itemRequest.getId()).collect(Collectors.toList());
            GetItemRequestDto getItemRequest = toGetItemRequestDto(itemRequest, itemsForActualRequest);
            getItemRequestDto.add(getItemRequest);
        }
        return getItemRequestDto;
    }
}
