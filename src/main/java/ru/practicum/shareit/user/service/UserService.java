package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {

    UserDto createUser(User user);

    UserDto updateUser(Long id, User user);

    void deleteUser(long id);

    UserDto getUser(long id);

    List<UserDto> getAllUsers();
}
