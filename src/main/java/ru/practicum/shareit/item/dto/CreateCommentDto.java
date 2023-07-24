package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static ru.practicum.shareit.item.ItemMessages.EMPTY_COMMENT;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentDto {
    @NotBlank(message = EMPTY_COMMENT)
    private String text;
}
