package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CreateCommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;

import static ru.practicum.shareit.common.CommonForControllers.*;
import static ru.practicum.shareit.common.Messages.*;

@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemClient itemClient;

    @PostMapping()
    public ResponseEntity<Object> createItem(@RequestHeader(USER_HEADER) long userId,
                                             @Valid @RequestBody ItemDto item) {
        log.info(RECEIVED_POST, "/items");
        return itemClient.createItem(userId, item);
    }

    @PatchMapping(BY_ID_PATH)
    public ResponseEntity<Object> updateItem(@RequestHeader(USER_HEADER) long userId,
                              @PathVariable long id,
                              @RequestBody ItemDto item) {
        log.info(RECEIVED_PATCH, "/items", id);
        return itemClient.updateItem(userId, id, item);
    }

    @GetMapping(BY_ID_PATH)
    public ResponseEntity<Object> getItem(@RequestHeader(USER_HEADER) long userId,
                           @PathVariable long id) {
        log.info(RECEIVED_GET, "/items", id);
        return itemClient.getItem(userId, id);
    }

    @GetMapping()
    public ResponseEntity<Object> getUserItems(@RequestHeader(USER_HEADER) long userId,
                                      @RequestParam(defaultValue = "0") int from,
                                      @RequestParam(defaultValue = "10") int size) {
        log.info(RECEIVED_GET, "/items", USER_HEADER + userId);
        return itemClient.getAllByUserId(userId, from, size);
    }

    @GetMapping(SEARCH_PATH)
    public ResponseEntity<Object> searchItems(@RequestHeader(USER_HEADER) long userId,
                                     @RequestParam String text,
                                     @RequestParam(defaultValue = "0") int from,
                                     @RequestParam(defaultValue = "10") int size) {
        log.info(RECEIVED_GET, "/items", SEARCH_PATH + USER_HEADER + userId);
        return itemClient.searchItemByText(text, from, size);
    }

    @PostMapping(BY_ID_PATH + "/comment")
    public ResponseEntity<Object> createComment(@RequestHeader(USER_HEADER) long userId,
                                    @PathVariable long id,
                                    @Valid @RequestBody CreateCommentDto comment) {
        log.info(RECEIVED_POST, "/items");
        return itemClient.addComment(userId, id, comment);
    }

}