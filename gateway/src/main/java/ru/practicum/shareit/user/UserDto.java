package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.common.CreateGroup;
import ru.practicum.shareit.common.UpdateGroup;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private long id;
    private String name;
    @NotBlank(message = "Заполните email", groups = CreateGroup.class)
    @Email(message = "Некорректный email! ", groups = {CreateGroup.class, UpdateGroup.class})
    private String email;
}
