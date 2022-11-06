package ru.akirakozov.sd.refactoring.model;

import java.util.Objects;

public class Product {
    private final String name;
    private final long price;

    public Product(final String name, final long price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String toHTML() {
        return String.format("%s\t%d</br>", getName(), getPrice());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Product product = (Product) o;
        return getPrice() == product.price && Objects.equals(getName(), product.name);
    }

    @Override
    public String toString() {
        return String.format("Product { name: %s, price: %d }", getName(), getPrice());
    }
}