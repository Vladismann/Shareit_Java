package ru.practicum.shareit.request.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static ru.practicum.shareit.request.ItemRequestMessages.EMPTY_DESCRIPTION;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddItemRequestDto {

    @NotBlank(message = EMPTY_DESCRIPTION)
    private String description;
}
