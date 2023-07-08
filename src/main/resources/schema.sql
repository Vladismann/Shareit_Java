DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS bookings;

CREATE TABLE IF NOT EXISTS users (
        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        name VARCHAR(100),
        email VARCHAR(254) NOT NULL
        --CONSTRAINT почему-то напрямую тут не срабатывает, а только через аннотацию в модели
        --CONSTRAINT uq_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS items (
        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        description VARCHAR(200) NOT NULL,
        is_available BOOLEAN NOT NULL,
        owner_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS items (
        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        description VARCHAR(200) NOT NULL,
        is_available BOOLEAN NOT NULL,
        owner_id BIGINT  NOT NULL REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS bookings (
        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        start_date TIMESTAMP NOT NULL,
        end_date TIMESTAMP NOT NULL,
        item_id BIGINT NOT NULL REFERENCES items(id),
        booker_id BIGINT NOT NULL REFERENCES users(id),
        status VARCHAR(10) NOT NULL
);