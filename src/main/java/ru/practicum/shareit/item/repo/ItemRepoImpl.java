/*
package ru.practicum.shareit.item.repo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.ItemMessages.INCORRECT_ITEM;
import static ru.practicum.shareit.item.ItemMessages.INCORRECT_OWNER;

@Repository
@Slf4j
public class ItemRepoImpl implements ItemRepo {

    private final LinkedHashMap<Long, Item> items = new LinkedHashMap<>();
    private final LinkedHashMap<Long, List<Item>> usersItems = new LinkedHashMap<>();
    private static long itemId = 1;

    private void checkItemIsExist(long id) {
        if (!items.containsKey(id)) {
            log.info(INCORRECT_ITEM + id);
            throw new NotFoundException(INCORRECT_ITEM + id);
        }
    }

    private void checkOwnerIsCorrect(Item item) {
        long id = item.getId();
        long ownerId = item.getOwner().getId();
        if (items.get(id).getOwner().getId() != ownerId) {
            log.info(INCORRECT_OWNER, id, ownerId);
            throw new NotFoundException(INCORRECT_ITEM + id);
        }
    }

    @Override
    public Item create(Item item) {
        item.setId(itemId++);
        items.put(item.getId(), item);
        List<Item> userItem = usersItems.getOrDefault(item.getOwner().getId(), new ArrayList<>());
        userItem.add(item);
        usersItems.put(item.getOwner().getId(), userItem);
        return item;
    }

    @Override
    public Item update(Item item) {
        long id = item.getId();
        checkItemIsExist(id);
        checkOwnerIsCorrect(item);
        Item actualItem = items.get(id);
        List<Item> userItem = usersItems.getOrDefault(item.getOwner().getId(), new ArrayList<>());
        userItem.remove(actualItem);
        if (item.getName() != null && !item.getName().isBlank()) {
            actualItem.setName(item.getName());
        }
        if (item.getDescription() != null && !item.getDescription().isBlank()) {
            actualItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null && item.getAvailable() != actualItem.getAvailable()) {
            actualItem.setAvailable(item.getAvailable());
        }
        userItem.add(actualItem);
        usersItems.put(item.getOwner().getId(), userItem);
        return actualItem;
    }

    @Override
    public Item get(long id) {
        checkItemIsExist(id);
        return items.get(id);
    }

    @Override
    public List<Item> getAllByUser(long userId) {
        return new ArrayList<>(usersItems.get(userId));
    }

    @Override
    public List<Item> search(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        String textForSearch = text.toLowerCase();
        return items.values().stream()
                .filter(item -> (item.getAvailable()
                        && (item.getName().toLowerCase().contains(textForSearch) || item.getDescription().toLowerCase().contains(textForSearch))))
                .collect(Collectors.toList());
    }
}*/
