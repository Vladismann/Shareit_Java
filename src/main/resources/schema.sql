DROP TABLE IF EXISTS requests CASCADE;
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS bookings CASCADE;
DROP TABLE IF EXISTS items CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users (
        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        name VARCHAR(100),
        email VARCHAR(254) NOT NULL,
        CONSTRAINT uq_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS requests (
        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        description VARCHAR(200) NOT NULL,
        requestor BIGINT NOT NULL REFERENCES users(id),
        created TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS items (
        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        description VARCHAR(200) NOT NULL,
        is_available BOOLEAN NOT NULL,
        owner_id BIGINT  NOT NULL REFERENCES users(id),
        request_id BIGINT REFERENCES requests(id)
);

CREATE TABLE IF NOT EXISTS bookings (
        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        start_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
        end_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
        item_id BIGINT NOT NULL REFERENCES items(id),
        booker_id BIGINT NOT NULL REFERENCES users(id),
        status VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS comments (
        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        text VARCHAR(200) NOT NULL,
        item_id BIGINT NOT NULL REFERENCES items(id),
        author_id BIGINT NOT NULL REFERENCES users(id),
        created TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);