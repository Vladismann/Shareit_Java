package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.AddItemRequestDto;

import javax.validation.Valid;

import static ru.practicum.shareit.common.CommonForControllers.BY_ID_PATH;
import static ru.practicum.shareit.common.CommonForControllers.USER_HEADER;
import static ru.practicum.shareit.common.Messages.RECEIVED_GET;
import static ru.practicum.shareit.common.Messages.RECEIVED_POST;

@RestController
@RequestMapping("/requests")
@Slf4j
@RequiredArgsConstructor
public class ItemRequestController {

    private final RequestClient requestClient;

    @PostMapping()
    public ResponseEntity<Object> createItem(@RequestHeader(USER_HEADER) long userId,
                                             @Valid @RequestBody AddItemRequestDto itemRequest) {
        log.info(RECEIVED_POST, "/requests");
        return requestClient.createRequest(userId, itemRequest);
    }

    @GetMapping()
    public ResponseEntity<Object> getOwnRequests(@RequestHeader(USER_HEADER) long userId) {
        log.info(RECEIVED_GET, "/requests");
        return requestClient.getUserRequests(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestHeader(USER_HEADER) long userId,
                                                  @RequestParam(defaultValue = "0") int from,
                                                  @RequestParam(defaultValue = "10") int size) {
        log.info(RECEIVED_GET, "/requests", "/all");
        return requestClient.getAllRequests(userId, from, size);
    }

    @GetMapping(BY_ID_PATH)
    public ResponseEntity<Object> getRequestById(@RequestHeader(USER_HEADER) long userId,
                                            @PathVariable long id) {
        log.info(RECEIVED_GET, "/requests", id);
        return requestClient.geById(userId, id);
    }
}
