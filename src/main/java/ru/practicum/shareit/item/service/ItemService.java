package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto createItem(long userId, ItemDto item);

    ItemDto updateItem(long userId, long itemId, ItemDto item);

    ItemDto getItem(long itemId);

    List<ItemDto> getAllByUserId(long userId);

    List<ItemDto> searchItemByText(String text);
}
