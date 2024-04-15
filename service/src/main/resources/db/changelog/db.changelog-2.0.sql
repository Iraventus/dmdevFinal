--liquibase formatted sql

--changeset ntokarev:1
ALTER TABLE orders
    ADD COLUMN created_at TIMESTAMP;

ALTER TABLE orders
    ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE orders
    ADD COLUMN created_by VARCHAR;

ALTER TABLE orders
    ADD COLUMN modified_by VARCHAR;