package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ValidationException;

import java.util.List;

import static ru.practicum.shareit.user.UserMessages.EMPTY_EMAIL;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public void checkEmailIsNotBlank(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ValidationException(EMPTY_EMAIL);
        }
    }

    @Override
    public UserDto createUser(User user) {
        checkEmailIsNotBlank(user);
        return userRepo.create(user);
    }

    @Override
    public UserDto updateUser(Long id, User user) {
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
