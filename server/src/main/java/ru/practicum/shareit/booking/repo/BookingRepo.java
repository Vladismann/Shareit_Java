package ru.practicum.shareit.booking.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Long> {

    List<Booking> findByBookerIdOrderByStartDesc(long id, Pageable pageable);

    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(long id, LocalDateTime startDate1, LocalDateTime startDate2, Pageable pageable);

    List<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(long id, LocalDateTime currentDate, Pageable pageable);

    List<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(long id, LocalDateTime currentDate, Pageable pageable);

    List<Booking> findByBookerIdAndStatusOrderByStartDesc(long id, BookingStatus status, Pageable pageable);

    @Query("select b " +
            "from Booking b " +
            "join b.item i " +
            "where i.owner.id = :id")
    List<Booking> findAllByOwner(@Param("id") long id, Pageable pageable);

    @Query("select b " +
            "from Booking b " +
            "join b.item i " +
            "where i.owner.id = :id " +
            "and b.start < :date " +
            "and b.end > :date")
    List<Booking> findAllCurrentByOwner(@Param("id") long id, @Param("date") LocalDateTime date, Pageable pageable);

    @Query("select b " +
            "from Booking b " +
            "join b.item i " +
            "where i.owner.id = :id " +
            "and b.end < :date")
    List<Booking> findAllPastByOwner(@Param("id") long id, @Param("date") LocalDateTime date, Pageable pageable);

    @Query("select b " +
            "from Booking b " +
            "join b.item i " +
            "where i.owner.id = :id " +
            "and b.start > :date")
    List<Booking> findAllFutureByOwner(@Param("id") long id, @Param("date") LocalDateTime date, Pageable pageable);

    @Query("select b " +
            "from Booking b " +
            "join b.item i " +
            "where i.owner.id = :id " +
            "and b.status = :status")
    List<Booking> findAllByStatusByOwner(@Param("id") long id, @Param("status") BookingStatus status, Pageable pageable);

    List<Booking> findAllByItemId(long id);

    List<Booking> findAllByItemIdIn(List<Long> ids);
}