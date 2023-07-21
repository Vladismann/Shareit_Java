package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.GetItemRequestDto;

public interface ItemRequestService {

    GetItemRequestDto createRequest(long userId, CreateItemRequestDto createItemRequestDto);

    GetItemRequestDto getUserRequests(long userId);
}
