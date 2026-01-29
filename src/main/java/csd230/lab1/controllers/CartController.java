package csd230.lab1.controllers;

import csd230.lab1.entities.CartEntity;
import csd230.lab1.entities.OrderEntity;
import csd230.lab1.entities.ProductEntity;
import csd230.lab1.entities.PublicationEntity;
import csd230.lab1.repositories.CartEntityRepository;
import csd230.lab1.repositories.OrderRepository;
import csd230.lab1.repositories.ProductEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartEntityRepository cartRepository;

    @Autowired
    private ProductEntityRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Display the shopping cart
     */
    @GetMapping
    public String viewCart(Model model) {
        // Get or create the default cart (ID = 1)
        CartEntity cart = cartRepository.findById(1L).orElseGet(() -> {
            CartEntity newCart = new CartEntity();
            return cartRepository.save(newCart);
        });

        // Calculate total
        double total = cart.getProducts().stream()
                .mapToDouble(ProductEntity::getPrice)
                .sum();

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cartDetails";
    }

    /**
     * Add a product to the cart
     */
    @GetMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId, RedirectAttributes redirectAttributes) {
        // Get or create cart
        CartEntity cart = cartRepository.findById(1L).orElseGet(() -> {
            CartEntity newCart = new CartEntity();
            return cartRepository.save(newCart);
        });

        // Find the product
        ProductEntity product = productRepository.findById(productId).orElse(null);

        if (product != null) {
            cart.addProduct(product);
            cartRepository.save(cart);
            redirectAttributes.addFlashAttribute("message", "Product added to cart!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Product not found!");
        }

        return "redirect:/cart";
    }

    /**
     * Remove a product from the cart
     */
    @GetMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId, RedirectAttributes redirectAttributes) {
        CartEntity cart = cartRepository.findById(1L).orElse(null);

        if (cart != null) {
            ProductEntity product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                cart.getProducts().remove(product);
                product.getCarts().remove(cart);
                cartRepository.save(cart);
                redirectAttributes.addFlashAttribute("message", "Product removed from cart!");
            }
        }

        return "redirect:/cart";
    }

    /**
     * LAB 2: Checkout Process
     */
    @PostMapping("/checkout")
    public String checkout(RedirectAttributes redirectAttributes) {
        // 1. Retrieve the Cart
        CartEntity cart = cartRepository.findById(1L).orElse(null);

        if (cart == null || cart.getProducts().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Cart is empty! Please add items before checkout.");
            return "redirect:/cart";
        }

        // 2. Create the Order
        OrderEntity order = new OrderEntity();
        order.setOrderDate(LocalDateTime.now());

        double total = 0.0;

        // 3. Process Products & Inventory
        for (ProductEntity product : cart.getProducts()) {
            // Calculate Total
            total += product.getPrice();

            // Update Stock (only for Publications/Books)
            if (product instanceof PublicationEntity) {
                PublicationEntity publication = (PublicationEntity) product;

                // Check if stock is available
                if (publication.getCopies() > 0) {
                    publication.setCopies(publication.getCopies() - 1);
                    productRepository.save(publication);
                } else {
                    // Optional: Handle out of stock scenario
                    redirectAttributes.addFlashAttribute("warning",
                            "Warning: " + publication.getTitle() + " is out of stock!");
                }
            }

            // Add to Order
            order.getProducts().add(product);
        }

        order.setTotalAmount(total);

        // 4. Clear the Cart
        cart.getProducts().clear();

        // 5. Persist Data
        orderRepository.save(order);
        cartRepository.save(cart);

        // 6. Redirect to Order Confirmation
        return "redirect:/orders/" + order.getId();
    }
}