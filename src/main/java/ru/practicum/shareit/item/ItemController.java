package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.shareit.common.CommonForControllers.*;
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
                              @Valid @RequestBody ItemDto item) {
        log.info(RECEIVED_POST + ITEMS_PATH);
        return itemService.createItem(userId, item);
    }

    @PatchMapping(BY_ID_PATH)
    public ItemDto updateItem(@RequestHeader(USER_HEADER) long userId,
                              @PathVariable long id,
                              @RequestBody ItemDto item) {
        log.info(RECEIVED_PATCH + ITEMS_PATH + "/" + id);
        return itemService.updateItem(userId, id, item);
    }

    @GetMapping(BY_ID_PATH)
    public ItemDto getItem(@PathVariable long id) {
        log.info(RECEIVED_GET + ITEMS_PATH + "/" + id);
        return itemService.getItem(id);
    }

    @GetMapping()
    public List<ItemDto> getUserItems(@RequestHeader(USER_HEADER) long userId) {
        log.info(RECEIVED_GET + ITEMS_PATH + USER_HEADER + userId);
        return itemService.getAllByUserId(userId);
    }

    @GetMapping(SEARCH_PATH)
    public List<ItemDto> searchItems(@RequestHeader(USER_HEADER) long userId,
                                     @RequestParam String text) {
        log.info(RECEIVED_GET + ITEMS_PATH + SEARCH_PATH + USER_HEADER + userId);
        return itemService.searchItemByText(text);
    }

}
