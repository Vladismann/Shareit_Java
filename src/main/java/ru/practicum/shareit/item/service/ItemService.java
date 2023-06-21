package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    ItemDto createItem(long userId, Item item);

    ItemDto updateItem(long userId, long itemId, Item item);

    ItemDto getItem(long itemId);

    List<ItemDto> getAllByUserId(long userId);

    List<ItemDto> searchItemByText(String text);
}
