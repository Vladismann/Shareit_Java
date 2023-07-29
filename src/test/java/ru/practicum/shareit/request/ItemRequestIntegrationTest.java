package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.common.CustomPageRequest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.dto.AddItemRequestDto;
import ru.practicum.shareit.request.dto.GetItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase
@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRequestIntegrationTest {

    private final ItemRequestService itemRequestService;
    private final UserService userService;
    private final ItemService itemService;

    private final UserDto userDto = new UserDto(0, "Test", "Test@mail.ru");
    private final AddItemRequestDto addItemRequestDto = new AddItemRequestDto("TestD");
    private final ItemDto itemDto
            = new ItemDto(0, "Test", "TestD", true, null, null, null, (long)1);
    private final Pageable pageable = new CustomPageRequest(0, 10, Sort.by("created"));

    @DirtiesContext
    @Test
    public void getUserRequests() {
        userService.createUser(userDto);
        userDto.setEmail("Test2@mail.ru");
        userService.createUser(userDto);

        itemRequestService.createRequest(2, addItemRequestDto);
        itemRequestService.createRequest(2, addItemRequestDto);

        itemService.createItem(1, itemDto);
        itemDto.setRequestId((long)2);
        itemService.createItem(1, itemDto);

        GetItemRequestDto dto1 = itemRequestService.geById(2, 1);
        GetItemRequestDto dto2 = itemRequestService.geById(2, 2);
        List<GetItemRequestDto> getItemRequestDto = itemRequestService.getAllRequests(1, pageable);

        assertEquals(List.of(dto1, dto2), getItemRequestDto);
    }
}
