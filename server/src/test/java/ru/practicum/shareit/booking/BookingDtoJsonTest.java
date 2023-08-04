package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class BookingDtoJsonTest {

    @Autowired
    private JacksonTester<BookingDto> dtoJacksonTester;

    @Test
    public void bookingDtoTest() throws IOException {
        LocalDateTime created = LocalDateTime.now();
        BookingDto bookingDto = new BookingDto(1, created, created);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        JsonContent<BookingDto> jsonContent = dtoJacksonTester.write(bookingDto);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.itemId").isEqualTo(1);

        assertThat(jsonContent).extractingJsonPathStringValue("$.start").contains(dateTimeFormatter.format(bookingDto.getStart()));

        assertThat(jsonContent).extractingJsonPathStringValue("$.end").contains(dateTimeFormatter.format(bookingDto.getEnd()));
    }
}
