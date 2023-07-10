package ru.practicum.shareit.item.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

import static ru.practicum.shareit.item.ItemMessages.EMPTY_COMMENT;

@Data
public class CreateCommentDto {
    @NotBlank(message = EMPTY_COMMENT)
    private String text;
}
