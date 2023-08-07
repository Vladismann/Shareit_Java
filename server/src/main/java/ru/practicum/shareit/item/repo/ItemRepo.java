package ru.practicum.shareit.item.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Long> {

    List<Item> findByOwnerIdOrderById(Long userId, Pageable pageable);

    @Query("select i from Item i " +
            "where upper(i.name) like upper(concat('%', :text, '%')) " +
            " or upper(i.description) like upper(concat('%', :text, '%')) " +
            "and i.available = true")
    List<Item> search(@Param("text") String text, Pageable pageable);

    List<Item> findAllByRequestIdIn(List<Long> requestorIds);
}
