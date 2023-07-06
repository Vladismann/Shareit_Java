package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepo;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.user.UserMessages.INCORRECT_EMAIL;
import static ru.practicum.shareit.user.UserMessages.INCORRECT_USER;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private User getUserById(long id) {
        return userRepo.findById(id).orElseThrow(() -> {
            log.info(INCORRECT_USER + id);
            return new NotFoundException(INCORRECT_USER + id);
        });
    }

    private void checkEmailIsFree(String email) {
        if (userRepo.existsByEmail(email)) {
            log.info(INCORRECT_EMAIL + email);
            throw new IllegalArgumentException(INCORRECT_EMAIL + email);
        }
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.fromUserDto(userDto);
        return UserMapper.toUserDto(userRepo.save(user));
    }

    @Override
    public UserDto updateUser(Long id, UserDto newUserDto) {
        User actualUser = getUserById(id);
        actualUser = UserMapper.updateUserFromUserDto(actualUser, newUserDto);
        return UserMapper.toUserDto(userRepo.save(actualUser));
    }

    @Override
    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }

    @Override
    public UserDto getUserDtoById(long id) {
        return UserMapper.toUserDto(getUserById(id));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }
}
