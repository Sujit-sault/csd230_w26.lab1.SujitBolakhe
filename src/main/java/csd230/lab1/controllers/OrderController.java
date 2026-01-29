package csd230.lab1.controllers;

import csd230.lab1.entities.OrderEntity;
import csd230.lab1.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * View all orders
     */
    @GetMapping
    public String getAllOrders(Model model) {
        List<OrderEntity> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orderList";
    }

    /**
     * View a specific order (Order Confirmation Page)
     */
    @GetMapping("/{id}")
    public String getOrderById(@PathVariable Long id, Model model) {
        OrderEntity order = orderRepository.findById(id).orElse(null);
        model.addAttribute("order", order);
        return "orderDetails";
    }
}