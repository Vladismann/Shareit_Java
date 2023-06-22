package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto user);

    UserDto updateUser(Long id, UserDto user);

    void deleteUser(long id);

    UserDto getUser(long id);

    List<UserDto> getAllUsers();
}
