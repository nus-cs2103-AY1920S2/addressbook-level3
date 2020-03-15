package seedu.address.model.offer;

import seedu.address.model.good.Good;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

public class Offer {
    private final Good good;
    private final Price price;

    /**
     * Constructs an {@code Offer}.
     * @param good the good to be offered
     * @param price the price of the good in the offer
     */
    public Offer(Good good, Price price) {
        requireAllNonNull(good, price);
        this.good = good;
        this.price = price;
    }

    public Good getGood() {
        return good;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Good: " + good + " | Price: " + price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Offer offer = (Offer) o;
        return getGood().equals(offer.getGood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGood());
    }
}