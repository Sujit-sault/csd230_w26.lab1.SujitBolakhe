package csd230.lab1.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("GUITAR")
public class GuitarEntity extends MusicalInstrumentEntity {
    private Integer numberOfStrings;
    private String guitarType;

    public GuitarEntity() {}

    public GuitarEntity(String title, Double price, Integer quantity,
                        String material, String brand,
                        Integer numberOfStrings, String guitarType) {

        this.numberOfStrings = numberOfStrings;
        this.guitarType = guitarType;
        setMaterial(material);
        setBrand(brand);
    }

    @Override
    public void sellItem() {
        System.out.println("Sold guitar: " + guitarType + " with " + numberOfStrings + " strings");
    }

    @Override
    public double getPrice() {

        return 299.99;
    }

    // Getters and setters
    public Integer getNumberOfStrings() {
        return numberOfStrings;
    }

    public void setNumberOfStrings(Integer numberOfStrings) {
        this.numberOfStrings = numberOfStrings;
    }

    public String getGuitarType() {
        return guitarType;
    }

    public void setGuitarType(String guitarType) {
        this.guitarType = guitarType;
    }

    @Override
    public String toString() {
        return "GuitarEntity{" +
                "id=" + getId() +
                ", guitarType='" + guitarType + '\'' +
                ", strings=" + numberOfStrings +
                ", material='" + getMaterial() + '\'' +
                ", brand='" + getBrand() + '\'' +
                '}';
    }
}