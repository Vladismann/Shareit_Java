package ru.practicum.shareit.booking.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Long> {

    List<Booking> findByBookerId(long id, Pageable pageable);

    List<Booking> findAllByBookerIdAndStartLessThanEqualAndEndGreaterThanEqual(long id, LocalDateTime currentDate1, LocalDateTime currentDate2, Pageable pageable);

    List<Booking> findAllByBookerIdAndEndLessThan(long id, LocalDateTime currentDate, Pageable pageable);

    List<Booking> findAllByBookerIdAndStartGreaterThan(long id, LocalDateTime currentDate, Pageable pageable);

    List<Booking> findByBookerIdAndStatus(long id, BookingStatus status, Pageable pageable);

    @Query("select b " +
            "from Booking b " +
            "join b.item i " +
            "where i.owner.id = ?1")
    List<Booking> findAllByOwner(long id, Pageable pageable);

    @Query("select b " +
            "from Booking b " +
            "join b.item i " +
            "where i.owner.id = ?1 " +
            "and b.start < ?2 " +
            "and b.end > ?2")
    List<Booking> findAllCurrentByOwner(long id, LocalDateTime currentDate, Pageable pageable);

    @Query("select b " +
            "from Booking b " +
            "join b.item i " +
            "where i.owner.id = ?1 " +
            "and b.end < ?2")
    List<Booking> findAllPastByOwner(long id, LocalDateTime currentDate, Pageable pageable);

    @Query("select b " +
            "from Booking b " +
            "join b.item i " +
            "where i.owner.id = ?1 " +
            "and b.start > ?2")
    List<Booking> findAllFutureByOwner(long id, LocalDateTime currentDate, Pageable pageable);

    @Query("select b " +
            "from Booking b " +
            "join b.item i " +
            "where i.owner.id = ?1 " +
            "and b.status = ?2")
    List<Booking> findAllByStatusByOwner(long id, BookingStatus status, Pageable pageable);

    List<Booking> findAllByItemId(long id);

    List<Booking> findAllByItemIdIn(List<Long> ids);
}