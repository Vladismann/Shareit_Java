package ru.practicum.shareit.item.repo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.LinkedHashMap;

import static ru.practicum.shareit.item.ItemMessages.*;
import static ru.practicum.shareit.item.dto.ItemMapper.toItemDto;

@Repository
@Slf4j
public class ItemRepoImpl implements ItemRepo {

    private final LinkedHashMap<Long, Item> items = new LinkedHashMap<>();
    private static long itemId = 1;

    private void checkItemIsExist(long id) {
        if (!items.containsKey(id)) {
            log.info(INCORRECT_ITEM + id);
            throw new NotFoundException(INCORRECT_ITEM + id);
        }
    }

    private void checkOwnerIsCorrect(Item item) {
        long id = item.getId();
        long ownerId = item.getOwner();
        if (items.get(id).getOwner() != ownerId) {
            log.info(INCORRECT_OWNER, id, ownerId);
            throw new NotFoundException(INCORRECT_ITEM + id);
        }
    }

    @Override
    public ItemDto create(Item item) {
        item.setId(itemId++);
        items.put(item.getId(), item);
        return toItemDto(item);
    }

    @Override
    public ItemDto update(Item item) {
        long id = item.getId();
        checkItemIsExist(id);
        checkOwnerIsCorrect(item);
        Item actualItem = items.get(id);
        if (item.getName() != null && !item.getName().isBlank()) {
            actualItem.setName(item.getName());
        }
        if (item.getDescription() != null && !item.getDescription().isBlank()) {
            actualItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null && item.getAvailable() != actualItem.getAvailable()) {
            actualItem.setAvailable(item.getAvailable());
        }
        items.put(id, actualItem);
        return toItemDto(actualItem);
    }

    @Override
    public ItemDto get(long id) {
        checkItemIsExist(id);
        return toItemDto(items.get(id));
    }
}