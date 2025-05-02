package com.example.backend.model;

public class Product {
    private int orderId;
    private int id;
    private String name;
    private String description;
    private double price;
    private String image;

    // Getters and Setters
    public int getorderId() { return orderId; }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    private int quantity;  // Add this field if not already present

    public int getQuantity() {
        return quantity;  // Add the getter method
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;  // Add the setter method
    }

}
