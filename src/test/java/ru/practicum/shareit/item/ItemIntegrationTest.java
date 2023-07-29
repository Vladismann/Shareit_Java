package ru.practicum.shareit.item;


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
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase
@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemIntegrationTest {

    private final ItemService itemService;
    private final UserService userService;
    private final UserDto userDto = new UserDto(0, "Test", "Test@mail.ru");
    private final ItemDto itemDto
            = new ItemDto(0, "Test", "TestD", true, null, null, null, null);
    private final Pageable pageable = new CustomPageRequest(0, 10, Sort.by("id"));

    @DirtiesContext
    @Test
    public void getUserItems() {
        userService.createUser(userDto);
        ItemDto dto1 = itemService.createItem(1, itemDto);
        ItemDto dto2 = itemService.createItem(1, itemDto);
        dto1.setComments(new HashSet<>());
        dto2.setComments(new HashSet<>());

        List<ItemDto> itemDtoList = itemService.getAllByUserId(1, pageable);

        assertEquals(List.of(dto1, dto2), itemDtoList);
    }
}
