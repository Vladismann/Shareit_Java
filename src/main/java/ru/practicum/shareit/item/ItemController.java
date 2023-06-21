package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;

import static ru.practicum.shareit.common.CommonForControllers.BY_ID_PATH;
import static ru.practicum.shareit.common.CommonForControllers.USER_HEADER;
import static ru.practicum.shareit.common.Messages.*;
import static ru.practicum.shareit.item.ItemPaths.ITEMS_PATH;

@RestController
@RequestMapping(ITEMS_PATH)
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping()
    public ItemDto createItem(@RequestHeader(USER_HEADER) long userId,
                              @Valid @RequestBody Item item) {
        log.info(RECEIVED_POST + ITEMS_PATH);
        return itemService.createItem(userId, item);
    }

    @PatchMapping(BY_ID_PATH)
    public ItemDto updateUser(@RequestHeader(USER_HEADER) long userId,
                              @PathVariable long id,
                              @Valid @RequestBody Item item) {
        log.info(RECEIVED_PATCH + ITEMS_PATH + "/" + id);
        return itemService.updateItem(userId, id, item);
    }

    @GetMapping(BY_ID_PATH)
    public ItemDto updateItem(@PathVariable long id) {
        log.info(RECEIVED_GET + ITEMS_PATH + "/" + id);
        return itemService.getItem(id);
    }

}
