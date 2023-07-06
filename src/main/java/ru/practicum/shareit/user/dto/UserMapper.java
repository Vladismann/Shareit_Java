package ru.practicum.shareit.user.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.user.model.User;

@UtilityClass
public class UserMapper {
    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public User fromUserDto(UserDto user) {
        return new User(user.getId(), user.getName(), user.getEmail());
    }

    public User updateUserFromUserDto(User actualUser, UserDto newUser) {
        if (newUser.getEmail() != null && !newUser.getEmail().isBlank() && !actualUser.getEmail().equals(newUser.getEmail())) {
            actualUser.setEmail(newUser.getEmail());
        }
        if (newUser.getName() != null && !newUser.getName().isBlank()) {
            actualUser.setName(newUser.getName());
        }
        return actualUser;
    }
}
