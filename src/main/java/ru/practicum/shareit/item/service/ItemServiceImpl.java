package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repo.BookingRepo;
import ru.practicum.shareit.common.CommonMethods;
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


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepo itemRepo;
    private final UserRepo userRepo;
    private final BookingRepo bookingRepo;

    private void validateSearchText(String text) {
        if (text == null) {
            throw new ValidationException(EMPTY_ITEM_SEARCH);
        }
    }

    @Override
    public ItemDto createItem(long userId, ItemDto itemDto) {
        CommonMethods.checkResourceIsExists(userId, userRepo);
        User owner = userRepo.getReferenceById(userId);
        Item item = ItemMapper.fromItemDto(itemDto, owner);
        ItemDto newItemDto = ItemMapper.toItemDto(itemRepo.save(item));
        log.info(CREATE_ITEM, newItemDto);
        return newItemDto;
    }

    @Override
    public ItemDto updateItem(long userId, long itemId, ItemDto itemDto) {
        CommonMethods.checkResourceIsExists(userId, userRepo);
        CommonMethods.checkResourceIsExists(itemId, itemRepo);
        itemDto.setId(itemId);
        Item atualItem = itemRepo.getReferenceById(itemId);
        if (atualItem.getOwner().getId() != userId) {
            log.info(INCORRECT_OWNER, itemId, userId);
            throw new NotFoundException(INCORRECT_ITEM + itemId);
        }
        atualItem = ItemMapper.updateItemFromItemDto(atualItem, itemDto);
        ItemDto newItemDto = ItemMapper.toItemDto(itemRepo.save(atualItem));
        log.info(UPDATE_ITEM, newItemDto);
        return newItemDto;
    }

    @Transactional(readOnly = true)
    @Override
    public ItemDto getItem(long userId, long itemId) {
        CommonMethods.checkResourceIsExists(userId, userRepo);
        CommonMethods.checkResourceIsExists(itemId, itemRepo);
        log.info(GET_ITEM, itemId);
        Item item = itemRepo.getReferenceById(itemId);
        if (userId != item.getOwner().getId()) {
            return ItemMapper.toItemDto(item);
        } else {
            List<Booking> itemBookings = bookingRepo.findByItemId(itemId);
            return ItemMapper.toItemDtoForOwner(item, itemBookings);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemDto> getAllByUserId(long userId) {
        CommonMethods.checkResourceIsExists(userId, userRepo);
        log.info(GET_ITEMS, userId);
        return ItemMapper.itemsListToItemDto(itemRepo.findByOwnerId(userId));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemDto> searchItemByText(String text) {
        validateSearchText(text);
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        List<ItemDto> foundItems = ItemMapper.itemsListToItemDto(itemRepo.search(text));
        log.info(GET_ITEMS, foundItems.size());
        return foundItems;
    }
}