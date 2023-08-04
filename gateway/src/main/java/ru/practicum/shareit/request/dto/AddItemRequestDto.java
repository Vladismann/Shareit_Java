package ru.practicum.shareit.request.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddItemRequestDto {

    @NotBlank(message = "Укажите описание запроса")
    private String description;
}
