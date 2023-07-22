package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.AddItemRequestDto;
import ru.practicum.shareit.request.dto.CreatedItemRequestDto;
import ru.practicum.shareit.request.dto.GetItemRequestDto;

import java.util.List;

public interface ItemRequestService {

    CreatedItemRequestDto createRequest(long userId, AddItemRequestDto addItemRequestDto);

    List<GetItemRequestDto> getUserRequests(long userId);
}
