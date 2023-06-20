package ru.practicum.shareit.user;

import java.util.List;

public interface UserRepo {

    UserDto create(User user);

    UserDto update(User user);

    void delete(long id);

    UserDto get(long id);

    List<UserDto> getAll();
}
