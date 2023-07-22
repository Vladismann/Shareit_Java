package ru.practicum.shareit.item.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.dto.GetItemBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.shareit.booking.model.BookingStatus.APPROVED;

@UtilityClass
public class ItemMapper {

    public ItemDto saveItemDto(Item item) {
        return new ItemDto.ItemDtoBuilder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .requestId(item.getRequestId())
                .build();
    }

    public ItemDto toItemDto(Item item, Set<Comment> comments) {
        return new ItemDto.ItemDtoBuilder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .comments(commentsToCommentsDtoList(comments))
                .requestId(item.getRequestId())
                .build();
    }

    public GetItemBookingDto bookingToGetItemBookingDto(Booking booking) {
        if (booking != null && booking.getId() != 0) {
            return GetItemBookingDto.builder()
                    .id(booking.getId())
                    .bookerId(booking.getBooker().getId())
                    .build();
        } else {
            return null;
        }
    }

    public CommentDto commentToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }

    public Set<CommentDto> commentsToCommentsDtoList(Set<Comment> comments) {
        if (comments != null) {
            return comments.stream().map(ItemMapper::commentToCommentDto).collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }

    public ItemDto toItemDtoForOwner(Item item, List<Booking> bookings, Set<Comment> comments) {
        LocalDateTime currentTime = LocalDateTime.now();
        Booking lastBooking = new Booking();
        Booking nextBooking = new Booking();
        if (!bookings.isEmpty()) {
            lastBooking = bookings.stream()
                    .filter(booking -> booking.getStart().isBefore(currentTime) && booking.getStatus().equals(APPROVED))
                    .reduce((first, second) -> second)
                    .orElse(null);
            nextBooking = bookings.stream()
                    .filter(booking -> booking.getStart().isAfter(currentTime) && booking.getStatus().equals(APPROVED))
                    .reduce((first, second) -> second)
                    .orElse(null);
        }

        return new ItemDto.ItemDtoBuilder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .lastBooking(bookingToGetItemBookingDto(lastBooking))
                .nextBooking(bookingToGetItemBookingDto(nextBooking))
                .comments(commentsToCommentsDtoList(comments))
                .requestId(item.getRequestId())
                .build();
    }

    public Comment createComment(CreateCommentDto commentDto, Item item, User author) {
        return Comment.builder()
                .text(commentDto.getText())
                .item(item)
                .author(author)
                .created(LocalDateTime.now())
                .build();
    }

    public List<ItemDto> toItemDtoForOwnerList(List<Item> items, List<Booking> bookings, Set<Comment> comments) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        for (Item item : items) {
            List<Booking> itemBookings = bookings.stream().filter(booking -> item.getId() == booking.getItem().getId()).collect(Collectors.toList());
            Set<Comment> itemComments = comments.stream().filter(comment -> item.getId() == comment.getItem().getId()).collect(Collectors.toSet());
            ItemDto itemDto = toItemDtoForOwner(item, itemBookings, itemComments);
            itemDtoList.add(itemDto);
        }
        return itemDtoList;
    }

    public Item fromItemDto(ItemDto itemDto, User owner) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .requestId(itemDto.getRequestId())
                .owner(owner).build();
    }

    public Item updateItemFromItemDto(Item actualItem, ItemDto newItem) {
        if (newItem.getName() != null && !newItem.getName().isBlank()) {
            actualItem.setName(newItem.getName());
        }
        if (newItem.getDescription() != null && !newItem.getDescription().isBlank()) {
            actualItem.setDescription(newItem.getDescription());
        }
        if (newItem.getAvailable() != null && newItem.getAvailable() != actualItem.getAvailable()) {
            actualItem.setAvailable(newItem.getAvailable());
        }
        return actualItem;
    }

    public List<ItemDto> itemsListToItemDto(List<Item> items, Set<Comment> comments) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        for (Item item : items) {
            Set<Comment> itemComments = comments.stream().filter(comment -> item.getId() == comment.getItem().getId()).collect(Collectors.toSet());
            ItemDto itemDto = toItemDto(item, itemComments);
            itemDtoList.add(itemDto);
        }
        return itemDtoList;
    }

}