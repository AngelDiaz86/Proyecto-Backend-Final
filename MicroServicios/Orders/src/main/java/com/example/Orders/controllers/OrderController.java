package com.example.Orders.controllers;

import com.example.Orders.model.Order;
import com.example.Orders.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/orders")
@RequiredArgsConstructor

public class OrderController {

    private final OrderService orderService;

    // Get orders
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getOrders() {return orderService.getOrders();}

    // Create order
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object>newOrder (@Valid @RequestBody Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            //Manejo de errores
            List <String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return orderService.newOrder(order);
    }

    // Update order
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateOrder(@PathVariable("id") Long id, @RequestBody Order updatedOrder) {
        return orderService.updateOrder(id, updatedOrder);
    }

    // Delete order
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteOrder(@PathVariable("id") Long id) {
        return orderService.deleteOrder(id);
    }
}
