package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.GetBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repo.BookingRepo;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.item.dto.GetBookingItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepo;
import ru.practicum.shareit.user.dto.GetBookingUserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepo;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.practicum.shareit.booking.model.BookingStatus.WAITING;

@SpringBootTest
public class BookingServiceTest {

    private final LocalDateTime startDate = LocalDateTime.now().plusHours(1);
    private final LocalDateTime endDate = LocalDateTime.now().plusHours(2);

    private BookingService bookingService;
    private final BookingDto bookingDto = new BookingDto(1, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
    private final User userSuccess = new User(1, "Test", "Test@mail.ru");
    private final User otherUser = new User(2, "Test2", "Test2@mail.ru");
    private final Item item = new Item(1, "Test", "TestD", true, otherUser, null);
    private final Booking booking = new Booking(1, startDate, endDate, item, otherUser, WAITING);

    GetBookingUserDto getBookingUserDto = new GetBookingUserDto(2);
    GetBookingItemDto getBookingItemDto = new GetBookingItemDto(1, "Test");
    private final GetBookingDto getBookingDto = new GetBookingDto(1, startDate, endDate, WAITING, getBookingUserDto, getBookingItemDto);


    @Mock
    private ItemRepo itemRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private BookingRepo bookingRepo;

    @BeforeEach
    void before() {
        bookingService = new BookingServiceImpl(userRepo, itemRepo, bookingRepo);
        when(userRepo.existsById(any())).thenReturn(true);
    }

    @Test
    public void addBookingTest() {
        when(itemRepo.existsById(any())).thenReturn(true);
        when(itemRepo.getReferenceById(any())).thenReturn(item);
        when(userRepo.getReferenceById(any())).thenReturn(userSuccess);
        when(bookingRepo.save(any())).thenReturn(booking);
        GetBookingDto createdBookingDto = bookingService.addBooking(1, bookingDto);
        assertEquals(getBookingDto, createdBookingDto);
    }
}
