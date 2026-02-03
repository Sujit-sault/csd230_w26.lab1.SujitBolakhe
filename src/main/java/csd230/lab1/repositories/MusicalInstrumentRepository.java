package csd230.lab1.repositories;

import csd230.lab1.entities.MusicalInstrumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MusicalInstrumentRepository extends JpaRepository<MusicalInstrumentEntity, Long> {
    List<MusicalInstrumentEntity> findByMaterial(String material);
    List<MusicalInstrumentEntity> findByBrand(String brand);
    List<MusicalInstrumentEntity> findByBrandContaining(String brandName);

    @Query("SELECT mi FROM MusicalInstrumentEntity mi WHERE mi.material LIKE %:material%")
    List<MusicalInstrumentEntity> findByMaterialLike(@Param("material") String material);
}