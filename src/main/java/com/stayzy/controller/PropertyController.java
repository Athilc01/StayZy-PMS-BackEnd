package com.stayzy.controller;

import com.stayzy.model.Property;
import com.stayzy.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {
    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping
    public ResponseEntity<List<Property>> createProperties(@RequestBody List<Property> properties) {
        List<Property> saved = properties.stream()
            .map(propertyService::saveProperty)
            .toList();
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable UUID id) {
        System.out.println("Fetching property with ID: " + id);
        Optional<Property> property = propertyService.getPropertyById(id);
        return property.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable UUID id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Property> searchProperties(
        @RequestParam(required = false) String city,
        @RequestParam(required = false) String propertyType,
        @RequestParam(required = false) BigDecimal minPrice,
        @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return propertyService.searchProperties(city, propertyType, minPrice, maxPrice);
    }

    @GetMapping("/city/{city}")
    public List<Property> getByCity(@PathVariable String city) {
        return propertyService.findByCity(city);
    }

    @GetMapping("/type/{type}")
    public List<Property> getByType(@PathVariable String type) {
        return propertyService.findByPropertyType(type);
    }

    @GetMapping("/price")
    public List<Property> getByPriceRange(
        @RequestParam BigDecimal min,
        @RequestParam BigDecimal max
    ) {
        return propertyService.findByPriceRange(min, max);
    }
}
