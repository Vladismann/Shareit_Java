package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.GetBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repo.BookingRepo;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.common.CustomPageRequest;
import ru.practicum.shareit.item.dto.GetBookingItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepo;
import ru.practicum.shareit.user.dto.GetBookingUserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepo;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.practicum.shareit.booking.model.BookingStatus.APPROVED;
import static ru.practicum.shareit.booking.model.BookingStatus.WAITING;

@SpringBootTest
public class BookingServiceTest {

    private final LocalDateTime startDate = LocalDateTime.now().plusHours(1);
    private final LocalDateTime endDate = LocalDateTime.now().plusHours(2);

    private BookingService bookingService;

    private final User userBooker = new User(1, "Test", "Test@mail.ru");
    private final User userOwner = new User(2, "Test2", "Test2@mail.ru");
    private final Item item = new Item(1, "Test", "TestD", true, userOwner, null);
    private final BookingDto bookingDto = new BookingDto(1, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
    private final Booking booking = new Booking(1, startDate, endDate, item, userOwner, WAITING);
    Pageable pageable =  new CustomPageRequest(1, 1, Sort.by("Test"));

    GetBookingUserDto getBookingUserDto = new GetBookingUserDto(2);
    GetBookingItemDto getBookingItemDto = new GetBookingItemDto(1, "Test");
    private GetBookingDto getBookingDto;


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
        getBookingDto = new GetBookingDto(1, startDate, endDate, WAITING, getBookingUserDto, getBookingItemDto);
    }

    @Test
    public void addBookingTest() {
        when(itemRepo.existsById(any())).thenReturn(true);
        when(itemRepo.getReferenceById(any())).thenReturn(item);
        when(userRepo.getReferenceById(any())).thenReturn(userBooker);
        when(bookingRepo.save(any())).thenReturn(booking);

        GetBookingDto createdBookingDto = bookingService.addBooking(1, bookingDto);
        assertEquals(getBookingDto, createdBookingDto);
    }

    @Test
    public void approveBookingTest() {
        when(bookingRepo.existsById(any())).thenReturn(true);
        when(bookingRepo.getReferenceById(any())).thenReturn(booking);
        when(bookingRepo.save(any())).thenReturn(booking);

        GetBookingDto createdBookingDto = bookingService.approveBooking(2, 1, true);
        getBookingDto.setStatus(APPROVED);
        assertEquals(getBookingDto, createdBookingDto);
    }

    @Test
    public void getBookingByIdTest() {
        when(bookingRepo.existsById(any())).thenReturn(true);
        when(bookingRepo.getReferenceById(any())).thenReturn(booking);

        GetBookingDto createdBookingDto = bookingService.getBooking(2, 1);
        assertEquals(getBookingDto, createdBookingDto);
    }

    @Test
    public void getBookingsByBooker() {
        when(bookingRepo.existsById(any())).thenReturn(true);
        when(bookingRepo.findByBookerId(2, pageable)).thenReturn(List.of(booking));

        List<GetBookingDto> createdBookingDto = bookingService.getAllBookingsByBooker(2, "ALL", pageable);
        assertEquals(List.of(getBookingDto), createdBookingDto);
    }
}
