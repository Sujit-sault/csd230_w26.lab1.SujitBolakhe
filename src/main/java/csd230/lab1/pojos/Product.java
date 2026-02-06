package csd230.lab1.pojos;

import java.io.Serializable;


public abstract class Product extends Editable implements SaleableItem, Serializable {
    private String productId;
    private String title;
    private Double price;
    private Integer quantity;
    private String description;


    public Product() {
        super();
    }

    public Product(String title, Double price, Integer quantity, String description) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public double getPrice() { return price != null ? price : 0.0; }


    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public void sellItem() {
        // Default implementation - subclasses should override
        System.out.println("Selling product: " + title);
        if (quantity != null && quantity > 0) {
            quantity--;
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}