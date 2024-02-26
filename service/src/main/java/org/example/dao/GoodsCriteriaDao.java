package org.example.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.goods.Goods;
import org.example.entity.goods.Goods_;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GoodsCriteriaDao {

    private static final GoodsCriteriaDao INSTANCE = new GoodsCriteriaDao();

    public static GoodsCriteriaDao getInstance() {
        return INSTANCE;
    }

    /**
     * Возвращает весь список товаров в лексическом порядке
     */
    public List<Goods> findAllGoods(Session session) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Goods.class);
        var goods = criteria.from(Goods.class);

        criteria.select(goods).orderBy(cb.asc(goods.get(Goods_.NAME)));

        return session.createQuery(criteria).list();
    }

    /**
     * Возвращает товар по наименованию
     */
    public Goods findByName(Session session, String name) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Goods.class);
        var goods = criteria.from(Goods.class);

        criteria.select(goods).where(cb.equal(goods.get(Goods_.NAME), name));

        return session.createQuery(criteria).getSingleResult();
    }
}
