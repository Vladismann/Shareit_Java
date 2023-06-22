package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repo.UserRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public UserDto createUser(UserDto user) {
        return userRepo.create(user);
    }

    @Override
    public UserDto updateUser(Long id, UserDto user) {
        user.setId(id);
        return userRepo.update(user);
    }

    @Override
    public void deleteUser(long id) {
        userRepo.delete(id);
    }

    @Override
    public UserDto getUser(long id) {
        return userRepo.get(id);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepo.getAll();
    }
}
