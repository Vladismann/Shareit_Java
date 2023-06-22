package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static ru.practicum.shareit.item.ItemMessages.*;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
public class ItemDto {

    private long id;
    @NotBlank(message = EMPTY_ITEM_NAME)
    private String name;
    @NotBlank(message = EMPTY_ITEM_DESCRIPTION)
    private String description;
    @NotNull(message = EMPTY_ITEM_AVAILABILITY)
    private Boolean available;
    @JsonIgnore
    private UserDto owner;
}
