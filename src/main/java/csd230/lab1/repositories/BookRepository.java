package csd230.lab1.repositories;

import csd230.lab1.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByIsbn(String isbn);
    BookEntity findById(long id);

    List<BookEntity> findByTitleContaining(String title);
    List<BookEntity> findByTitleLike(String title);

    List<BookEntity> findByAuthor(String author);
    List<BookEntity> findByPrice(Double price);
    List<BookEntity> findByCopies(Integer copies);

    @Query("SELECT b FROM BookEntity b WHERE b.price BETWEEN :minPrice AND :maxPrice")
    List<BookEntity> findByPriceRange(@Param("minPrice") Double minPrice,
                                      @Param("maxPrice") Double maxPrice);

    List<BookEntity> findByAuthorContaining(String authorPart);
    List<BookEntity> findByPriceGreaterThan(Double price);
    List<BookEntity> findByPriceLessThan(Double price);
    List<BookEntity> findByCopiesGreaterThan(Integer minCopies);
}