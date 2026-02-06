package csd230.lab1.pojos;

import java.util.Objects;

public class Guitar extends MusicalInstrument {
    private Integer numberOfStrings;
    private String guitarType; // Acoustic, Electric, Bass

    public Guitar() {
        super();
    }

    public Guitar(String title, Double price, Integer quantity, String description,
                  String material, String brand, Integer numberOfStrings, String guitarType) {
        super(title, price, quantity, description, material, brand);
        this.numberOfStrings = numberOfStrings;
        this.guitarType = guitarType;
    }

    @Override
    public void edit() {
        super.edit(); // Call parent's edit
        System.out.println("Editing Guitar specific properties...");
    }

    @Override
    public void initialize() {
        super.initialize(); // Call parent's initialize
        System.out.println("Initializing Guitar specific properties...");
    }


    @Override
    public void sellItem() {
        System.out.println("Selling Guitar: " + getTitle() +
                " (" + guitarType + ", " + numberOfStrings + " strings)");
    }


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
        return "Guitar{" +
                "numberOfStrings=" + numberOfStrings +
                ", guitarType='" + guitarType + '\'' +
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
        if (!(o instanceof Guitar)) return false;
        if (!super.equals(o)) return false;
        Guitar guitar = (Guitar) o;
        return Objects.equals(numberOfStrings, guitar.numberOfStrings) &&
                Objects.equals(guitarType, guitar.guitarType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numberOfStrings, guitarType);
    }
}