package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

import javax.validation.Valid;

import static ru.practicum.shareit.common.CommonForControllers.BY_ID_PATH;
import static ru.practicum.shareit.common.CommonForControllers.USER_HEADER;
import static ru.practicum.shareit.common.Messages.*;

@Controller
@RequestMapping("/bookings")
@Slf4j
@RequiredArgsConstructor
public class BookingController {

    private final BookingClient bookingClient;

    @PostMapping()
    public ResponseEntity<Object> createBooking(@RequestHeader(USER_HEADER) long userId,
                                                @Valid @RequestBody BookingDto bookingDto) {
        log.info(RECEIVED_POST, "/bookings");
        return bookingClient.addBooking(userId, bookingDto);
    }

    @PatchMapping(BY_ID_PATH)
    public ResponseEntity<Object> approveBooking(@RequestHeader(USER_HEADER) long userId,
                                                 @PathVariable long id,
                                                 @RequestParam boolean approved) {
        log.info(RECEIVED_PATCH, "/bookings", id);
        return bookingClient.approveBooking(userId, id, approved);
    }

    @GetMapping(BY_ID_PATH)
    public ResponseEntity<Object> getBooking(@RequestHeader(USER_HEADER) long userId,
                                             @PathVariable long id) {
        log.info(RECEIVED_GET, "/bookings", id);
        return bookingClient.getByBookingId(userId, id);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllByBooker(@RequestHeader(USER_HEADER) long userId,
                                                 @RequestParam(defaultValue = "all") String state,
                                                 @RequestParam(defaultValue = "0") int from,
                                                 @RequestParam(defaultValue = "10") int size) {
        log.info(RECEIVED_GET, "/bookings", USER_HEADER + userId);
        return bookingClient.getAllBookingsByBooker(userId, state, from, size);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllByOwner(@RequestHeader(USER_HEADER) long userId,
                                                @RequestParam(defaultValue = "all") String state,
                                                @RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        log.info(RECEIVED_GET, "/bookings", USER_HEADER + userId);
        return bookingClient.getAllBookingsByOwner(userId, state, from, size);
    }
}
