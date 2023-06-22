package ru.practicum.shareit.item.repo;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemRepo {

    ItemDto create(ItemDto item);

    ItemDto update(ItemDto item);

    ItemDto get(long id);

    List<ItemDto> getAllByUser(long userId);

    List<ItemDto> search(String text);
}
