package ru.practicum.shareit.user;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMessages {

    public static final String CREATE_USER = "Пользователь создан: {}";
    public static final String DELETE_USER = "Пользователь удален id: {}";
    public static final String UPDATE_USER = "Пользователь изменен: {}";
    public static final String GET_USER = "Запрошен пользователь с id: {}";
    public static final String GET_USERS = "Запрошен список пользователей в размере: {}";
}
