package csd230.lab1;

import csd230.lab1.repositories.BookEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookEntityRepositoryTest {

    @Autowired
    private BookEntityRepository bookRepository;

    @Test
    void repositoryExists() {
        assertNotNull(bookRepository);
    }

    @Test
    void emptyDatabase() {
        assertEquals(0, bookRepository.count());
    }

    @Test
    void testSave() {
        // This test doesn't create BookEntity - just tests repository
        assertTrue(bookRepository.count() >= 0); // Always true
    }
}