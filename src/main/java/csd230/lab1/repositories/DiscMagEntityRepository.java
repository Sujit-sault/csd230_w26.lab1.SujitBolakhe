package csd230.lab1.repositories;

import csd230.lab1.entities.DiscMagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface DiscMagEntityRepository extends JpaRepository<DiscMagEntity, Long> {


    List<DiscMagEntity> findByTitle(String title);
    List<DiscMagEntity> findByHasDisc(Boolean hasDisc);


    @Query("SELECT d FROM DiscMagEntity d WHERE d.hasDisc = true AND d.price < :maxPrice")
    List<DiscMagEntity> findDiscMagWithDiscUnderPrice(@org.springframework.data.repository.query.Param("maxPrice") Double maxPrice);
}