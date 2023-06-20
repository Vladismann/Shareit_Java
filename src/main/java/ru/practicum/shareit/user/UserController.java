package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.shareit.common.Messages.*;
import static ru.practicum.shareit.user.UserPaths.BY_ID_PATH;
import static ru.practicum.shareit.user.UserPaths.USERS_PATH;

@RestController
@RequestMapping(USERS_PATH)
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public UserDto createUser(@Valid @RequestBody User user) {
        log.info(RECEIVED_POST + USERS_PATH);
        return userService.createUser(user);
    }

    @PatchMapping(BY_ID_PATH)
    public UserDto updateUser(@PathVariable long id,
                              @Valid @RequestBody User user) {
        log.info(RECEIVED_PATCH + USERS_PATH + "/" + id);
        return userService.updateUser(id, user);
    }

    @GetMapping(BY_ID_PATH)
    public UserDto getUser(@PathVariable long id) {
        log.info(RECEIVED_GET + USERS_PATH + "/" + id);
        return userService.getUser(id);
    }

    @DeleteMapping(BY_ID_PATH)
    public void deleteUser(@PathVariable long id) {
        log.info(RECEIVED_DELETE + USERS_PATH + "/" + id);
        userService.deleteUser(id);
    }

    @GetMapping()
    public List<UserDto> getAllUsers() {
        log.info(RECEIVED_GET + USERS_PATH);
        return userService.getAllUsers();
    }
}