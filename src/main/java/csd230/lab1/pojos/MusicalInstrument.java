package csd230.lab1.pojos;

import java.util.Objects;

public class MusicalInstrument extends Product {
    private String material;
    private String brand;


    public MusicalInstrument() {
        super();
    }

    public MusicalInstrument(String title, Double price, Integer quantity,
                             String description, String material, String brand) {
        super(title, price, quantity, description);
        this.material = material;
        this.brand = brand;
    }


    @Override
    public void edit() {
        System.out.println("Editing Musical Instrument: " + getTitle());
        // Add edit logic here
    }

    @Override
    public void initialize() {
        System.out.println("Initializing Musical Instrument...");
        // Add initialization logic here
    }


    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "MusicalInstrument{" +
                "material='" + material + '\'' +
                ", brand='" + brand + '\'' +
                ", title='" + getTitle() + '\'' +
                ", price=" + getPrice() +
                ", quantity=" + getQuantity() +
                ", description='" + getDescription() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MusicalInstrument)) return false;
        MusicalInstrument that = (MusicalInstrument) o;
        return Objects.equals(material, that.material) &&
                Objects.equals(brand, that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(material, brand);
    }
}