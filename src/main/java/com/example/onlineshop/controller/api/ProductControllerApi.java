package com.example.onlineshop.controller.api;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/products")
public class ProductControllerApi {

    private final Map<Integer, String> productStore = new HashMap<>();
    private int currentId = 1;

    @GetMapping
    public List<Map<String, Object>> getAllProducts() {
        List<Map<String, Object>> products = new ArrayList<>();
        for (var entry : productStore.entrySet()) {
            products.add(Map.of("id", entry.getKey(), "name", entry.getValue()));
        }
        return products;
    }

    @PostMapping
    public Map<String, Object> createProduct(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        productStore.put(currentId, name);
        return Map.of("id", currentId++, "name", name);
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateProduct(@PathVariable int id, @RequestBody Map<String, String> payload) {
        if (!productStore.containsKey(id)) {
            throw new IllegalArgumentException("Product not found");
        }
        String name = payload.get("name");
        productStore.put(id, name);
        return Map.of("id", id, "name", name);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        productStore.remove(id);
    }
}
