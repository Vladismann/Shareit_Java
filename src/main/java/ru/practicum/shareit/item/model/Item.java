package ru.practicum.shareit.item.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Item {

    private long id;
    private String name;
    private String description;
    private Boolean available;
    @JsonIgnore
    private long owner;
}
