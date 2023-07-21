package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.GetItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import javax.validation.Valid;

import static ru.practicum.shareit.common.CommonForControllers.USER_HEADER;
import static ru.practicum.shareit.common.Messages.RECEIVED_GET;
import static ru.practicum.shareit.common.Messages.RECEIVED_POST;
import static ru.practicum.shareit.request.ItemRequestPaths.ITEM_REQUEST_PATH;

@RestController
@RequestMapping(ITEM_REQUEST_PATH)
@Slf4j
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping()
    public GetItemRequestDto createItem(@RequestHeader(USER_HEADER) long userId,
                                        @Valid @RequestBody CreateItemRequestDto itemRequest) {
        log.info(RECEIVED_POST, ITEM_REQUEST_PATH);
        return itemRequestService.createRequest(userId, itemRequest);
    }

    @GetMapping()
    public GetItemRequestDto getOwnRequest(@RequestHeader(USER_HEADER) long userId) {
        log.info(RECEIVED_GET, ITEM_REQUEST_PATH);
        return itemRequestService.getUserRequests(userId);
    }
}
