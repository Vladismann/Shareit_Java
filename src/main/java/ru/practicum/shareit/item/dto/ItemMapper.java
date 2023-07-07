package ru.practicum.shareit.item.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

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