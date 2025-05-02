package com.example.backend.controller;

import com.example.backend.model.Product;
import com.example.backend.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:8000")
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }


    
    @PostMapping("/checkout")
public ResponseEntity<String> checkout(@RequestBody List<Product> cartItems) {
    productService.saveCartItems(cartItems);
    return ResponseEntity.ok("Successfully ordered!");
}


@GetMapping("/orders")
public List<Map<String, Object>> getOrderHistory() {
    System.out.println("Fetching order history...");  // Log the request
    return productService.getOrderHistory();
}



@DeleteMapping("/orders/{orderId}")
public ResponseEntity<String> deleteOrder(@PathVariable int orderId) {
    try {
        productService.deleteOrder(orderId);
        return ResponseEntity.ok("Order deleted successfully!");
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body("Error deleting order");
    }
}







}
