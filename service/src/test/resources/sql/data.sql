INSERT INTO users (id, login, password, firstname, lastname, role, status, job_title, address)
VALUES (1, 'Nick@gmail.com', '{noop}123', 'Nick', 'Ivanov', 'CUSTOMER', 'CUSTOMER', null, null),
       (2, 'Alex@gmail.com', '{noop}123', 'Alex', null, 'CUSTOMER', 'CUSTOMER', null, null),
       (3, 'Bob@gmail.com', '{noop}123', 'Bob', 'Petrov', 'MANAGER', 'MANAGER', null, null),
       (4, 'Van@gmail.com', '{noop}123', 'Van', 'Petrov', 'CUSTOMER', 'CUSTOMER', null, null),
       (5, 'Gale@gmail.com', '{noop}123', 'Gale', null, 'CUSTOMER', 'CUSTOMER', null, null);
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO producer (id, name)
VALUES (1, 'UltraPro'),
       (2, 'Ultimate Guard'),
       (3, 'Card-Pro');
SELECT SETVAL('producer_id_seq', (SELECT MAX(id) FROM producer));

INSERT INTO goods (id, name, type, board_game_theme, localization, quantity, price, producer_id)
VALUES (1, 'Dragon Shield sleeves', 'accessories', null, null, 3, 500,
        (SELECT id FROM producer WHERE name = 'UltraPro')),
       (2, 'Mayday', 'accessories', null, null, 2, 300, (SELECT id FROM producer WHERE name = 'Ultimate Guard')),
       (3, 'UltraPro Sleeves', 'accessories', null, null, 5, 100, (SELECT id FROM producer WHERE name = 'Card-Pro')),
       (4, 'Arkham Horror', 'boardGames', 'AMERITRASH', 'RU', 1, 3000, null),
       (5, 'Gloomhaven', 'boardGames', 'AMERITRASH', 'EN', 2, 15000, null),
       (6, 'Mage-Knight', 'boardGames', 'EURO', 'FR', 5, 8000, null);
SELECT SETVAL('goods_id_seq', (SELECT MAX(id) FROM goods));

INSERT INTO cart (id, user_id)
VALUES (1, (SELECT id FROM users WHERE login = 'Nick@gmail.com')),
       (2, (SELECT id FROM users WHERE login = 'Alex@gmail.com'));
SELECT SETVAL('cart_id_seq', (SELECT MAX(id) FROM cart));

INSERT INTO orders (id, user_id, status)
VALUES (1, (SELECT id FROM users WHERE login = 'Nick@gmail.com'), 'PAID'),
       (3, (SELECT id FROM users WHERE login = 'Alex@gmail.com'), 'RESERVED');
SELECT SETVAL('orders_id_seq', (SELECT MAX(id) FROM orders));

INSERT INTO cart_goods (id, cart_id, goods_id, order_id, total_goods)
VALUES (1, (SELECT cart.id
            FROM cart
                     JOIN users ON cart.user_id = users.id
            WHERE login = 'Nick@gmail.com'), (SELECT id FROM goods WHERE name = 'UltraPro Sleeves'),
        (SELECT orders.id
         FROM orders
                  JOIN users ON orders.user_id = users.id
         where login = 'Nick@gmail.com'), 3),
       (2, (SELECT cart.id
            FROM cart
                     JOIN users ON cart.user_id = users.id
            WHERE login = 'Nick@gmail.com'), (SELECT id FROM goods WHERE name = 'Gloomhaven'),
        (SELECT orders.id
         FROM orders
                  JOIN users ON orders.user_id = users.id
         where login = 'Nick@gmail.com'),
        1),
       (3, (SELECT cart.id
            FROM cart
                     JOIN users ON cart.user_id = users.id
            WHERE login = 'Alex@gmail.com'), (SELECT id FROM goods WHERE name = 'Arkham Horror'),
        (SELECT orders.id
         FROM orders
                  JOIN users ON orders.user_id = users.id
         where login = 'Alex@gmail.com'), 1);
SELECT SETVAL('cart_goods_id_seq', (SELECT MAX(id) FROM cart_goods));
