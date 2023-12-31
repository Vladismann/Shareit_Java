package ru.practicum.shareit.request.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

public interface ItemRequestRepo extends JpaRepository<ItemRequest, Long> {

    List<ItemRequest> findByRequestor(Long requestor);

    List<ItemRequest> findByRequestorNot(Long requestor, Pageable pageable);
}
