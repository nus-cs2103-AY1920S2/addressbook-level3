package csdev.couponstash.model.coupon.savings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import csdev.couponstash.model.coupon.exceptions.InvalidSavingsException;

/**
 * Represents a certain amount of Savings in
 * CouponStash, but with the restriction that
 * the Saving must be an actual MonetaryAmount.
 *
 * <p>Saveables are allowed as well, but not
 * required.
 *
 * <p>In practice, PureMonetarySavings is used
 * to represent a sum of many Coupons' Savings,
 * while Savings is used for individual Coupons.
 */
public class PureMonetarySavings extends Savings {
    public static final String MESSAGE_CONSTRAINTS = "PureMonetarySavings "
            + "must have a double representing MonetaryAmount.";

    /**
     * Constructor for a PureMonetarySavings that
     * represents no money saved (MonetaryAmount
     * wil be set to 0)
     */
    public PureMonetarySavings() {
        super(new MonetaryAmount(0, 0));
    }

    /**
     * Constructor for a PureMonetarySavings that
     * represents no money saved (MonetaryAmount
     * wil be set to 0), but contains certain
     * Saveables (this will be stored as usual)
     *
     * @param saveables The saveables to be stored
     *                  as Savings.
     */
    public PureMonetarySavings(List<Saveable> saveables) {
        super(new MonetaryAmount(0, 0), saveables);
    }

    public PureMonetarySavings(MonetaryAmount monetaryAmount) {
        super(monetaryAmount);
    }

    public PureMonetarySavings(MonetaryAmount monetaryAmount, List<Saveable> saveables) {
        super(monetaryAmount, saveables);
    }

    /**
     * Cloning constructor for PureMonetarySavings. The internal
     * MonetaryAmount and Saveables will not be cloned, as they
     * are immutable. But the Saveables List will be cloned.
     * Assumes pms is a valid PureMonetarySavings.
     *
     * @param pms The PureMonetarySavings to be cloned.
     */
    public PureMonetarySavings(PureMonetarySavings pms) {
        super(pms);
    }

    /**
     * Combines two PureMonetarySavings, giving a new
     * PureMonetarySavings that takes into account
     * monetary amounts in both PureMonetarySavings,
     * as well as the Saveable items.
     *
     * This method assumes that the original Saveable
     * lists in "this" is unique (no duplicate Saveables
     * with the same String names).
     *
     * @param pms The other PureMonetarySavings to be
     *            combined with this PureMonetarySavings.
     * @return Returns a new PureMonetarySavings that adds the
     * monetary amounts, and contains all the Saveables
     * from both PureMonetarySavings.
     */
    public PureMonetarySavings add(PureMonetarySavings pms) {
        if (!this.hasMonetaryAmount() || !pms.hasMonetaryAmount()) {
            // should never be thrown as PureMonetarySavings
            // should not be created without MonetaryAmount
            throw new InvalidSavingsException("PureMonetarySavings without MonetaryAmount");
        }
        MonetaryAmount newAmount = this.getMonetaryAmount().get().add(pms.getMonetaryAmount().get());
        List<Saveable> originalSaveables = this.getListOfSaveables();
        List<Saveable> newSaveables = pms.getListOfSaveables();
        List<Saveable> combinedSaveables = new ArrayList<Saveable>();

        // try to increase count of Saveables with same name
        Map<String, Saveable> saveableMap = originalSaveables.stream()
                .collect(Collectors.toMap(Saveable::getValue, sva -> sva));
        for (Saveable s : newSaveables) {
            // try to get from map
            String value = s.getValue();
            Saveable retrieveFromMap = saveableMap.get(value);
            if (retrieveFromMap != null) {
                // item seen before, increase count in map
                Saveable increased = s.increaseCount(retrieveFromMap);
                saveableMap.put(value, increased);
            } else {
                // new item, just add to list
                combinedSaveables.add(s);
            }
        }
        // put all from map into the list
        combinedSaveables.addAll(saveableMap.values());

        if (combinedSaveables.isEmpty()) {
            return new PureMonetarySavings(newAmount);
        } else {
            return new PureMonetarySavings(newAmount, combinedSaveables);
        }
    }

    /**
     * Gets the double value of monetary savings
     * that is represented by this PureMonetarySavings.
     *
     * @return Double representing all the money
     * saved in this PureMonetarySavings.
     */
    public double getMonetaryAmountAsDouble() {
        if (!super.hasMonetaryAmount()) {
            // should never throw this as all PureMonetarySavings
            // can only be created with MonetaryAmounts
            throw new InvalidSavingsException("PureMonetarySavings without MonetaryAmount");
        } else {
            return super.getMonetaryAmount().get().getValue();
        }
    }

    /**
     * Gets the List of all Saveables represented in
     * this PureMonetarySavings. If there are no items,
     * it returns an empty list.
     *
     * @return List representing all Saveables in this
     * PureMonetarySavings (can be empty)
     */
    public List<Saveable> getListOfSaveables() {
        if (super.hasSaveables()) {
            return super.getSaveables().get();
        } else {
            return new ArrayList<Saveable>();
        }
    }

    @Override
    public String getStringWithMoneySymbol(String symbol) {
        return "Saved: " + this.getMonetaryAmount().get().getStringWithMoneySymbol(symbol)
                + this.getSaveables().map(svaList -> {
                    StringBuilder saveablesString = new StringBuilder();
                    for (Saveable sva : svaList) {
                        saveablesString.append(", ");
                        saveablesString.append(sva.toString());
                    }
                    return saveablesString.toString();
                }).orElse("");
    }

    /**
     * Make a new copy of current PureMonetarySavings
     *
     * @return a copy of the current PureMonetarySavings
     */
    @Override
    public PureMonetarySavings copy() {
        Optional<MonetaryAmount> monetaryAmountCopy =
                getMonetaryAmount().map(MonetaryAmount::new);
        Optional<List<Saveable>> savablesCopy = getSaveables().map(ArrayList::new);

        if (monetaryAmountCopy.isEmpty() && savablesCopy.isEmpty()) {
            return new PureMonetarySavings();
        } else if (monetaryAmountCopy.isEmpty()) {
            return new PureMonetarySavings(savablesCopy.get());
        } else if (savablesCopy.isEmpty()) {
            return new PureMonetarySavings(monetaryAmountCopy.get());
        } else {
            return new PureMonetarySavings(monetaryAmountCopy.get(), savablesCopy.get());
        }
    }
}
