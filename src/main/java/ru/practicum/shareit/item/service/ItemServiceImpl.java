package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.repo.ItemRepo;
import ru.practicum.shareit.user.repo.UserRepo;

import java.util.List;

import static ru.practicum.shareit.item.ItemMessages.EMPTY_ITEM_SEARCH;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepo itemRepo;
    private final UserRepo userRepo;

    private void validateSearchText(String text) {
        if (text == null) {
            throw new ValidationException(EMPTY_ITEM_SEARCH);
        }
    }

    @Override
    public ItemDto createItem(long userId, ItemDto item) {
        item.setOwner(userRepo.get(userId));
        return itemRepo.create(item);
    }

    @Override
    public ItemDto updateItem(long userId, long itemId, ItemDto item) {
        item.setOwner(userRepo.get(userId));
        item.setId(itemId);
        return itemRepo.update(item);
    }

    @Override
    public ItemDto getItem(long itemId) {
        return itemRepo.get(itemId);
    }

    @Override
    public List<ItemDto> getAllByUserId(long userId) {
        userRepo.checkUserIsExist(userId);
        return itemRepo.getAllByUser(userId);
    }

    @Override
    public List<ItemDto> searchItemByText(String text) {
        validateSearchText(text);
        return itemRepo.search(text);
    }
}