package model;

import org.bson.Document;

public class Customer {
    private final long id;
    private final Currency currency;

    public Customer(final Document document) {
        this(
                document.getLong("id"),
                document.getString("currency")
        );
    }

    public Customer(final long id, final String currency) {
        this.id = id;
        this.currency = Currency.valueOf(currency);
    }

    public Document toDocument() {
        return new Document()
                .append("id", id)
                .append("currency", currency.toString());
    }

    @Override
    public String toString() {
        return "Customer {\n" +
                "   id :       " + id + ",\n" +
                "   currency : " + currency + "\n" +
                "}";
    }
}