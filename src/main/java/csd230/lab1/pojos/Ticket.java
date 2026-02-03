package csd230.lab1.pojos;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket extends Product {
    private String description = "";
    private double price = 0.0;
    private String eventName = "";
    private LocalDateTime eventDate;

    public Ticket() {
        super();
    }

    public Ticket(String description, double price, String eventName, LocalDateTime eventDate) {
        this.description = description;
        this.price = price;
        this.eventName = eventName;
        this.eventDate = eventDate;
    }

    @Override
    public void initialize() {
        System.out.println("Enter Event Name:");
        this.eventName = getInput("");

        System.out.println("Enter Description:");
        this.description = getInput("");

        System.out.println("Enter Price:");
        this.price = getInput(0.0);

        System.out.println("Enter Event Date (YYYY-MM-DDTHH:MM:SS or dd-MMM-yyyy):");
        this.eventDate = getInput((LocalDateTime) null);
    }

    @Override
    public void edit() {
        System.out.println("Edit Event Name [" + this.eventName + "]:");
        this.eventName = getInput(this.eventName);

        System.out.println("Edit Description [" + this.description + "]:");
        this.description = getInput(this.description);

        System.out.println("Edit Price [" + this.price + "]:");
        this.price = getInput(this.price);

        System.out.println("Edit Event Date [" + this.eventDate + "]:");
        this.eventDate = getInput(this.eventDate);
    }

    @Override
    public void sellItem() {
        System.out.println("Selling Ticket: " + eventName);
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "eventName='" + eventName + '\'' +
                ", eventDate=" + eventDate +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return Double.compare(price, ticket.price) == 0 &&
                Objects.equals(description, ticket.description) &&
                Objects.equals(eventName, ticket.eventName) &&
                Objects.equals(eventDate, ticket.eventDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, price, eventName, eventDate);
    }
}