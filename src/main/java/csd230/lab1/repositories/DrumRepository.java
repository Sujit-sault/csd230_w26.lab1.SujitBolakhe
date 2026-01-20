package csd230.lab1.repositories;

import csd230.lab1.entities.DrumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface DrumRepository extends JpaRepository<DrumEntity, Long> {
    List<DrumEntity> findByDrumType(String drumType);
    List<DrumEntity> findByDiameterInInches(Integer diameter);
    List<DrumEntity> findByMaterial(String material);

    @Query("SELECT d FROM DrumEntity d WHERE d.diameterInInches > :minDiameter")
    List<DrumEntity> findByDiameterGreaterThan(@Param("minDiameter") Integer minDiameter);
}