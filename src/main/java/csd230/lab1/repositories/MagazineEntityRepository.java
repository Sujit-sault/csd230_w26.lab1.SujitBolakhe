package csd230.lab1.repositories;

import csd230.lab1.entities.MagazineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MagazineEntityRepository extends JpaRepository<MagazineEntity, Long> {


    List<MagazineEntity> findByTitle(String title);
    List<MagazineEntity> findByTitleContaining(String title);
    List<MagazineEntity> findByPages(Integer pages);
    List<MagazineEntity> findByPrice(Double price);


    @Query("SELECT m FROM MagazineEntity m WHERE m.pages > :minPages")
    List<MagazineEntity> findByPagesGreaterThan(@Param("minPages") Integer minPages);


    List<MagazineEntity> findByTitleLike(String pattern);
}