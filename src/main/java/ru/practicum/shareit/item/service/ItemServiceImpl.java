package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepo;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepo;

import java.util.List;
import java.util.stream.Collectors;

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
    public ItemDto createItem(long userId, ItemDto itemDto) {
        User owner = userRepo.get(userId);
        Item item = ItemMapper.fromItemDto(itemDto, owner);
        return ItemMapper.toItemDto(itemRepo.create(item));
    }

    @Override
    public ItemDto updateItem(long userId, long itemId, ItemDto itemDto) {
        itemDto.setId(itemId);
        User owner = userRepo.get(userId);
        Item item = ItemMapper.fromItemDto(itemDto, owner);
        return ItemMapper.toItemDto(itemRepo.update(item));
    }

    @Override
    public ItemDto getItem(long itemId) {
        return ItemMapper.toItemDto(itemRepo.get(itemId));
    }

    @Override
    public List<ItemDto> getAllByUserId(long userId) {
        userRepo.checkUserIsExist(userId);
        return itemRepo.getAllByUser(userId).stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchItemByText(String text) {
        validateSearchText(text);
        return itemRepo.search(text).stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }
}