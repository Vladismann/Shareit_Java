package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.repo.BookingRepo;
import ru.practicum.shareit.item.repo.CommentRepo;
import ru.practicum.shareit.item.repo.ItemRepo;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ItemServiceTest {

    private ItemService itemService;
    private final User userSuccess = new User(1, "Test", "Test@mail.ru");

    @Mock
    private ItemRepo itemRepo;
    private UserRepo userRepo;
    private CommentRepo commentRepo;
    private BookingRepo bookingRepo;


    @BeforeEach
    void before() {
        itemService = new ItemServiceImpl(itemRepo, userRepo, commentRepo, bookingRepo);
        when(userRepo.existsById(any())).thenReturn(true);
    }

    @Test
    public void createItem() {
        when(userRepo.getReferenceById((long) 1)).thenReturn(userSuccess);
    }
}
