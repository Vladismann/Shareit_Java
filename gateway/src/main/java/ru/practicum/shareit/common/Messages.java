package ru.practicum.shareit.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Messages {

    public static final String RECEIVED_GET = "Получен запрос GET {}/{}. Gateway";
    public static final String RECEIVED_POST = "Получен запрос POST {}. Gateway";
    public static final String RECEIVED_PATCH = "Получен запрос PATCH {}/{}. Gateway";
    public static final String RECEIVED_PUT = "Получен запрос PUT . Gateway";
    public static final String RECEIVED_DELETE = "Получен запрос DELETE {}/{}. Gateway";
    public static final String INCORRECT_DATA = "Проверьте корректность отправляемых данных. Gateway";
    public static final String INCORRECT_RESOURCE = "Объект не найден. Id: ";
}
