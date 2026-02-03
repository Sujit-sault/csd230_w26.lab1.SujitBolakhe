package csd230.lab1.repositories;

import csd230.lab1.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    List<TicketEntity> findByEventName(String eventName);
    List<TicketEntity> findByEventNameContaining(String eventNamePart);
    List<TicketEntity> findByTicketPriceBetween(Double price, Double max);
    List<TicketEntity> findByEventDate(LocalDateTime eventDate);

    @Query("SELECT t FROM TicketEntity t WHERE t.eventDate > :currentDate")
    List<TicketEntity> findUpcomingEvents(@Param("currentDate") LocalDateTime currentDate);


    @Query("SELECT t FROM TicketEntity t WHERE t.ticketPrice BETWEEN :min AND :max")
    List<TicketEntity> findByPriceRange(@Param("min") Double minPrice,
                                        @Param("max") Double maxPrice);
}