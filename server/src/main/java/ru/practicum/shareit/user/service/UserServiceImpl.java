package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.common.CommonMethods;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepo;

import java.util.List;

import static ru.practicum.shareit.user.UserMessages.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.fromUserDto(userDto);
        UserDto createdUserDto = UserMapper.toUserDto(userRepo.save(user));
        log.info(CREATE_USER, createdUserDto);
        return createdUserDto;
    }

    @Override
    public UserDto updateUser(Long id, UserDto newUserDto) {
        CommonMethods.checkResourceIsExists(id, userRepo);
        User actualUser = userRepo.getReferenceById(id);
        actualUser = UserMapper.updateUserFromUserDto(actualUser, newUserDto);
        UserDto updatedUserDto = UserMapper.toUserDto(userRepo.save(actualUser));
        log.info(UPDATE_USER, updatedUserDto);
        return updatedUserDto;
    }

    @Override
    public void deleteUser(long id) {
        log.info(DELETE_USER, id);
        userRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserDtoById(long id) {
        CommonMethods.checkResourceIsExists(id, userRepo);
        log.info(GET_USER, id);
        return UserMapper.toUserDto(userRepo.getReferenceById(id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> users = UserMapper.listUserToUserDto(userRepo.findAll());
        log.info(GET_USERS, users.size());
        return users;
    }
}