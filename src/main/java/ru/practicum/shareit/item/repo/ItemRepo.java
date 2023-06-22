package ru.practicum.shareit.item.repo;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepo {

    Item create(Item item);

    Item update(Item item);

    Item get(long id);

    List<Item> getAllByUser(long userId);

    List<Item> search(String text);
}
