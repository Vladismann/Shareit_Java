package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepo;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepo;

import java.util.Collections;
import java.util.List;

import static ru.practicum.shareit.item.ItemMessages.*;
import static ru.practicum.shareit.user.UserMessages.INCORRECT_USER;


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

    private void checkUserIsExists(long userId) {
        if (!userRepo.existsById(userId)) {
            log.info(INCORRECT_USER + userId);
            throw new NotFoundException(INCORRECT_USER + userId);
        }
    }

    private void checkItemIsExist(long itemId) {
        if (!itemRepo.existsById(itemId)) {
            log.info(INCORRECT_ITEM + itemId);
            throw new NotFoundException(INCORRECT_ITEM + itemId);
        }
    }

    @Override
    public ItemDto createItem(long userId, ItemDto itemDto) {
        checkUserIsExists(userId);
        User owner = userRepo.getReferenceById(userId);
        Item item = ItemMapper.fromItemDto(itemDto, owner);
        return ItemMapper.toItemDto(itemRepo.save(item));
    }

    @Override
    public ItemDto updateItem(long userId, long itemId, ItemDto itemDto) {
        checkUserIsExists(userId);
        checkItemIsExist(itemId);
        itemDto.setId(itemId);
        Item atualItem = itemRepo.getReferenceById(itemId);
        if (atualItem.getOwner().getId() != userId) {
            log.info(INCORRECT_OWNER, itemId, userId);
            throw new NotFoundException(INCORRECT_ITEM + itemId);
        }
        atualItem = ItemMapper.updateItemFromItemDto(atualItem, itemDto);
        return ItemMapper.toItemDto(itemRepo.save(atualItem));
    }

    @Override
    public ItemDto getItem(long itemId) {
        checkItemIsExist(itemId);
        return ItemMapper.toItemDto(itemRepo.getReferenceById(itemId));
    }

    @Override
    public List<ItemDto> getAllByUserId(long userId) {
        checkUserIsExists(userId);
        return ItemMapper.itemsListToItemDto(itemRepo.findByOwnerId(userId));
    }

    @Override
    public List<ItemDto> searchItemByText(String text) {
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        validateSearchText(text);
        return ItemMapper.itemsListToItemDto(itemRepo.search(text));
    }
}