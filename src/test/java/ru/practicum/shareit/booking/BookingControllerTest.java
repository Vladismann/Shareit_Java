package ru.practicum.shareit.booking;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.GetBookingDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.booking.BookingPaths.BOOKINGS_PATH;
import static ru.practicum.shareit.booking.BookingPaths.OWNER_PATH;
import static ru.practicum.shareit.common.CommonForControllers.BY_ID_PATH;
import static ru.practicum.shareit.common.CommonForControllers.USER_HEADER;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    private final LocalDateTime dateTime = LocalDateTime.now().plusHours(1);
    private final BookingDto bookingDto = new BookingDto(1, dateTime, dateTime.plusDays(1));
    private final GetBookingDto getBookingDto = new GetBookingDto(1, dateTime, dateTime, BookingStatus.WAITING, null, null);

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private MockMvc mvc;

    @InjectMocks
    private BookingController bookingController;

    @Mock
    BookingService bookingService;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders
                .standaloneSetup(bookingController)
                .build();
    }

    @Test
    public void createBooking() throws Exception {
        when(bookingService.addBooking(anyLong(), any())).thenReturn(getBookingDto);

        MvcResult result = mvc.perform(post(BOOKINGS_PATH).content(mapper.writeValueAsString(bookingDto))
                        .header(USER_HEADER, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        GetBookingDto actualDto = mapper.readValue(result.getResponse().getContentAsString(), GetBookingDto.class);

        assertEquals(getBookingDto, actualDto);
    }

    @Test
    public void approveBooking() throws Exception {
        when(bookingService.approveBooking(anyLong(), anyLong(), anyBoolean())).thenReturn(getBookingDto);

        MvcResult result = mvc.perform(patch(BOOKINGS_PATH + BY_ID_PATH, 1).content(mapper.writeValueAsString(bookingDto))
                .header(USER_HEADER, 1)
                .param("approved", "true")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        GetBookingDto actualDto = mapper.readValue(result.getResponse().getContentAsString(), GetBookingDto.class);

        assertEquals(getBookingDto, actualDto);
    }

    @Test
    public void getBooking() throws Exception {
        when(bookingService.getBooking(anyLong(), anyLong())).thenReturn(getBookingDto);

        MvcResult result = mvc.perform(get(BOOKINGS_PATH + BY_ID_PATH, 1).content(mapper.writeValueAsString(bookingDto))
                        .header(USER_HEADER, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        GetBookingDto actualDto = mapper.readValue(result.getResponse().getContentAsString(), GetBookingDto.class);

        assertEquals(getBookingDto, actualDto);
    }

    @Test
    public void getAllByBooker() throws Exception {
        when(bookingService.getAllBookingsByBooker(anyLong(), any(), any())).thenReturn(List.of(getBookingDto));

        MvcResult result = mvc.perform(get(BOOKINGS_PATH).content(mapper.writeValueAsString(bookingDto))
                .header(USER_HEADER, 1)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        List<GetBookingDto> actualDto = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(List.of(getBookingDto), actualDto);
    }

    @Test
    public void getAllByOwner() throws Exception {
        when(bookingService.getAllBookingsByOwner(anyLong(), any(), any())).thenReturn(List.of(getBookingDto));

        MvcResult result = mvc.perform(get(BOOKINGS_PATH + OWNER_PATH).content(mapper.writeValueAsString(bookingDto))
                .header(USER_HEADER, 1)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        List<GetBookingDto> actualDto = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(List.of(getBookingDto), actualDto);
    }

}
