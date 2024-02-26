package org.example.dao;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.goods.Goods;
import org.hibernate.Session;

import java.util.List;

import static org.example.entity.goods.QGoods.goods;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GoodsQueryDslDao {

    private static final GoodsQueryDslDao INSTANCE = new GoodsQueryDslDao();

    /**
     * Возвращает весь список товаров в лексическом порядке
     */
    public List<Goods> findAllGoods(Session session) {
        return new JPAQuery<Goods>(session)
                .select(goods)
                .from(goods)
                .orderBy(goods.name.asc())
                .fetch();
    }

    /**
     * Возвращает товар по наименованию
     */
    public Goods findByName(Session session, String name) {
        return new JPAQuery<Goods>(session)
                .select(goods)
                .from(goods)
                .where(goods.name.eq(name))
                .fetchOne();
    }

    public static GoodsQueryDslDao getInstance() {
        return INSTANCE;
    }
}
