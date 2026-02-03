package csd230.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("MAGAZINE")
public class MagazineEntity extends PublicationEntity {
    private int orderQty;
    private LocalDateTime currentIssue;
    private int pages;

    public MagazineEntity() {}


    public MagazineEntity(String title, double price, int copies, int pages, LocalDateTime currentIssue) {
        super(title, price, copies);
        this.pages = pages;
        this.orderQty = orderQty;
        this.currentIssue = currentIssue;
    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int o) {
        this.orderQty = o;
    }

    public void setCurrentIssue(LocalDateTime d) {
        this.currentIssue = d;
    }

    public LocalDateTime getCurrentIssue() {
        return currentIssue;
    }

    @Override
    public String toString() {
        return "MagazineEntity{" +
                "id=" + getId() +
                ", title='" + getTitle() + "'" +
                ", pages=" + pages +
                ", orderQty=" + orderQty +
                ", currentIssue=" + currentIssue +
                ", price=$" + getPrice() +
                ", copies=" + getCopies() +
                "}";
    }
}