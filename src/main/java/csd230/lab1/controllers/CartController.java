package csd230.lab1.controllers;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.entities.CartEntity;
import csd230.lab1.entities.OrderEntity;
import csd230.lab1.entities.ProductEntity;
import csd230.lab1.entities.UserEntity;
import csd230.lab1.repositories.BookEntityRepository;
import csd230.lab1.repositories.CartEntityRepository;
import csd230.lab1.repositories.OrderEntityRepository;
import csd230.lab1.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartEntityRepository cartRepository;

    @Autowired
    private BookEntityRepository bookRepository;

    @Autowired
    private OrderEntityRepository orderRepository;

    @Autowired
    private UserEntityRepository userRepository;


    private CartEntity getCartForCurrentUser(Principal principal) {
        String username = principal.getName();

        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {


            throw new RuntimeException("Logged-in user not found in database: " + username);
        }

        CartEntity cart = cartRepository.findByUser(user);

        if (cart == null) {
            cart = new CartEntity();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }

        return cart;
    }

    @GetMapping
    public String viewCart(Model model, Principal principal) {
        CartEntity cart = getCartForCurrentUser(principal);
        model.addAttribute("cart", cart);
        return "cartDetails";
    }

    @GetMapping("/add/{bookId}")
    public String addToCart(@PathVariable Long bookId, Principal principal) {
        CartEntity cart = getCartForCurrentUser(principal);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (book != null) {
            cart.addProduct(book);
            cartRepository.save(cart);
        }

        return "redirect:/books";
    }

    @GetMapping("/remove/{bookId}")
    public String removeFromCart(@PathVariable Long bookId, Principal principal) {
        CartEntity cart = getCartForCurrentUser(principal);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (book != null) {
            cart.getProducts().remove(book);
            cartRepository.save(cart);
        }

        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(Principal principal) {
        CartEntity cart = getCartForCurrentUser(principal);

        if (cart.getProducts() == null || cart.getProducts().isEmpty()) {
            return "redirect:/cart";
        }

        OrderEntity order = new OrderEntity();
        order.setOrderDate(LocalDateTime.now());

        double total = 0.0;


        var productsToBuy = new ArrayList<>(cart.getProducts());

        for (ProductEntity product : productsToBuy) {
            total += product.getPrice();


            if (product instanceof BookEntity book) {
                if (book.getCopies() > 0) {
                    book.setCopies(book.getCopies() - 1);
                    bookRepository.save(book);
                }
            }

            order.getProducts().add(product);
        }

        order.setTotalAmount(total);
        orderRepository.save(order);


        cart.getProducts().clear();
        cartRepository.save(cart);

        return "redirect:/orders/" + order.getId();
    }
}
