package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.common.CustomPageRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepo;
import ru.practicum.shareit.user.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ItemRepoTest {

    private final Pageable pageable = new CustomPageRequest(0, 10, Sort.by("id"));
    private final User userOwner = new User(0, "Test", "Test@mail.ru");
    private final Item item1 = new Item(0, "Test1", "TestD", true, userOwner, null);
    private final Item item2 = new Item(0, "Test2", "TestD", true, userOwner, null);

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ItemRepo itemRepository;

    @BeforeEach
    void before() {
        entityManager.persist(userOwner);
        entityManager.persist(item1);
        entityManager.persist(item2);
    }

    @Test
    void searchFindAllItems() {
        List<Item> items = itemRepository.search("TeSt", pageable);

        assertEquals(List.of(item1, item2), items);
    }

    @Test
    void searchFindOnlyFirstItem() {
        List<Item> items = itemRepository.search("TeSt1", pageable);

        assertEquals(List.of(item1), items);
    }

    @Test
    void searchFindOnlySecondItem() {
        List<Item> items = itemRepository.search("tEsT2", pageable);

        assertEquals(List.of(item2), items);
    }

    @Test
    void searchFindNothing() {
        List<Item> items = itemRepository.search("TEST 3", pageable);

        assertEquals(0, items.size());
    }
}
