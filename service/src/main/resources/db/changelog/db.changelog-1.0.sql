--liquibase formatted sql

--changeset ntokarev:1
CREATE TABLE IF NOT EXISTS users
(
    id                BIGSERIAL PRIMARY KEY,
    login             varchar(32) UNIQUE NOT NULL,
    password          varchar(128) DEFAULT '{noop123}',
    firstname         varchar(128),
    lastname          varchar(128),
    birth_date        date,
    role              varchar(32),
    status            varchar(32),
    address           jsonb,
    phone             varchar(32),
    job_title         varchar(32),
    personal_discount integer
);
--rollback DROP TABLE users;

--changeset ntokarev:2
CREATE TABLE IF NOT EXISTS producer
(
    id            BIGSERIAL PRIMARY KEY,
    name          varchar(128) UNIQUE,
    producer_info varchar(128),
    legal_address jsonb
);
--rollback DROP TABLE producer;

--changeset ntokarev:3
CREATE TABLE IF NOT EXISTS goods
(
    id               BIGSERIAL PRIMARY KEY,
    name             varchar(128),
    type              varchar(32),
    board_game_theme varchar(128),
    localization     char(2),
    description      varchar(1024),
    contents         varchar(256),
    creator          varchar(128),
    quantity         integer,
    price            integer,
    producer_id      BIGINT REFERENCES producer (id) ON DELETE CASCADE
);
--rollback DROP TABLE goods;

--changeset ntokarev:4
CREATE TABLE IF NOT EXISTS cart
(
    id      BIGSERIAL PRIMARY KEY,
    user_id bigserial NOT NULL UNIQUE REFERENCES users (id) ON DELETE CASCADE
);
--rollback DROP TABLE cart;

--changeset ntokarev:5
CREATE TABLE IF NOT EXISTS orders
(
    id                   BIGSERIAL PRIMARY KEY,
    status               varchar(32),
    reservation_end_date TIMESTAMP,
    user_id    BIGINT REFERENCES users (id) ON DELETE CASCADE
    );
--rollback DROP TABLE orders;

--changeset ntokarev:6
CREATE TABLE IF NOT EXISTS cart_goods
(
    id          BIGSERIAL PRIMARY KEY,
    cart_id     bigint REFERENCES cart (id) ON DELETE CASCADE,
    goods_id    bigint REFERENCES goods (id) ON DELETE CASCADE,
    order_id    BIGINT REFERENCES orders (id) ON DELETE CASCADE,
    total_goods integer
);
--rollback DROP TABLE cart_goods;