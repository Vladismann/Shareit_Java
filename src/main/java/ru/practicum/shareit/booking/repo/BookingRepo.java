package ru.practicum.shareit.booking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Long> {

    List<Booking> findByBookerIdOrderByStartDesc(long id);

    List<Booking> findAllByBookerIdAndStartLessThanEqualAndEndGreaterThanEqualOrderByStartDesc(long id, LocalDateTime currentDate1, LocalDateTime currentDate2);

    List<Booking> findAllByBookerIdAndEndLessThanOrderByStartDesc(long id, LocalDateTime currentDate);

    List<Booking> findAllByBookerIdAndStartGreaterThanOrderByStartDesc(long id, LocalDateTime currentDate);

    List<Booking> findByBookerIdAndStatusOrderByStartDesc(long id, BookingStatus status);

    @Query("select b " +
            "from Booking b " +
            "join b.item i " +
            "where i.owner.id = ?1 " +
            "order by b.start desc")
    List<Booking> findAllByOwner(long id);

    @Query("select b " +
            "from Booking b " +
            "join b.item i " +
            "where i.owner.id = ?1 " +
            "and b.start <= ?2 " +
            "and b.end <= ?2 " +
            "order by b.start desc")
    List<Booking> findAllCurrentByOwner(long id, LocalDateTime currentDate);

    @Query("select b " +
            "from Booking b " +
            "join b.item i " +
            "where i.owner.id = ?1 " +
            "and b.end < ?2 " +
            "order by b.start desc")
    List<Booking> findAllPastByOwner(long id, LocalDateTime currentDate);

    @Query("select b " +
            "from Booking b " +
            "join b.item i " +
            "where i.owner.id = ?1 " +
            "and b.start > ?2 " +
            "order by b.start desc")
    List<Booking> findAllFutureByOwner(long id, LocalDateTime currentDate);

    @Query("select b " +
            "from Booking b " +
            "join b.item i " +
            "where i.owner.id = ?1 " +
            "and b.status = ?2 " +
            "order by b.start desc")
    List<Booking> findAllByStatusByOwner(long id, BookingStatus status);
}