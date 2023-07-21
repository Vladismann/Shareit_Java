package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.dto.GetItemBookingDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ru.practicum.shareit.item.ItemMessages.*;

@Data
@Builder
@AllArgsConstructor
public class ItemDto {

    private long id;
    @NotBlank(message = EMPTY_ITEM_NAME)
    private String name;
    @NotBlank(message = EMPTY_ITEM_DESCRIPTION)
    private String description;
    @NotNull(message = EMPTY_ITEM_AVAILABILITY)
    private Boolean available;
    private GetItemBookingDto lastBooking;
    private GetItemBookingDto nextBooking;
    private Set<CommentDto> comments;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long requestId;
}
