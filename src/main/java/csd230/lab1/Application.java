package csd230.lab1;

import com.github.javafaker.Faker;
import csd230.lab1.entities.*;
import csd230.lab1.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner demo(
            BookRepository bookRepository,
            MagazineRepository magazineRepository,
            DiscMagRepository discMagRepository,
            TicketRepository ticketRepository,
            CartRepository cartRepository,
            ProductEntityRepository productRepository,
            // Niche repositories
            GuitarRepository guitarRepository,
            DrumRepository drumRepository) {

        return args -> {
            Faker faker = new Faker();

            System.out.println("\n" + "=".repeat(80));
            System.out.println("CSD230 LAB 1 - SPRING DATA REPOSITORIES");
            System.out.println("Student: Sujit Bolakhe | Niche: Musical Instruments");
            System.out.println("=".repeat(80) + "\n");


            System.out.println(" Clearing existing data...");
            cartRepository.deleteAll();
            bookRepository.deleteAll();
            magazineRepository.deleteAll();
            discMagRepository.deleteAll();
            ticketRepository.deleteAll();
            guitarRepository.deleteAll();
            drumRepository.deleteAll();


            System.out.println("\n1. CREATE OPERATIONS (JavaFaker Data):");
            System.out.println("-".repeat(60));


            BookEntity book1 = new BookEntity(
                    faker.book().title(),
                    29.99,
                    10,
                    faker.book().author(),
                    faker.code().isbn10()
            );
            bookRepository.save(book1);
            System.out.println("   ✓ Book: '" + book1.getTitle() +
                    "' by " + book1.getAuthor() +
                    " (ISBN: " + book1.getIsbn() + ")");

            BookEntity book2 = new BookEntity(
                    "The " + faker.book().title(),
                    19.99,
                    5,
                    faker.book().author(),
                    faker.code().isbn10()
            );
            bookRepository.save(book2);


            MagazineEntity magazine = new MagazineEntity();
            magazine.setTitle(faker.lorem().word() + " Magazine");
            magazine.setPrice(12.99);
            magazine.setCopies(20);
            magazine.setOrderQty(100);
            magazine.setCurrentIssue(LocalDateTime.now());
            magazineRepository.save(magazine);
            System.out.println("   ✓ Magazine: '" + magazine.getTitle() +
                    "' (Order Qty: " + magazine.getOrderQty() + ")");


            DiscMagEntity discMag = new DiscMagEntity();
            discMag.setTitle("Digital " + faker.lorem().word() + " Magazine");
            discMag.setPrice(15.99);
            discMag.setCopies(25);
            discMag.setOrderQty(150);
            discMag.setCurrentIssue(LocalDateTime.now());
            discMag.setHasDisc(true);
            discMagRepository.save(discMag);
            System.out.println("   ✓ Disc Magazine: '" + discMag.getTitle() +
                    "' (Has Disc: " + discMag.isHasDisc() + ")");

            TicketEntity ticket = new TicketEntity(
                    "Concert ticket for " + faker.artist().name(),
                    89.99,
                    faker.artist().name() + " Live Concert",
                    LocalDateTime.now().plusDays(30)
            );
            ticketRepository.save(ticket);
            System.out.println("   ✓ Ticket: '" + ticket.getEventName() +
                    "' on " + ticket.getEventDate().toLocalDate() +
                    " ($" + ticket.getPrice() + ")");


            System.out.println("\n2.  NICHE HIERARCHY (Musical Instruments):");
            System.out.println("-".repeat(60));


            GuitarEntity guitar = new GuitarEntity();
            guitar.setGuitarType("Electric");
            guitar.setNumberOfStrings(6);
            guitar.setMaterial("Maple");
            guitar.setBrand("Fender");
            guitarRepository.save(guitar);
            System.out.println("   ✓ Guitar: " + guitar.getGuitarType() +
                    " (" + guitar.getBrand() + ") - " +
                    guitar.getNumberOfStrings() + " strings");


            DrumEntity drum = new DrumEntity();
            drum.setDrumType("Snare");
            drum.setDiameterInInches(14);
            drum.setMaterial("Brass");
            drum.setBrand("Pearl");
            drumRepository.save(drum);
            System.out.println("   ✓ Drum: " + drum.getDrumType() +
                    " (" + drum.getBrand() + ") - " +
                    drum.getDiameterInInches() + " inches");


            GuitarEntity acousticGuitar = new GuitarEntity();
            acousticGuitar.setGuitarType("Acoustic");
            acousticGuitar.setNumberOfStrings(6);
            acousticGuitar.setMaterial("Spruce");
            acousticGuitar.setBrand("Yamaha");
            guitarRepository.save(acousticGuitar);

            System.out.println("\n3. MANY-TO-MANY RELATIONSHIP (Cart ↔ Product):");
            System.out.println("-".repeat(60));


            CartEntity cart1 = new CartEntity();
            cart1.addProduct(book1);
            cart1.addProduct(magazine);
            cartRepository.save(cart1);
            System.out.println("   ✓ Cart 1: " + cart1.getProducts().size() + " products (Book + Magazine)");

            productRepository.save(book1);
            productRepository.save(book2);

            CartEntity cart2 = new CartEntity();
            cart2.addProduct(book2);
            cart2.addProduct(ticket);
            cart2.addProduct(book1);
            cartRepository.save(cart2);
            System.out.println("   ✓ Cart 2: " + cart2.getProducts().size() +
                    " products (Book2 + Ticket + Book1 in both carts)");


            CartEntity musicCart = new CartEntity();
            musicCart.addProduct(guitar);
            musicCart.addProduct(drum);
            musicCart.addProduct(acousticGuitar);
            cartRepository.save(musicCart);
            System.out.println("   ✓ Music Cart: " + musicCart.getProducts().size() + " musical instruments");


            System.out.println("\n4.  READ OPERATIONS (Repository Queries):");
            System.out.println("-".repeat(60));


            List<BookEntity> booksByIsbn = bookRepository.findByIsbn(book1.getIsbn());
            System.out.println("   ✓ findByIsbn('" + book1.getIsbn().substring(0, 10) + "...'): " +
                    booksByIsbn.size() + " book(s) found");


            BookEntity foundBook = bookRepository.findById(book1.getId()).orElse(null);
            System.out.println("   ✓ findById(" + book1.getId() + "): " +
                    (foundBook != null ? "'" + foundBook.getTitle() + "'" : "Not found"));


            List<BookEntity> booksLike = bookRepository.findByTitleContaining("The");
            System.out.println("   ✓ findByTitleContaining('The'): " + booksLike.size() + " book(s)");


            List<BookEntity> booksInRange = bookRepository.findByPriceRange(10.0, 30.0);
            System.out.println("   ✓ Custom @Query - findByPriceRange($10-$30): " +
                    booksInRange.size() + " book(s)");


            List<GuitarEntity> electricGuitars = guitarRepository.findByGuitarType("Electric");
            System.out.println("   ✓ GuitarRepository.findByGuitarType('Electric'): " +
                    electricGuitars.size() + " guitar(s)");

            List<DrumEntity> largeDrums = drumRepository.findByDiameterGreaterThan(12);
            System.out.println("   ✓ DrumRepository.findByDiameterGreaterThan(12): " +
                    largeDrums.size() + " drum(s)");


            System.out.println("\n5.  UPDATE OPERATION:");
            System.out.println("-".repeat(60));

            double oldPrice = book1.getPrice();
            book1.setPrice(24.99);
            bookRepository.save(book1);
            System.out.println("   ✓ Updated '" + book1.getTitle() +
                    "' price: $" + oldPrice + " → $" + book1.getPrice());


            guitar.setNumberOfStrings(7);
            guitarRepository.save(guitar);
            System.out.println("   ✓ Updated guitar to " + guitar.getNumberOfStrings() + " strings");


            System.out.println("\n6.  DELETE OPERATION:");
            System.out.println("-".repeat(60));

            int initialSize = cart1.getProducts().size();
            cart1.getProducts().remove(magazine);
            cartRepository.save(cart1);
            System.out.println("   ✓ Removed magazine from Cart 1: " +
                    initialSize + " → " + cart1.getProducts().size() + " products");


            int musicCartInitial = musicCart.getProducts().size();
            musicCart.getProducts().remove(acousticGuitar);
            cartRepository.save(musicCart);
            System.out.println("   ✓ Removed acoustic guitar from Music Cart: " +
                    musicCartInitial + " → " + musicCart.getProducts().size() + " products");


            System.out.println("\n7. VERIFY MANY-TO-MANY RELATIONSHIP:");
            System.out.println("-".repeat(60));

            List<CartEntity> allCarts = cartRepository.findAll();
            System.out.println("   Total carts in database: " + allCarts.size());

            for (CartEntity cart : allCarts) {
                System.out.println("\n  Cart ID: " + cart.getId() +
                        " (" + cart.getProducts().size() + " products)");

                for (ProductEntity product : cart.getProducts()) {

                    if (product instanceof BookEntity b) {
                        System.out.println("     Book: '" + b.getTitle() +
                                "' by " + b.getAuthor() + " ($" + b.getPrice() + ")");
                    } else if (product instanceof MagazineEntity m) {
                        System.out.println("     Magazine: '" + m.getTitle() +
                                "' (Order Qty: " + m.getOrderQty() + ", $" + m.getPrice() + ")");
                    } else if (product instanceof TicketEntity t) {
                        System.out.println("     Ticket: '" + t.getEventName() +
                                "' (" + t.getEventDate().toLocalDate() + ", $" + t.getPrice() + ")");
                    } else if (product instanceof GuitarEntity g) {
                        System.out.println("     Guitar: " + g.getGuitarType() +
                                " - " + g.getBrand() + " (" + g.getNumberOfStrings() + " strings)");
                    } else if (product instanceof DrumEntity d) {
                        System.out.println("     Drum: " + d.getDrumType() +
                                " - " + d.getBrand() + " (" + d.getDiameterInInches() + " inches)");
                    }
                }
            }


            System.out.println("\n8. DATABASE STATISTICS:");
            System.out.println("-".repeat(60));

            System.out.println("    Books: " + bookRepository.count());
            System.out.println("    Magazines: " + magazineRepository.count());
            System.out.println("    Disc Mags: " + discMagRepository.count());
            System.out.println("    Tickets: " + ticketRepository.count());
            System.out.println("    Guitars: " + guitarRepository.count());
            System.out.println("    Drums: " + drumRepository.count());
            System.out.println("    Total Products: " + productRepository.count());
            System.out.println("    Total Carts: " + cartRepository.count());


            System.out.println("\n9. SaleableItem INTERFACE DEMONSTRATION:");
            System.out.println("-".repeat(60));

            System.out.println("   Selling items from Cart 2:");
            for (ProductEntity product : cart2.getProducts()) {
                product.sellItem();
                System.out.println("     Price: $" + product.getPrice());
            }

            System.out.println("\n" + "=".repeat(80));
            System.out.println("LAB 1 DEMONSTRATION COMPLETE");
            System.out.println("All CRUD operations verified");
            System.out.println("Many-to-Many relationship working");
            System.out.println("Niche hierarchy implemented");
            System.out.println("Repository queries functioning");
            System.out.println("=".repeat(80));
        };
    }
}