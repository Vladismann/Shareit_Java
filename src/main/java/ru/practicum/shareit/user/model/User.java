package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    // "CONSTRAINT uq_email UNIQUE (email)" не работал, поэтому указываю ограничение в аннотации.
    // В чем может быть причина, что не срабатывает UNIQUE на уровне создания таблицы?
    @Column(nullable = false, unique = true)
    private String email;
}
