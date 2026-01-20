package csd230.lab1.repositories;

import csd230.lab1.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByTitleContaining(String title);


    @Query("SELECT DISTINCT p FROM ProductEntity p JOIN p.carts c")
    List<ProductEntity> findProductsInCarts();


    @Query("SELECT p FROM ProductEntity p WHERE p.carts IS EMPTY")
    List<ProductEntity> findProductsNotInCarts();


}