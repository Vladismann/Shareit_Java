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
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CreateCommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.CommentRepo;
import ru.practicum.shareit.item.repo.ItemRepo;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepo;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.shareit.booking.model.BookingStatus.APPROVED;
import static ru.practicum.shareit.item.ItemMessages.*;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepo itemRepo;
    private final UserRepo userRepo;
    private final CommentRepo commentRepo;
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
        ItemDto newItemDto = ItemMapper.saveItemDto(itemRepo.save(item));
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
        ItemDto newItemDto = ItemMapper.saveItemDto(itemRepo.save(atualItem));
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
        List<Booking> bookings = bookingRepo.findAllByItemId(itemId);
        Set<Comment> comments = commentRepo.findAllByItemId(itemId);
        if (userId != item.getOwner().getId()) {
            return ItemMapper.toItemDto(item, comments);
        } else {
            return ItemMapper.toItemDtoForOwner(item, bookings, comments);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemDto> getAllByUserId(long userId) {
        CommonMethods.checkResourceIsExists(userId, userRepo);
        List<Item> items = itemRepo.findByOwnerId(userId);
        if (items.isEmpty()) {
            log.info(GET_ITEMS, 0);
            return List.of();
        }
        List<Long> itemsIds = items.stream().map(Item::getId).collect(Collectors.toList());
        List<Booking> bookings = bookingRepo.findAllByItemIdIn(itemsIds);
        Set<Comment> comments = commentRepo.findAllByItemIdIn(itemsIds);
        log.info(GET_ITEMS, userId);
        return ItemMapper.toItemDtoForOwnerList(itemRepo.findByOwnerId(userId), bookings, comments);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemDto> searchItemByText(String text) {
        validateSearchText(text);
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        List<Item> items = itemRepo.search(text);
        if (items.isEmpty()) {
            log.info(GET_ITEMS, 0);
            return List.of();
        }
        List<Long> itemsIds = items.stream().map(Item::getId).collect(Collectors.toList());
        Set<Comment> comments = commentRepo.findAllByItemIdIn(itemsIds);
        List<ItemDto> foundItems = ItemMapper.itemsListToItemDto(items, comments);
        log.info(GET_ITEMS, foundItems.size());
        return foundItems;
    }

    @Override
    public CommentDto addComment(long userId, long itemId, CreateCommentDto commentDto) {
        CommonMethods.checkResourceIsExists(userId, userRepo);
        CommonMethods.checkResourceIsExists(itemId, itemRepo);
        Item actualItem = itemRepo.getReferenceById(itemId);
        List<Booking> bookings = bookingRepo.findAllByItemId(itemId);
        if (bookings.stream()
                .noneMatch(booking
                        -> booking.getBooker().getId() == userId
                        && booking.getStatus().equals(APPROVED)
                        && booking.getEnd().isBefore(LocalDateTime.now()))) {
            throw new ValidationException(NO_BOOKING_COMMENT);
        }
        User author = userRepo.getReferenceById(userId);
        Comment comment = ItemMapper.createComment(commentDto, actualItem, author);
        return ItemMapper.commentToCommentDto(commentRepo.save(comment));
    }
}