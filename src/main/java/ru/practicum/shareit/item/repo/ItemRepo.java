package ru.practicum.shareit.item.repo;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepo {

    ItemDto create(Item item);

    ItemDto update(Item item);

    ItemDto get(long id);

    List<ItemDto> getAllByUser(long userId);

    List<ItemDto> search(String text);
}
