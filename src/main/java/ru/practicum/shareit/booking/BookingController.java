package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.GetBookingDto;
import ru.practicum.shareit.booking.service.BookingServiceImpl;

import javax.validation.Valid;

import static ru.practicum.shareit.booking.BookingPaths.BOOKINGS_PATH;
import static ru.practicum.shareit.common.CommonForControllers.BY_ID_PATH;
import static ru.practicum.shareit.common.CommonForControllers.USER_HEADER;
import static ru.practicum.shareit.common.Messages.RECEIVED_PATCH;
import static ru.practicum.shareit.common.Messages.RECEIVED_POST;

@RestController
@RequestMapping(BOOKINGS_PATH)
@Slf4j
@RequiredArgsConstructor
public class BookingController {

    private final BookingServiceImpl bookingService;

    @PostMapping()
    public GetBookingDto createBooking(@RequestHeader(USER_HEADER) long userId,
                                       @Valid @RequestBody BookingDto bookingDto) {
        log.info(RECEIVED_POST + BOOKINGS_PATH);
        return bookingService.addBooking(userId, bookingDto);
    }

    @PatchMapping(BY_ID_PATH)
    public GetBookingDto approveBooking(@RequestHeader(USER_HEADER) long userId,
                                        @PathVariable long id,
                                        @RequestParam boolean approved) {
        log.info(RECEIVED_PATCH + BOOKINGS_PATH + "/" + id);
        return bookingService.approveBooking(userId, id, approved);
    }
}
