package csdev.couponstash.commons.moneysymbol;

/**
 * Concrete implementation of MoneySymbol interface.
 */
public class MoneySymbolImpl implements MoneySymbol {
    // Warning: This should NOT be accessed by any other class
    // other than MoneySymbol!
    protected static MoneySymbol theOne = null;

    private String moneySymbol;

    /**
     * WARNING: THIS METHOD SHOULD NOT BE RUN OUTSIDE
     * OF MONEY_SYMBOL and MONEY_SYMBOL_IMPL!
     * @param moneySymbol The String representing
     *                    money symbol.
     */
    protected MoneySymbolImpl(String moneySymbol) {
        this.moneySymbol = moneySymbol;
    }

    @Override
    public String setString(String newSymbol) {
        String oldSymbol = moneySymbol;
        this.moneySymbol = newSymbol;
        return oldSymbol;
    }

    @Override
    public String getString() {
        return this.moneySymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof MoneySymbol) {
            return ((MoneySymbol) o).getString().equals(this.moneySymbol);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.moneySymbol;
    }
}
