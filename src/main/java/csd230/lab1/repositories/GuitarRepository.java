package csd230.lab1.repositories;

import csd230.lab1.entities.GuitarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface GuitarRepository extends JpaRepository<GuitarEntity, Long> {
    List<GuitarEntity> findByGuitarType(String guitarType);
    List<GuitarEntity> findByNumberOfStrings(Integer numberOfStrings);
    List<GuitarEntity> findByMaterial(String material);

    @Query("SELECT g FROM GuitarEntity g WHERE g.numberOfStrings BETWEEN :minStrings AND :maxStrings")
    List<GuitarEntity> findByNumberOfStringsRange(@Param("minStrings") Integer minStrings,
                                                  @Param("maxStrings") Integer maxStrings);
}