package csdev.couponstash.commons.moneysymbol;

/**
 * Mutable class for holding the money symbol
 * used in Coupon Stash. A wrapper class around a String,
 * this ensures that all references to the money symbol
 * will be changed once the String is changed.
 *
 * Follows the singleton pattern. Only one MoneySymbol exists
 * and acts as the universal source of truth for every occurence.
 */
public interface MoneySymbol {
    /**
     * Returns the single MoneySymbol if it exists.
     * If it does not exist, create it with value
     * represented in the String initial.
     *
     * @param initial The initial String value to be stored,
     *                if the MoneySymbol did not exist.
     * @return The MoneySymbol.
     */
    static MoneySymbol getMoneySymbol(String initial) {
        if (MoneySymbolImpl.theOne == null) {
            MoneySymbolImpl.theOne = new MoneySymbolImpl(initial);
        }
        return MoneySymbolImpl.theOne;
    }

    /**
     * Changes the String representing money symbol
     * stored in this MoneySymbol to a new String.
     * @param newSymbol The new symbol to be set.
     * @return Returns the old symbol.
     */
    String setString(String newSymbol);

    /**
     * Gets the String representing money symbol
     * stored in this MoneySymbol object.
     * @return The current money symbol.
     */
    String getString();
}
