package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepo;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    private UserService service;
    private final User userSuccess = new User(1, "Test", "Test@mail.ru");
    private final UserDto userDto = new UserDto(0, "Test", "Test@mail.ru");
    private final UserDto expectedUser = new UserDto(1, "Test", "Test@mail.ru");

    @Mock
    private UserRepo userRepo;

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

    @Test
    public void deleteUser() {
        service.deleteUser(1);
        Mockito.verify(userRepo, Mockito.times(1)).deleteById((long)1);
    }

    @Test
    public void getUserById() {
        when(userRepo.existsById(any())).thenReturn(true);
        when(userRepo.getReferenceById((long) 1)).thenReturn(userSuccess);

        UserDto getUser = service.getUserDtoById(1);
        assertEquals(expectedUser, getUser);
    }

    @Test
    public void getAllUser() {
        when(userRepo.findAll()).thenReturn(List.of(userSuccess));
        List<UserDto> createUser = service.getAllUsers();
        assertEquals(List.of(expectedUser), createUser);
    }
}
