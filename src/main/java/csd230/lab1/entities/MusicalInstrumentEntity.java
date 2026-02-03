package csd230.lab1.entities;

import jakarta.persistence.*;

@Entity

public abstract class MusicalInstrumentEntity extends ProductEntity {
    private String material;
    private String brand;

    public MusicalInstrumentEntity() {}

    public MusicalInstrumentEntity(String title, Double price, Integer quantity,
                                   String material, String brand) {

        this.material = material;
        this.brand = brand;
    }


    @Override
    public abstract void sellItem();

    @Override
    public abstract double getPrice();


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
        return "MusicalInstrumentEntity{" +
                "id=" + getId() +
                ", material='" + material + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}