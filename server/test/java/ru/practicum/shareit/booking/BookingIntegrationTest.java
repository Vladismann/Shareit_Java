package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.GetBookingDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.common.CustomPageRequest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase
@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingIntegrationTest {

    private final BookingService bookingService;
    private final ItemService itemService;
    private final UserService userService;

    private final UserDto userDto = new UserDto(0, "Test", "Test@mail.ru");
    private final ItemDto itemDto
            = new ItemDto(0, "Test", "TestD", true, null, null, null, null);
    private final BookingDto bookingDto = new BookingDto(1, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
    private final Pageable pageable = new CustomPageRequest(0, 10, Sort.by("start"));

    @DirtiesContext
    @Test
    public void getBookerBookings() {
        userService.createUser(userDto);
        userDto.setEmail("Test2@mail.ru");
        userService.createUser(userDto);
        itemService.createItem(1, itemDto);
        itemService.createItem(1, itemDto);
        GetBookingDto getBookingDto1 = bookingService.addBooking(2, bookingDto);
        bookingDto.setItemId(2);
        GetBookingDto getBookingDto2 = bookingService.addBooking(2, bookingDto);

        List<GetBookingDto> getBookingDto = bookingService.getAllBookingsByBooker(2, "ALL",  pageable);

        assertEquals(List.of(getBookingDto1, getBookingDto2), getBookingDto);
    }
}
