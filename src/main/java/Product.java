package com.student_work;

import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private String category;
    private double price;

    public Product(int id, String name, String category, double price) {
        this.id = id;
        this.name = (name == null) ? "" : name;
        this.category = (category == null) ? "" : category;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public String getCategory() {
        return category == null ? "" : category;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product other = (Product) o;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product ID: " + id + "\n" +
                "Name: " + getName() + "\n" +
                "Category: " + getCategory() + "\n" +
                String.format("Price: $%.2f", price);
    }
}
