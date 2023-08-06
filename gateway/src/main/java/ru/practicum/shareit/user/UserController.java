package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.CreateGroup;
import ru.practicum.shareit.common.UpdateGroup;

import static ru.practicum.shareit.common.CommonForControllers.BY_ID_PATH;
import static ru.practicum.shareit.common.Messages.*;

@Controller
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserClient userClient;

    @PostMapping()
    public ResponseEntity<Object> createUser(@Validated({CreateGroup.class}) @RequestBody UserDto user) {
        log.info(RECEIVED_POST, "/users");
        return userClient.createUser(user);
    }

    @PatchMapping(BY_ID_PATH)
    public ResponseEntity<Object> updateUser(@PathVariable long id,
                              @Validated({UpdateGroup.class}) @RequestBody UserDto user) {
        log.info(RECEIVED_PATCH, "/users", id);
        return userClient.updateUser(id, user);
    }

    @GetMapping(BY_ID_PATH)
    public ResponseEntity<Object> getUser(@PathVariable long id) {
        log.info(RECEIVED_GET, "/users", id);
        return userClient.getUserDtoById(id);
    }

    @DeleteMapping(BY_ID_PATH)
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        log.info(RECEIVED_DELETE, "/users", id);
        return userClient.deleteUser(id);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllUsers() {
        log.info(RECEIVED_GET, "/users");
        return userClient.getAllUsers();
    }
}