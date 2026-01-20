package csd230.lab1;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.entities.CartEntity;
import csd230.lab1.repositories.BookRepository;
import csd230.lab1.repositories.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CartRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testCreateCart() {
        CartEntity cart = new CartEntity();
        CartEntity saved = cartRepository.save(cart);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    public void testManyToManyRelationship() {
        BookEntity book = new BookEntity();
        book.setTitle("Test Book");
        book.setIsbn("TEST123");
        BookEntity savedBook = bookRepository.save(book);

        CartEntity cart = new CartEntity();
        cart.addProduct(savedBook);
        CartEntity savedCart = cartRepository.save(cart);

        entityManager.flush();
        entityManager.clear();

        CartEntity found = cartRepository.findById(savedCart.getId()).orElseThrow();
        assertThat(found.getProducts()).isNotEmpty();
    }
}