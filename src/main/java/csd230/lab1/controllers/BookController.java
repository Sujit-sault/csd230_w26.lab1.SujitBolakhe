package csd230.lab1.controllers;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.repositories.BookEntityRepository;
import csd230.lab1.repositories.CartEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Books", description = "Operations related to Book Management")
@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookEntityRepository bookRepository;

    @Autowired
    private CartEntityRepository cartRepository;

    @Operation(
            summary = "Get all books",
            description = "Returns the HTML view of the book list"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public String getAllBooks(Model model) {
        List<BookEntity> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "bookList";
    }

    @Operation(
            summary = "View book details",
            description = "Returns details for a single book"
    )
    @ApiResponse(responseCode = "200", description = "Book found")
    @GetMapping("/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        BookEntity book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        return "bookDetails";
    }

    @Operation(
            summary = "Add new book",
            description = "Admin-only: Displays form to add a new book"
    )
    @ApiResponse(responseCode = "200", description = "Form loaded")
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new BookEntity());
        return "bookForm";
    }

    @Operation(
            summary = "Save new book",
            description = "Admin-only: Saves a new book"
    )
    @ApiResponse(responseCode = "302", description = "Redirect to book list")
    @PostMapping("/add")
    public String saveBook(@ModelAttribute BookEntity book) {
        bookRepository.save(book);
        return "redirect:/books";
    }

    @Operation(
            summary = "Edit book",
            description = "Admin-only: Displays edit form for a book"
    )
    @ApiResponse(responseCode = "200", description = "Edit form loaded")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        BookEntity book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        return "bookForm";
    }


    @Operation(
            summary = "Delete book",
            description = "Admin-only: Deletes a book and removes it from all carts"
    )
    @ApiResponse(responseCode = "302", description = "Book deleted")
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {

        BookEntity book = bookRepository.findById(id).orElse(null);
        if (book != null) {

            cartRepository.findCartsContainingProduct(id)
                    .forEach(cart -> {
                        cart.getProducts().remove(book);
                        cartRepository.save(cart);
                    });

            bookRepository.delete(book);
        }
        return "redirect:/books";
    }
}
