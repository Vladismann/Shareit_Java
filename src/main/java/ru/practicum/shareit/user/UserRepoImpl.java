package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.user.UserMapper.toUserDto;
import static ru.practicum.shareit.user.UserMessages.*;

@Repository
@Slf4j
public class UserRepoImpl implements UserRepo {
    private final LinkedHashMap<Long, User> users = new LinkedHashMap<>();
    private static long userId = 1;

    public void checkEmailIsNotBlank(User user) {
        if (user.getEmail().isBlank()) {
            throw new ValidationException(EMPTY_EMAIL);
        }
    }

    public void checkEmailIsFree(User user) {
        String actualEmail = user.getEmail();
        if (users.values().stream().anyMatch(actualUser -> actualUser.getEmail().equals(actualEmail))) {
            log.info(INCORRECT_EMAIL + actualEmail);
            throw new ValidationException(INCORRECT_EMAIL + actualEmail);
        }
    }

    public void checkUserIsExist(long id) {
        if (!users.containsKey(id)) {
            log.info(INCORRECT_USER + id);
            throw new NotFoundException(INCORRECT_USER + id);
        }
    }

    @Override
    public UserDto create(User user) {
        checkEmailIsNotBlank(user);
        checkEmailIsFree(user);
        user.setId(userId++);
        users.put(user.getId(), user);
        log.info(CREATE_USER, user);
        return toUserDto(user);
    }

    @Override
    public UserDto update(User user) {
        long id = user.getId();
        checkUserIsExist(id);
        User actualUser = users.get(id);
        if (!user.getEmail().isBlank() && !actualUser.getEmail().equals(user.getEmail())) {
            checkEmailIsFree(user);
            actualUser.setEmail(user.getEmail());
        }
        if (!user.getName().isBlank()) {
            actualUser.setName(user.getName());
        }
        users.put(id, actualUser);
        log.info(UPDATE_USER, actualUser);
        return toUserDto(actualUser);
    }

    @Override
    public void delete(long id) {
        checkUserIsExist(id);
        users.remove(id);
        log.info(DELETE_USER, id);
    }

    @Override
    public UserDto get(long id) {
        checkUserIsExist(id);
        log.info(GET_USER, id);
        return toUserDto(users.get(id));
    }

    @Override
    public List<UserDto> getAll() {
        log.info(GET_USERS, users.values().size());
        return users.values().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }
}
