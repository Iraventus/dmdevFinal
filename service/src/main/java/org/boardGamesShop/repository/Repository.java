package org.boardGamesShop.repository;

import static java.util.Collections.emptyMap;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.boardGamesShop.entity.BaseEntity;

public interface Repository<K extends Serializable, E extends BaseEntity<K>> {

  E save(E entity);

  void delete(E entity);

  void update(E entity);

  default Optional<E> findById(K id) {
    return findById(id, emptyMap());
  }

  Optional<E> findById(K id, Map<String, Object> properties);

  List<E> findAll();
}
