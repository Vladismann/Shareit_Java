package ru.practicum.shareit.user.model;

import lombok.Data;

import javax.validation.constraints.Email;

import static ru.practicum.shareit.user.UserMessages.INCORRECT_EMAIL;

@Data
public class User {
    private long id;
    private String name;
    @Email(message = INCORRECT_EMAIL)
    private String email;
}
