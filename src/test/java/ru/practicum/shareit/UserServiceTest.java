package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepo;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    UserService service;
    User userSuccess = new User(1, "Test", "Test@mail.ru");
    UserDto userDto = new UserDto(0, "Test", "Test@mail.ru");
    UserDto expectedUser = new UserDto(1, "Test", "Test@mail.ru");

    @Mock
    UserRepo userRepo;

    @BeforeEach
    void before() {
        service = new UserServiceImpl(userRepo);
    }

    @Test
    public void saveUser() {
        when(userRepo.save(any())).thenReturn(userSuccess);
        UserDto createUser = service.createUser(userDto);
        assertEquals(expectedUser, createUser);
    }

    @Test
    public void updateUser() {
        when(userRepo.existsById(any())).thenReturn(true);
        when(userRepo.getReferenceById((long) 1)).thenReturn(userSuccess);
        when(userRepo.save(any())).thenReturn(userSuccess);
        UserDto updatedUser = service.updateUser((long)1, userDto);
        assertEquals(expectedUser, updatedUser);
    }


}
