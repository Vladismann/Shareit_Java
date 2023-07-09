package ru.practicum.shareit.item.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.dto.GetItemBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.booking.model.BookingStatus.APPROVED;

@UtilityClass
public class ItemMapper {

    public ItemDto toItemDto(Item item) {
        return new ItemDto.ItemDtoBuilder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public GetItemBookingDto bookingToGetItemBookingDto(Booking booking) {
        if (booking.getId() != 0) {
            return GetItemBookingDto.builder()
                    .id(booking.getId())
                    .bookerId(booking.getId())
                    .build();
        } else {
            return new GetItemBookingDto();
        }
    }

    public ItemDto toItemDtoForOwner(Item item) {
        LocalDateTime currentTime = LocalDateTime.now();
        Booking lastBooking = new Booking();
        Booking nextBooking = new Booking();
        List<Booking> bookings = item.getBookings();
        if (!bookings.isEmpty()) {
            lastBooking = bookings.stream()
                    .filter(booking -> booking.getStart().isBefore(currentTime) && booking.getStatus().equals(APPROVED))
                    .findFirst()
                    .orElse(null);
            nextBooking = bookings.stream()
                    .filter(booking -> booking.getStart().isAfter(currentTime) && booking.getStatus().equals(APPROVED))
                    .findFirst()
                    .orElse(null);
        }

        return new ItemDto.ItemDtoBuilder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .lastBooking(bookingToGetItemBookingDto(lastBooking))
                .nextBooking(bookingToGetItemBookingDto(nextBooking))
                .build();
    }

    public List<ItemDto> toItemDtoForOwnerList(List<Item> items) {
        return items.stream().map(ItemMapper::toItemDtoForOwner).collect(Collectors.toList());
    }

    public Item fromItemDto(ItemDto itemDto, User owner) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
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

    public List<ItemDto> itemsListToItemDto(List<Item> items) {
        return items.stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

}