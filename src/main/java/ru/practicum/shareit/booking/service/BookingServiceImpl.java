package ru.practicum.shareit.booking.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.repo.ItemRepo;
import ru.practicum.shareit.user.repo.UserRepo;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    UserRepo userRepo;
    ItemRepo itemRepo;

    @Override
    public BookingDto addBooking(long userId, BookingDto bookingDto) {
        return null;
    }
}
