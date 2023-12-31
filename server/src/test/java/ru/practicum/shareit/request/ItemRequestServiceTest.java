package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.common.CustomPageRequest;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ItemRequestServiceTest {


    private ItemRequestService itemRequestService;

    private final LocalDateTime created = LocalDateTime.now();
    private final User userOwner = new User(1, "Test", "Test@mail.ru");
    private final ItemRequest itemRequest = new ItemRequest(1, "TestD", 1, created);
    private final Item item = new Item(1, "Test", "TestD", true, userOwner, (long) 1);
    private final GetItemRequestItemDto getItemRequestItemDto = new GetItemRequestItemDto(1, "Test", "TestD", true, 1);

    private final AddItemRequestDto addItemRequestDto = new AddItemRequestDto("TestD");
    private final CreatedItemRequestDto createdItemRequestDto = new CreatedItemRequestDto((long) 1, "TestD", created);
    private final GetItemRequestDto getItemRequestDto = new GetItemRequestDto(1, "TestD", created, List.of(getItemRequestItemDto));
    private final Pageable pageable = new CustomPageRequest(1, 1, Sort.by("Test"));

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

    @Test
    public void geUserRequest() {
        when(itemRequestRepo.existsById(any())).thenReturn(true);
        when(itemRequestRepo.findByRequestorNot(anyLong(), any())).thenReturn(List.of(itemRequest));
        when(itemRepo.findAllByRequestIdIn(List.of((long) 1))).thenReturn(List.of(item));

        List<GetItemRequestDto> actualItemRequest = itemRequestService.getAllRequests(1, pageable);
        assertEquals(List.of(getItemRequestDto), actualItemRequest);
    }

    @Test
    public void geUserRequestEmpty() {
        when(itemRequestRepo.existsById(any())).thenReturn(true);
        when(itemRequestRepo.findByRequestorNot(anyLong(), any())).thenReturn(List.of());

        List<GetItemRequestDto> actualItemRequest = itemRequestService.getAllRequests(1, pageable);
        assertEquals(0, actualItemRequest.size());
    }

    @Test
    public void geUserAllRequest() {
        when(itemRequestRepo.existsById(any())).thenReturn(true);
        when(itemRequestRepo.findByRequestor(anyLong())).thenReturn(List.of(itemRequest));
        when(itemRepo.findAllByRequestIdIn(List.of((long) 1))).thenReturn(List.of(item));

        List<GetItemRequestDto> actualItemRequest = itemRequestService.getUserRequests(1);
        assertEquals(List.of(getItemRequestDto), actualItemRequest);
    }

    @Test
    public void geUserAllRequestEmpty() {
        when(itemRequestRepo.existsById(any())).thenReturn(true);
        when(itemRequestRepo.findByRequestor(anyLong())).thenReturn(List.of());

        List<GetItemRequestDto> actualItemRequest = itemRequestService.getUserRequests(1);
        assertEquals(0, actualItemRequest.size());
    }

}
