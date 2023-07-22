package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.AddItemRequestDto;
import ru.practicum.shareit.request.dto.CreatedItemRequestDto;
import ru.practicum.shareit.request.dto.GetItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.shareit.common.CommonForControllers.BY_ID_PATH;
import static ru.practicum.shareit.common.CommonForControllers.USER_HEADER;
import static ru.practicum.shareit.common.Messages.RECEIVED_GET;
import static ru.practicum.shareit.common.Messages.RECEIVED_POST;
import static ru.practicum.shareit.request.ItemRequestPaths.GET_ALL_ITEM_REQUEST_PATH;
import static ru.practicum.shareit.request.ItemRequestPaths.ITEM_REQUEST_PATH;

@RestController
@RequestMapping(ITEM_REQUEST_PATH)
@Slf4j
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping()
    public CreatedItemRequestDto createItem(@RequestHeader(USER_HEADER) long userId,
                                            @Valid @RequestBody AddItemRequestDto itemRequest) {
        log.info(RECEIVED_POST, ITEM_REQUEST_PATH);
        return itemRequestService.createRequest(userId, itemRequest);
    }

    @GetMapping()
    public List<GetItemRequestDto> getOwnRequests(@RequestHeader(USER_HEADER) long userId) {
        log.info(RECEIVED_GET, ITEM_REQUEST_PATH);
        return itemRequestService.getUserRequests(userId);
    }

    @GetMapping(GET_ALL_ITEM_REQUEST_PATH)
    public List<GetItemRequestDto> getAllRequests(@RequestHeader(USER_HEADER) long userId,
                                                  @RequestParam(defaultValue = "0") int from,
                                                  @RequestParam(defaultValue = "10") int size) {
        log.info(RECEIVED_GET, ITEM_REQUEST_PATH, GET_ALL_ITEM_REQUEST_PATH);
        Pageable paging = PageRequest.of(from, size, Sort.by("created"));
        return itemRequestService.getAllRequests(userId, paging);
    }

    @GetMapping(BY_ID_PATH)
    public GetItemRequestDto getAllRequests(@RequestHeader(USER_HEADER) long userId,
                                            @PathVariable long id) {
        log.info(RECEIVED_GET, ITEM_REQUEST_PATH, id);
        return itemRequestService.geById(userId, id);
    }
}
