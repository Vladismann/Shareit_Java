package ru.practicum.shareit.booking;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BookingMessages {

    public static final String INCORRECT_BOOKING_DATE = "Проверьте корректность дат бронирования";
    public static final String INCORRECT_BOOKING = "Бронирование на указаные даты недоступно";
    public static final String INCORRECT_BOOKING_STATE = "Unknown state: UNSUPPORTED_STATUS";
    public static final String ALREADY_APPROVED = "Бронирование уже было подтверждено";
    public static final String CREATE_BOOKING = "Создано бронирование: {}";
    public static final String APPROVE_BOOKING = "Изменен статус бронирования: {}";
    public static final String GET_BOOKING = "Запрошено бронирование {}";
    public static final String GET_BOOKINGS = "Запрошен список бронирований размере {}";
}
