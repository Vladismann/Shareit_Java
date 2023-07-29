package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.ItemDto;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class ItemDtoJsonTest {

    @Autowired
    private JacksonTester<ItemDto> itemDtoJacksonTester;

    @Test
    void itemDtoTest() throws IOException {
        ItemDto itemDto = ItemDto.builder()
                .id(1)
                .name("Test")
                .description("TestD")
                .available(true)
                .requestId(1L)
                .build();

        JsonContent<ItemDto> jsonContent = itemDtoJacksonTester.write(itemDto);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(1);

        assertThat(jsonContent).extractingJsonPathStringValue("$.name").isEqualTo(itemDto.getName());

        assertThat(jsonContent).extractingJsonPathStringValue("$.description").isEqualTo(itemDto.getDescription());

        assertThat(jsonContent).extractingJsonPathBooleanValue("$.available").isEqualTo(itemDto.getAvailable());

        assertThat(jsonContent).extractingJsonPathNumberValue("$.requestId").isEqualTo(1);
    }
}
