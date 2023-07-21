package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.common.CommonMethods;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.GetItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repo.ItemRequestRepo;
import ru.practicum.shareit.user.repo.UserRepo;

import static ru.practicum.shareit.request.ItemRequestMessages.CREATE_ITEM_REQUEST;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepo itemRequestRepo;
    private final UserRepo userRepo;

    @Override
    public GetItemRequestDto createRequest(long userId, CreateItemRequestDto createItemRequestDto) {
        CommonMethods.checkResourceIsExists(userId, userRepo);
        ItemRequest itemRequest = itemRequestRepo.save(ItemRequestMapper.toItemRequest(userId, createItemRequestDto));
        log.info(CREATE_ITEM_REQUEST, itemRequest);
        return ItemRequestMapper.toGetItemRequest(itemRequest);
    }

    @Override
    public GetItemRequestDto getUserRequests(long userId) {
        CommonMethods.checkResourceIsExists(userId, userRepo);
        return null;
    }
}
