package ru.practicum.shareit.user.repo;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepo {

    void checkUserIsExist(long id);

    User create(User user);

    User update(User user);

    void delete(long id);

    User get(long id);

    List<User> getAll();
}
