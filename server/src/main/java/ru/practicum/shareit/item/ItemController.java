package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.CustomPageRequest;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CreateCommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.shareit.common.CommonForControllers.*;
import static ru.practicum.shareit.common.Messages.*;
import static ru.practicum.shareit.item.ItemPaths.COMMENT_PATH;
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
        log.info(RECEIVED_POST, ITEMS_PATH);
        return itemService.createItem(userId, item);
    }

    @PatchMapping(BY_ID_PATH)
    public ItemDto updateItem(@RequestHeader(USER_HEADER) long userId,
                              @PathVariable long id,
                              @RequestBody ItemDto item) {
        log.info(RECEIVED_PATCH, ITEMS_PATH, id);
        return itemService.updateItem(userId, id, item);
    }

    @GetMapping(BY_ID_PATH)
    public ItemDto getItem(@RequestHeader(USER_HEADER) long userId,
                           @PathVariable long id) {
        log.info(RECEIVED_GET, ITEMS_PATH, id);
        return itemService.getItem(userId, id);
    }

    @GetMapping()
    public List<ItemDto> getUserItems(@RequestHeader(USER_HEADER) long userId,
                                      @RequestParam(defaultValue = "0") int from,
                                      @RequestParam(defaultValue = "10") int size) {
        log.info(RECEIVED_GET, ITEMS_PATH, USER_HEADER + userId);
        Pageable paging = new CustomPageRequest(from, size, Sort.by("id"));
        return itemService.getAllByUserId(userId, paging);
    }

    @GetMapping(SEARCH_PATH)
    public List<ItemDto> searchItems(@RequestHeader(USER_HEADER) long userId,
                                     @RequestParam String text,
                                     @RequestParam(defaultValue = "0") int from,
                                     @RequestParam(defaultValue = "10") int size) {
        log.info(RECEIVED_GET, ITEMS_PATH, SEARCH_PATH + USER_HEADER + userId);
        Pageable paging = new CustomPageRequest(from, size, Sort.by("id"));
        return itemService.searchItemByText(text, paging);
    }

    @PostMapping(BY_ID_PATH + COMMENT_PATH)
    public CommentDto createComment(@RequestHeader(USER_HEADER) long userId,
                                    @PathVariable long id,
                                    @Valid @RequestBody CreateCommentDto comment) {
        log.info(RECEIVED_POST, ITEMS_PATH);
        return itemService.addComment(userId, id, comment);
    }

}