package csdev.couponstash.commons.core;

import java.io.Serializable;
import java.util.Objects;

import csdev.couponstash.commons.moneysymbol.MoneySymbol;

/**
 * This class holds the settings of the CouponStash
 * that may be configured by the user, such as the
 * symbol to be used for MonetaryAmount.
 */
public class StashSettings implements Serializable {
    public static final String DEFAULT_MONEY_SYMBOL = "$";

    private static final long serialVersionUID = 9044979298460194104L;

    private final MoneySymbol moneySymbol;

    public StashSettings() {
        this.moneySymbol = MoneySymbol.getMoneySymbol(StashSettings.DEFAULT_MONEY_SYMBOL);
    }

    public StashSettings(MoneySymbol moneySymbol) {
        this.moneySymbol = moneySymbol;
    }

    public MoneySymbol getMoneySymbol() {
        return this.moneySymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StashSettings)) {
            return false;
        }

        StashSettings ss = (StashSettings) o;
        // will always return true as there can only be one MoneySymbol
        return this.moneySymbol.equals(ss.moneySymbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.moneySymbol);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Money Symbol : " + this.moneySymbol.getString());
        return sb.toString();
    }
}
