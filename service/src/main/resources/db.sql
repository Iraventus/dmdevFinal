CREATE TABLE users(
id BIGSERIAL PRIMARY KEY,
login varchar(32) UNIQUE NOT NULL ,
password varchar(32) NOT NULL ,
firstname varchar(128),
lastname varchar(128),
birth_date date,
registration_date date,
role varchar(32),
address jsonb,
phone varchar(32),
personal_discount integer
);

CREATE TABLE producer
(
    id BIGSERIAL PRIMARY KEY,
    name varchar(128) UNIQUE ,
    producer_info varchar(128),
    legal_address jsonb
);

CREATE TABLE goods
(
id BIGSERIAL PRIMARY KEY,
name varchar(128),
board_game_theme varchar(128),
localization char(2),
description varchar(1024),
contents varchar(256),
creator varchar(128),
quantity integer,
price integer,
producer_id bigserial REFERENCES producer(id)
);

CREATE TABLE cart
(
id BIGSERIAL PRIMARY KEY,
name varchar(128),
user_id bigserial REFERENCES users(id)
);

CREATE TABLE cart_goods(
id BIGSERIAL PRIMARY KEY,
cart_id bigint REFERENCES cart(id),
goods_id bigint REFERENCES goods(id),
created_at TIMESTAMP,
total_count integer,
total_price integer
);

create table orders
(
id BIGSERIAL PRIMARY KEY,
cart_goods_id bigint NOT NULL UNIQUE REFERENCES cart_goods(id),
status varchar(32),
creation_date TIMESTAMP,
reservation_end_date TIMESTAMP
);