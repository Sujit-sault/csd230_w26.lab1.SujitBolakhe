package csd230.lab1.pojos;

import java.util.Objects;

public class Drum extends MusicalInstrument {
    private String drumType;
    private Integer diameterInInches;

    public Drum() {
        super();
    }

    public Drum(String title, Double price, Integer quantity, String description,
                String material, String brand, String drumType, Integer diameterInInches) {
        super(title, price, quantity, description, material, brand);
        this.drumType = drumType;
        this.diameterInInches = diameterInInches;
    }

    @Override
    public void edit() {
        super.edit();
        System.out.println("Editing Drum specific properties...");
    }

    @Override
    public void initialize() {
        super.initialize();
        System.out.println("Initializing Drum specific properties...");
    }

    @Override
    public void sellItem() {
        System.out.println("Selling Drum: " + getTitle() +
                " (" + drumType + ", " + diameterInInches + " inches)");
    }


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
        return "Drum{" +
                "drumType='" + drumType + '\'' +
                ", diameterInInches=" + diameterInInches +
                ", material='" + getMaterial() + '\'' +
                ", brand='" + getBrand() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", price=" + getPrice() +
                ", quantity=" + getQuantity() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drum)) return false;
        if (!super.equals(o)) return false;
        Drum drum = (Drum) o;
        return Objects.equals(drumType, drum.drumType) &&
                Objects.equals(diameterInInches, drum.diameterInInches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), drumType, diameterInInches);
    }
}