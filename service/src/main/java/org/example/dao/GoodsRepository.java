package org.example.dao;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.example.dto.BoardGamesFilters;
import org.example.entity.Producer_;
import org.example.entity.goods.Accessories;
import org.example.entity.goods.Accessories_;
import org.example.entity.goods.BoardGames;
import org.example.entity.goods.Goods;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

import static org.example.entity.goods.QBoardGames.boardGames;
import static org.example.entity.goods.QGoods.goods;

public class GoodsRepository extends RepositoryBase<Long, Goods> {
    public GoodsRepository(EntityManager entityManager) {
        super(Goods.class, entityManager);
    }

    public Optional<Goods> findByName(Session session, String name) {
        return Optional.ofNullable(new JPAQuery<Goods>(session).select(goods).from(goods).where(goods.name.eq(name)).fetchOne());
    }

    public List<Accessories> findByProducerName(Session session, String producerName) {

        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Accessories.class);
        var goods = criteria.from(Accessories.class);
        var producer = goods.join(Accessories_.PRODUCER);
        criteria.select(goods).where(cb.equal(producer.get(Producer_.NAME), producerName));
        return session.createQuery(criteria).list();
    }

    /**
     * Подскажи, пожалуйста, как корректно реализовать метод ниже findByFilter, при его вызове
     * ловлю ошибку Could not interpret path expression
     */

    public List<BoardGames> findByFilter(Session session, BoardGamesFilters boardGamesFilters) {
        var predicate = QPredicate.builder().add(boardGamesFilters.getBoardGameTheme(), boardGames.boardGameTheme::eq).add(boardGamesFilters.getLocalization(), boardGames.localization::eq).buildAnd();
        return new JPAQuery<BoardGames>(session).select(goods).from(goods).where(predicate).fetch().stream().map(boardGame -> (BoardGames) boardGame).toList();

    }
}
