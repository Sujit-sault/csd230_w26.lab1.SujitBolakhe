package csd230.lab1.controllers;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.entities.CartEntity;
import csd230.lab1.entities.OrderEntity;
import csd230.lab1.entities.ProductEntity;
import csd230.lab1.repositories.BookEntityRepository;
import csd230.lab1.repositories.CartEntityRepository;
import csd230.lab1.repositories.OrderEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartEntityRepository cartRepository;

    @Autowired
    private BookEntityRepository bookRepository;

    @Autowired
    private OrderEntityRepository orderRepository;


    @GetMapping
    public String viewCart(Model model) {
        Long defaultCartId = 1L;

        CartEntity cart = cartRepository.findById(defaultCartId)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setId(defaultCartId);
                    return cartRepository.save(newCart);
                });

        model.addAttribute("cart", cart);
        return "cartDetails";
    }


    @GetMapping("/add/{bookId}")
    public String addToCart(@PathVariable Long bookId) {
        Long defaultCartId = 1L;

        CartEntity cart = cartRepository.findById(defaultCartId).orElse(null);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (cart != null && book != null) {
            cart.addProduct(book);
            cartRepository.save(cart);
        }

        return "redirect:/books";
    }


    @GetMapping("/remove/{bookId}")
    public String removeFromCart(@PathVariable Long bookId) {
        Long defaultCartId = 1L;

        CartEntity cart = cartRepository.findById(defaultCartId).orElse(null);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (cart != null && book != null) {
            cart.getProducts().remove(book);
            cartRepository.save(cart);
        }

        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout() {
        Long defaultCartId = 1L;

        CartEntity cart = cartRepository.findById(defaultCartId).orElse(null);

        if (cart == null || cart.getProducts().isEmpty()) {
            return "redirect:/cart";
        }

        OrderEntity order = new OrderEntity();
        order.setOrderDate(LocalDateTime.now());

        double total = 0.0;


        var productsToBuy = new java.util.ArrayList<>(cart.getProducts());

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
