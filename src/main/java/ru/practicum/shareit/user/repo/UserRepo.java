package ru.practicum.shareit.user.repo;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepo {

    void checkUserIsExist(long id);

    UserDto create(User user);

    UserDto update(User user);

    void delete(long id);

    UserDto get(long id);

    List<UserDto> getAll();
}
