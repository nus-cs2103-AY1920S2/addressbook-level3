package csdev.couponstash.testutil;

import csdev.couponstash.commons.moneysymbol.MoneySymbol;

/**
 * A money symbol stub that does not provide full
 * functionality of the real MoneySymbol (which
 * relies on mutability). However, MoneySymbolStub
 * is immutable as compared to MoneySymbol.
 */
public class MoneySymbolStub implements MoneySymbol {
    private String moneySymbol;

    public MoneySymbolStub(String moneySymbol) {
        this.moneySymbol = moneySymbol;
    }

    @Override
    public String setString(String newSymbol) {
        // does not do anything
        return this.moneySymbol;
    }

    @Override
    public String getString() {
        return this.moneySymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MoneySymbol) {
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
