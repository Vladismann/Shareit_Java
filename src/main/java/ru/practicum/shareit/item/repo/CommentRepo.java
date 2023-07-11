package ru.practicum.shareit.item.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Comment;

import java.util.List;
import java.util.Set;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    Set<Comment> findAllByItemId(long itemId);

    Set<Comment> findAllByItemIdIn(List<Long> ids);
}
