package csd230.lab1.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@DiscriminatorValue("TICKET")
public class TicketEntity extends ProductEntity {
    private String description;
    private String eventName;
    private LocalDateTime eventDate;
    private Double ticketPrice;

    public TicketEntity() {}

    public TicketEntity(String description, Double price, String eventName, LocalDateTime eventDate) {
        this.description = description;
        this.ticketPrice = price;
        this.eventName = eventName;
        this.eventDate = eventDate;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public LocalDateTime getEventDate() { return eventDate; }
    public void setEventDate(LocalDateTime eventDate) { this.eventDate = eventDate; }

    public Double getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(Double ticketPrice) { this.ticketPrice = ticketPrice; }

    @Override
    public void sellItem() {
        System.out.println("Sold ticket: " + eventName);
    }

    @Override
    public double getPrice() {
        return ticketPrice != null ? ticketPrice : 0.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketEntity)) return false;
        TicketEntity that = (TicketEntity) o;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "TicketEntity{id=" + getId() +
                ", event='" + eventName +
                "', date=" + eventDate +
                ", price=$" + ticketPrice + "}";
    }
}