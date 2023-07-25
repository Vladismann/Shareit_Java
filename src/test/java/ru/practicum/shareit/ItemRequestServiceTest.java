package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.dto.GetItemRequestItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepo;
import ru.practicum.shareit.request.dto.AddItemRequestDto;
import ru.practicum.shareit.request.dto.CreatedItemRequestDto;
import ru.practicum.shareit.request.dto.GetItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repo.ItemRequestRepo;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.request.service.ItemRequestServiceImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepo;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ItemRequestServiceTest {


    private ItemRequestService itemRequestService;

    private final LocalDateTime created = LocalDateTime.now();
    private final AddItemRequestDto addItemRequestDto = new AddItemRequestDto("TestD");
    private final CreatedItemRequestDto createdItemRequestDto = new CreatedItemRequestDto((long) 1, "TestD", created);
    private final ItemRequest itemRequest = new ItemRequest(1, "TestD", 1, created);
    private final User userOwner = new User(1, "Test", "Test@mail.ru");
    private final Item item = new Item(1, "Test", "TestD", true, userOwner, (long) 1);
    private final GetItemRequestItemDto getItemRequestItemDto = new GetItemRequestItemDto(1, "Test", "TestD", true, 1);
    private final GetItemRequestDto getItemRequestDto = new GetItemRequestDto(1, "TestD", created, List.of(getItemRequestItemDto));

    @Mock
    private ItemRepo itemRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private ItemRequestRepo itemRequestRepo;

    @BeforeEach
    void before() {
        itemRequestService = new ItemRequestServiceImpl(itemRepo, itemRequestRepo, userRepo);
        when(userRepo.existsById(any())).thenReturn(true);
    }

    @Test
    public void createRequest() {
        when(itemRequestRepo.save(any())).thenReturn(itemRequest);

        CreatedItemRequestDto actualItemRequest = itemRequestService.createRequest(1, addItemRequestDto);
        assertEquals(createdItemRequestDto, actualItemRequest);
    }

    @Test
    public void geRequestById() {
        when(itemRequestRepo.existsById(any())).thenReturn(true);
        when(itemRequestRepo.getReferenceById(any())).thenReturn(itemRequest);
        when(itemRepo.findAllByRequestIdIn(List.of((long) 1))).thenReturn(List.of(item));

        GetItemRequestDto actualItemRequest = itemRequestService.geById(1, 1);
        assertEquals(getItemRequestDto, actualItemRequest);
    }
}
