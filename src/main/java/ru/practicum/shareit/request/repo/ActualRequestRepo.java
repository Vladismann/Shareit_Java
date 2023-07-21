package ru.practicum.shareit.request.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.model.ActualRequest;

public interface ActualRequestRepo extends JpaRepository<ActualRequest, Long> {
}
