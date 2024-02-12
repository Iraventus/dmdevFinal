# Проект Онлайн-магазина настольных игр

# В проекте существует 6 основных таблиц: (sql скрипт и диаграмму можно глянуть в service.src.main.java.org.example.resources)

* users - пользователи сайта. На данный момент, существует 2 типа: manager (предполагается,
что он будет вносить изменения по товару и существующим данным пользователей) и customer
(покупатели).
* board_games и accessories - основные товары для покупки. Предполагается, что пользователь добавляет
их в корзину, а затем формируется заказ, если quantity товара > 0.
* producer - объекты типа Accessories имеют производителя. Данная таблица хранит информацию о нем.
(имеет связь one to many с таблицей accessories)
* cart - предполагается, что перед созданием заказа, покупатель добавит board_games и (или) accessories
в корзину и далее, сформирует заказ. Имеет связь one to many c board_games и accessories. Имеет связь
one to one c orders. Не имеет связи с users.
* orders - предполагается, что после подтверждения выбранных товаров из корзины, формируется заказ.
Если товар был оплачен, заказу присваивается статус paid. Если товар не был оплачен, заказу присваивается
статус RESERVED, и присваивается reservation_end_date. Предполагается, что по истечению reservation_end_date
order и соответствующий cart удаляются из системы, а количество товара возобновляется.