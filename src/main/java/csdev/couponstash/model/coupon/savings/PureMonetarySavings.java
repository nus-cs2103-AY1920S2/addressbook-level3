package csdev.couponstash.model.coupon.savings;

import java.util.ArrayList;
import java.util.List;

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
    /**
     * Constructor for a PureMonetarySavings that
     * represents no money saved (MonetaryAmount
     * wil be set to 0)
     */
    public PureMonetarySavings() {
        super(new MonetaryAmount(0));
    }

    /**
     * Constructor for a PureMonetarySavings that
     * represents no money saved (MonetaryAmount
     * wil be set to 0), but contains certain
     * Saveables (this will be stored as usual)
     * @param saveables The saveables to be stored
     *                  as Savings.
     */
    public PureMonetarySavings(List<Saveable> saveables) {
        super(new MonetaryAmount(0), saveables);
    }

    public PureMonetarySavings(MonetaryAmount monetaryAmount) {
        super(monetaryAmount);
    }

    public PureMonetarySavings(MonetaryAmount monetaryAmount, List<Saveable> saveables) {
        super(monetaryAmount, saveables);
    }

    /**
     * Combines two PureMonetarySavings, giving a new
     * PureMonetarySavings that takes into account
     * monetary amounts in both PureMonetarySavings,
     * as well as the Saveable items.
     * @param pms The other PureMonetarySavings to be
     *            combined with this PureMonetarySavings.
     * @return Returns a new PureMonetarySavings that adds the
     *     monetary amounts, and contains all the Saveables
     *     from both PureMonetarySavings.
     */
    public PureMonetarySavings add(PureMonetarySavings pms) {
        MonetaryAmount newAmount = new MonetaryAmount(
                this.getMonetaryAmountAsDouble() + pms.getMonetaryAmountAsDouble());
        List<Saveable> combinedSaveables = this.getListOfSaveables();
        combinedSaveables.addAll(pms.getListOfSaveables());
        if (combinedSaveables.isEmpty()) {
            return new PureMonetarySavings(newAmount);
        } else {
            return new PureMonetarySavings(newAmount, combinedSaveables);
        }
    }

    /**
     * Gets the double value of monetary savings
     * that is represented by this PureMonetarySavings.
     * @return Double representing all the money
     *     saved in this PureMonetarySavings.
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
     * @return List representing all Saveables in this
     *     PureMonetarySavings (can be empty)
     */
    public List<Saveable> getListOfSaveables() {
        if (super.hasSaveables()) {
            return super.getSaveables().get();
        } else {
            return new ArrayList<Saveable>();
        }
    }
}
