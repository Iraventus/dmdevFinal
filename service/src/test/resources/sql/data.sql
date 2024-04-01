INSERT INTO users (id, login, password, firstname, lastname, role, personal_discount)
VALUES (1, 'Nick@gmail.com', '12345', 'Nick', 'Ivanov', 'customer', null),
       (2, 'Alex@gmail.com', 'qwerty', 'Alex', null, 'customer', null),
       (3, 'Bob@gmail.com', '123qwe', 'Bob', 'Petrov', 'manager', 20),
       (4, 'Gale@gmail.com', 'qwertys', 'Gale', null, 'customer', null);
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO producer (id, name)
VALUES (1, 'UltraPro'),
       (2, 'Ultimate Guard'),
       (3, 'Card-Pro');

INSERT INTO goods (id, name, type, board_game_theme, localization, quantity, price, producer_id)
VALUES (1, 'Dragon Shield sleeves', 'accessories', null, null, 3, 500,
        (SELECT id FROM producer WHERE name = 'UltraPro')),
       (2, 'Mayday', 'accessories', null, null, 2, 300, (SELECT id FROM producer WHERE name = 'Ultimate Guard')),
       (3, 'UltraPro Sleeves', 'accessories', null, null, 5, 100, (SELECT id FROM producer WHERE name = 'Card-Pro')),
       (4, 'Arkham Horror', 'boardGames', 'AMERITRASH', 'RU', 1, 3000, null),
       (5, 'Gloomhaven', 'boardGames', 'AMERITRASH', 'EN', 2, 15000, null),
       (6, 'Mage-Knight', 'boardGames', 'EURO', 'FR', 5, 8000, null);
SELECT SETVAL('goods_id_seq', (SELECT MAX(id) FROM goods));

INSERT INTO cart (id, name, user_id)
VALUES (1, 'first Nick cart', (SELECT id FROM users WHERE login = 'Nick@gmail.com')),
       (2, 'second Nick cart', (SELECT id FROM users WHERE login = 'Nick@gmail.com')),
       (3, 'Alex cart', (SELECT id FROM users WHERE login = 'Alex@gmail.com'));
SELECT SETVAL('cart_id_seq', (SELECT MAX(id) FROM cart));

INSERT INTO cart_goods (id, cart_id, goods_id, total_price)
VALUES (1, (SELECT id FROM cart WHERE name = 'first Nick cart'), (SELECT id FROM goods WHERE name = 'UltraPro Sleeves'),
        200),
       (2, (SELECT id FROM cart WHERE name = 'first Nick cart'), (SELECT id FROM goods WHERE name = 'Gloomhaven'),
        15000),
       (3, (SELECT id FROM cart WHERE name = 'second Nick cart'), (SELECT id FROM goods WHERE name = 'Gloomhaven'),
        10000),
       (4, (SELECT id FROM cart WHERE name = 'Alex cart'), (SELECT id FROM goods WHERE name = 'Arkham Horror'), 5000);
SELECT SETVAL('cart_goods_id_seq', (SELECT MAX(id) FROM cart_goods));

INSERT INTO orders (id, cart_goods_id, status)
VALUES (1, (SELECT id FROM cart_goods WHERE total_price = 200), 'PAID'),
       (2, (SELECT id FROM cart_goods WHERE total_price = 15000), 'PAID'),
       (3, (SELECT id FROM cart_goods WHERE total_price = 10000), 'RESERVED'),
       (4, (SELECT id FROM cart_goods WHERE total_price = 5000), 'RESERVED');
SELECT SETVAL('orders_id_seq', (SELECT MAX(id) FROM orders));