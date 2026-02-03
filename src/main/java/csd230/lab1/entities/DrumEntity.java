package csd230.lab1.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("DRUM")
public class DrumEntity extends MusicalInstrumentEntity {
    private String drumType;
    private Integer diameterInInches;

    public DrumEntity() {}

    public DrumEntity(String title, Double price, Integer quantity,
                      String material, String brand,
                      String drumType, Integer diameterInInches) {
        this.drumType = drumType;
        this.diameterInInches = diameterInInches;
        setMaterial(material);
        setBrand(brand);
    }

    @Override
    public void sellItem() {
        System.out.println("Sold drum: " + drumType + " (" + diameterInInches + " inches)");
    }

    @Override
    public double getPrice() {
        // Implement pricing logic
        return 199.99; // Example price
    }

    // Getters and setters
    public String getDrumType() {
        return drumType;
    }


    public void setDrumType(String drumType) {
        this.drumType = drumType;
    }

    public Integer getDiameterInInches() {
        return diameterInInches;
    }

    public void setDiameterInInches(Integer diameterInInches) {
        this.diameterInInches = diameterInInches;
    }

    @Override
    public String toString() {
        return "DrumEntity{" +
                "id=" + getId() +
                ", drumType='" + drumType + '\'' +
                ", diameter=" + diameterInInches + " inches" +
                ", material='" + getMaterial() + '\'' +
                ", brand='" + getBrand() + '\'' +
                '}';
    }
}