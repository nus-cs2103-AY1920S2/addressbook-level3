package csdev.couponstash.commons;

/**
 * Mutable class for holding the money symbol
 * used in Coupon Stash. A wrapper class around a String,
 * this ensures that all references to the money symbol
 * will be changed once the String is changed.
 *
 * Follows the singleton pattern. Only one MoneySymbol exists
 * and acts as the universal source of truth for every occurence.
 */
public class MoneySymbol {
    private static MoneySymbol theOne = null;
    private String moneySymbol;

    private MoneySymbol(String moneySymbol) {
        this.moneySymbol = moneySymbol;
    }

    /**
     * Returns the single MoneySymbol if it exists.
     * If it does not exist, create it.
     * @param initial The initial String value to be stored.
     * @return The MoneySymbol.
     */
    public static MoneySymbol getMoneySymbol(String initial) {
        if (MoneySymbol.theOne == null) {
            MoneySymbol.theOne = new MoneySymbol(initial);
        }
        return MoneySymbol.theOne;
    }

    /**
     * Changes the String representing money symbol
     * stored in this MoneySymbol to a new String.
     * @param newSymbol The new symbol to be set.
     * @return Returns the old symbol.
     */
    public String setString(String newSymbol) {
        String oldSymbol = moneySymbol;
        this.moneySymbol = newSymbol;
        return oldSymbol;
    }

    /**
     * Gets the String representing money symbol
     * stored in this MoneySymbol object.
     * @return The current money symbol.
     */
    public String getString() {
        return this.moneySymbol;
    }

    @Override
    public String toString() {
        return this.moneySymbol;
    }
}
