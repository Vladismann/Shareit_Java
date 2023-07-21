package ru.practicum.shareit.request.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "actual_requests", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActualRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long itemId;

    @Column(nullable = false)
    private long requestId;
}
