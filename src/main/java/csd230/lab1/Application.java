package csd230.lab1;

import com.github.javafaker.Commerce;
import com.github.javafaker.Faker;
import csd230.lab1.entities.BookEntity;
import csd230.lab1.entities.CartEntity;
import csd230.lab1.entities.ProductEntity;
import csd230.lab1.entities.UserEntity;
import csd230.lab1.repositories.CartEntityRepository;
import csd230.lab1.repositories.ProductEntityRepository;
import csd230.lab1.repositories.UserEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final ProductEntityRepository productRepository;
    private final CartEntityRepository cartRepository;
    private final UserEntityRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Application(ProductEntityRepository productRepository,
                       CartEntityRepository cartRepository,
                       UserEntityRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Allow access to all /api endpoints from any origin
                registry.addMapping("/api/**")
                        .allowedOrigins("*");
            }
        };
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Faker faker = new Faker();
        Commerce cm = faker.commerce();
        com.github.javafaker.Book fakeBook = faker.book();
        String priceString = faker.commerce().price();

        BookEntity book = new BookEntity(
                fakeBook.title(),
                Double.parseDouble(priceString),
                10,
                fakeBook.author()
        );

        csd230.lab1.entities.MagazineEntity magazine =
                new csd230.lab1.entities.MagazineEntity(
                        faker.lorem().word() + " Magazine",
                        12.99,
                        20,
                        50,
                        java.time.LocalDateTime.now()
                );

        CartEntity cart = new CartEntity();
        cartRepository.save(cart);

        cart.addProduct(book);
        cartRepository.save(cart);

        cart.addProduct(magazine);
        cartRepository.save(cart);

        List<ProductEntity> allProducts = productRepository.findAll();
        for (ProductEntity p : allProducts) {
            System.out.println(p);
        }

        List<CartEntity> allCarts = cartRepository.findAll();
        for (CartEntity c : allCarts) {
            System.out.println(c);
            for (ProductEntity p : c.getProducts()) {
                System.out.println(p);
            }
        }


        UserEntity admin = new UserEntity(
                "admin",
                passwordEncoder.encode("admin"),
                "ADMIN"
        );
        userRepository.save(admin);

        UserEntity user = new UserEntity(
                "user",
                passwordEncoder.encode("user"),
                "USER"
        );
        userRepository.save(user);

        System.out.println("Default users created: admin/admin and user/user");
    }
}
