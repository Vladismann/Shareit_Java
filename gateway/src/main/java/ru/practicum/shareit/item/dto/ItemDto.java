package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.GetItemBookingDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private long id;
    @NotBlank(message = "Укажите название предмета")
    private String name;
    @NotBlank(message = "Укажите описание предмета")
    private String description;
    @NotNull(message = "Укажите статус доступности предмета")
    private Boolean available;
    private GetItemBookingDto lastBooking;
    private GetItemBookingDto nextBooking;
    private Set<CommentDto> comments;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long requestId;
}
