package csd230.lab1.repositories;

import csd230.lab1.entities.CartEntity;
import csd230.lab1.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CartEntityRepository extends JpaRepository<CartEntity, Long> {
    CartEntity findByUser(UserEntity user);



    @Query("SELECT DISTINCT c FROM CartEntity c JOIN c.products p WHERE p.id = :productId")
    List<CartEntity> findCartsContainingProduct(@Param("productId") Long productId);

    @Query("SELECT c FROM CartEntity c WHERE SIZE(c.products) > :minProducts")
    List<CartEntity> findCartsWithMinProducts(@Param("minProducts") int minProducts);

    @Query("SELECT c FROM CartEntity c WHERE c.products IS EMPTY")
    List<CartEntity> findEmptyCarts();
}