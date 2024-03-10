package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.entity.Cart;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository extends RepositoryBase<Long, Cart> {

  public CartRepository(EntityManager entityManager) {
    super(Cart.class, entityManager);
  }
}
