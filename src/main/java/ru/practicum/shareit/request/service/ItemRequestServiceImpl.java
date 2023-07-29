package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.common.CommonMethods;
import ru.practicum.shareit.item.dto.GetItemRequestItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepo;
import ru.practicum.shareit.request.dto.AddItemRequestDto;
import ru.practicum.shareit.request.dto.CreatedItemRequestDto;
import ru.practicum.shareit.request.dto.GetItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repo.ItemRequestRepo;
import ru.practicum.shareit.user.repo.UserRepo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.request.ItemRequestMessages.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRepo itemRepo;
    private final ItemRequestRepo itemRequestRepo;
    private final UserRepo userRepo;

    @Override
    public CreatedItemRequestDto createRequest(long userId, AddItemRequestDto addItemRequestDto) {
        CommonMethods.checkResourceIsExists(userId, userRepo);
        ItemRequest itemRequest = itemRequestRepo.save(ItemRequestMapper.toItemRequest(userId, addItemRequestDto));
        log.info(CREATE_ITEM_REQUEST, itemRequest);
        return ItemRequestMapper.toGetItemRequest(itemRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetItemRequestDto> getUserRequests(long userId) {
        CommonMethods.checkResourceIsExists(userId, userRepo);
        List<ItemRequest> requests = itemRequestRepo.findByRequestor(userId);
        if (requests.isEmpty()) {
            log.info(GET_ITEM_REQUESTS, 0);
            return List.of();
        }
        List<Long> requestsIds = requests.stream().map(ItemRequest::getId).collect(Collectors.toList());
        List<Item> items = itemRepo.findAllByRequestIdIn(requestsIds);
        List<GetItemRequestItemDto> getItemRequestItemDto = ItemMapper.toListGetItemRequestItemDto(items);
        List<GetItemRequestDto> getItemRequestDto = ItemRequestMapper.toGetItemRequestDto(requests, getItemRequestItemDto);
        log.info(GET_ITEM_REQUESTS, getItemRequestDto.size());
        return getItemRequestDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetItemRequestDto> getAllRequests(long userId, Pageable pageable) {
        CommonMethods.checkResourceIsExists(userId, userRepo);
        List<ItemRequest> requests = itemRequestRepo.findByRequestorNot(userId, pageable);
        if (requests.isEmpty()) {
            log.info(GET_ALL_ITEM_REQUESTS, 0);
            return List.of();
        }
        List<Long> requestsIds = requests.stream().map(ItemRequest::getId).collect(Collectors.toList());
        List<Item> items = itemRepo.findAllByRequestIdIn(requestsIds);
        List<GetItemRequestItemDto> getItemRequestItemDto = ItemMapper.toListGetItemRequestItemDto(items);
        List<GetItemRequestDto> getItemRequestDto = ItemRequestMapper.toGetItemRequestDto(requests, getItemRequestItemDto);
        log.info(GET_ALL_ITEM_REQUESTS, getItemRequestDto.size());
        return getItemRequestDto;
    }

    @Transactional(readOnly = true)
    @Override
    public GetItemRequestDto geById(long userId, long requestId) {
        CommonMethods.checkResourceIsExists(userId, userRepo);
        CommonMethods.checkResourceIsExists(requestId, itemRequestRepo);
        log.info(GET_ITEM_REQUEST_BY_ID, requestId);
        ItemRequest request = itemRequestRepo.getReferenceById(requestId);
        List<Item> items = itemRepo.findAllByRequestIdIn(Collections.singletonList(request.getId()));
        List<GetItemRequestItemDto> getItemRequestItemDto = ItemMapper.toListGetItemRequestItemDto(items);
        return ItemRequestMapper.toGetItemRequestDto(request, getItemRequestItemDto);
    }

}
