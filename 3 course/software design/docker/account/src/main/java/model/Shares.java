package model;

public class Shares {
    private final String companyName;
    private final int count;
    private final int price;

    public Shares(final String companyName, final int count, final int price) {
        this.companyName = companyName;
        this.count = count;
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
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