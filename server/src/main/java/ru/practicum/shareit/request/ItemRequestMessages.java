package ru.practicum.shareit.request;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemRequestMessages {

    public static final String CREATE_ITEM_REQUEST = "Создан запрос на предмет: {}";
    public static final String GET_ITEM_REQUESTS = "Получен список запросов на предметы в размере: {}";
    public static final String GET_ALL_ITEM_REQUESTS = "Получен список всех запросов на предметы в размере: {}";
    public static final String GET_ITEM_REQUEST_BY_ID = "Получен запрос на предмет с id: {}";
}
