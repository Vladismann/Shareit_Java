package ru.practicum.shareit.item;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemMessages {

    public static final String INCORRECT_ITEM = "Предмет не существует. Id: ";
    public static final String INCORRECT_OWNER = "Попытка обновить предмет с id: {} пользователем с id: {}";
    public static final String EMPTY_ITEM_SEARCH = "Укажите текст для поиска";
    public static final String NO_BOOKING_COMMENT = "Для указания комментария, необходимо арендовать предмет и завершить аренду";
    public static final String CREATE_ITEM = "Предмет создан: {}";
    public static final String UPDATE_ITEM = "Предмет изменен: {}";
    public static final String GET_ITEM = "Запрошен предмет с id: {}";
    public static final String GET_ITEMS = "Запрошен список предметов пользователя с id: {}";
}
