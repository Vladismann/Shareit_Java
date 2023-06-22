package ru.practicum.shareit.user.repo;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserRepo {

    void checkUserIsExist(long id);

    UserDto create(UserDto user);

    UserDto update(UserDto user);

    void delete(long id);

    UserDto get(long id);

    List<UserDto> getAll();
}
