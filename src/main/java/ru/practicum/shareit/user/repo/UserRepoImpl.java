package ru.practicum.shareit.user.repo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.*;

import static ru.practicum.shareit.user.UserMessages.*;

@Repository
@Slf4j
public class UserRepoImpl implements UserRepo {

    private final LinkedHashMap<Long, User> users = new LinkedHashMap<>();
    private final Set<String> userEmails = new HashSet<>();
    private static long userId = 1;

    public void checkEmailIsFree(User user) {
        String actualEmail = user.getEmail();
        if (userEmails.contains(actualEmail)) {
            log.info(INCORRECT_EMAIL + actualEmail);
            throw new IllegalArgumentException(INCORRECT_EMAIL + actualEmail);
        } else {
            userEmails.add(actualEmail);
        }
    }

    @Override
    public void checkUserIsExist(long id) {
        if (!users.containsKey(id)) {
            log.info(INCORRECT_USER + id);
            throw new NotFoundException(INCORRECT_USER + id);
        }
    }

    @Override
    public User create(User user) {
        checkEmailIsFree(user);
        user.setId(userId++);
        users.put(user.getId(), user);
        log.info(CREATE_USER, user);
        return user;
    }

    @Override
    public User update(User user) {
        long id = user.getId();
        checkUserIsExist(id);
        User actualUser = users.get(id);
        if (user.getEmail() != null && !user.getEmail().isBlank() && !actualUser.getEmail().equals(user.getEmail())) {
            checkEmailIsFree(user);
            userEmails.remove(actualUser.getEmail());
            actualUser.setEmail(user.getEmail());
        }
        if (user.getName() != null && !user.getName().isBlank()) {
            actualUser.setName(user.getName());
        }
        users.put(id, actualUser);
        log.info(UPDATE_USER, actualUser);
        return actualUser;
    }

    @Override
    public void delete(long id) {
        checkUserIsExist(id);
        userEmails.remove(users.get(id).getEmail());
        users.remove(id);
        log.info(DELETE_USER, id);
    }

    @Override
    public User get(long id) {
        checkUserIsExist(id);
        log.info(GET_USER, id);
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info(GET_USERS, users.values().size());
        return new ArrayList<>(users.values());
    }
}