package model;

import org.bson.Document;

public class Shares {
    private final String companyName;
    private final int count;
    private final int price;

    public Shares(final Document document) {
        this(document.getString("companyName"), document.getInteger("count"), document.getInteger("price"));
    }

    public Shares(final String companyName, final int count, final int price) {
        this.companyName = companyName;
        this.count = count;
        this.price = price;
    }

    public Document toDocument() {
        return new Document()
                .append("companyName", companyName)
                .append("count", count)
                .append("price", price);
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
    }

    public Shares changePrice(final int newStockPrice) {
        return new Shares(companyName, count, newStockPrice);
    }

    public Shares add(final int sharesCount) {
        return new Shares(companyName, count + sharesCount, price);
    }

    public Shares subtract(final int sharesCount) {
        return new Shares(companyName, count - sharesCount, price);
    }

    @Override
    public String toString() {
        return "Shares {\n" +
                "   companyName : " + companyName + ",\n" +
                "   count :       " + count + ",\n" +
                "   price :       " + price + "\n" +
                "}";
    }
}
