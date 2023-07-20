package ru.practicum.shareit.request.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.model.ActualRequests;

public interface ActualRequestsRepo extends JpaRepository<ActualRequests, Long> {
}
