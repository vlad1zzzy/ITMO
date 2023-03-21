package model;

import org.bson.Document;

public class Product {
    private final long id;
    private final Currency currency;
    private final double price;

    public Product(final Document document) {
        this(
                document.getLong("id"),
                document.getString("currency"),
                document.getDouble("price")
        );
    }

    public Product(final long id, final String currency, final double price) {
        this.id = id;
        this.currency = Currency.valueOf(currency);
        this.price = price;
    }

    public Document toDocument() {
        return new Document()
                .append("id", id)
                .append("currency", currency.toString())
                .append("price", price);
    }

    public Product convert(final String otherCurrency) {
        return new Product(id, otherCurrency, price * currency.value(Currency.valueOf(otherCurrency)));
    }

    @Override
    public String toString() {
        return "Product {\n" +
                "   id :       " + id + ",\n" +
                "   currency : " + currency + ",\n" +
                "   price :    " + price + "\n" +
                "}";
    }
}