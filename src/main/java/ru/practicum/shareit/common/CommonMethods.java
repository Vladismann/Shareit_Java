package ru.practicum.shareit.common;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.exceptions.NotFoundException;

import static ru.practicum.shareit.common.Messages.INCORRECT_RESOURCE;

@Slf4j
@UtilityClass
public class CommonMethods {

    public void checkResourceIsExists(long resourceId, JpaRepository<?, Long> repo) {
        if (!repo.existsById(resourceId)) {
            log.info(INCORRECT_RESOURCE + resourceId);
            throw new NotFoundException(INCORRECT_RESOURCE + resourceId);
        }
    }
}
