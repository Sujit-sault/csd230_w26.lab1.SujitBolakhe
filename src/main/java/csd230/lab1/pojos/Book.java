package csd230.lab1.pojos;

import java.util.Objects;

public class Book extends Publication {
    private String author = "";
    private String isbn = "";  // ONLY ADD THIS LINE

    public Book() {
        super();
    }

    public Book(String author) {
        this.author = author;
    }

    public Book(String author, String title, double price, int copies) {
        super(title, price, copies);
        this.author = author;
    }


    public Book(String author, String title, double price, int copies, String isbn) {
        super(title, price, copies);
        this.author = author;
        this.isbn = isbn;
    }

    @Override
    public void initialize() {
        super.initialize();
        System.out.println("Enter Author:");
        this.author = getInput("Unknown Author");
        super.initPriceCopies();
    }

    @Override
    public void edit() {
        super.edit();
        System.out.println("Edit Author [" + this.author + "]:");
        this.author = getInput(this.author);
    }

    @Override
    public void sellItem() {
        System.out.println("Selling Book: " + getTitle() + " by " + author);
        setCopies(getCopies() - 1);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book{author='" + author + "', isbn='" + isbn + "', " + super.toString() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return Objects.equals(author, book.author) &&
                Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author, isbn);
    }
}