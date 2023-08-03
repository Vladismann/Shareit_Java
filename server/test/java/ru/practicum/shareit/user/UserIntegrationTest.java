package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase
@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserIntegrationTest {

    private final UserService userService;
    private final UserDto userDto = new UserDto(0, "Test", "Test@mail.ru");

    @DirtiesContext
    @Test
    public void getAllUsers() {
        UserDto dto1 = userService.createUser(userDto);
        userDto.setEmail("Test2@mail.ru");
        UserDto dto2 = userService.createUser(userDto);

        List<UserDto> userDtoList = userService.getAllUsers();

        assertEquals(List.of(dto1, dto2), userDtoList);
    }
}
