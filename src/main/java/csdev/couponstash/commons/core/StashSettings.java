package csdev.couponstash.commons.core;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class holds the settings of the CouponStash
 * that may be configured by the user, such as the
 * symbol to be used for MonetaryAmount.
 * Immutable.
 */
public class StashSettings implements Serializable {
    private static final long serialVersionUID = 9044979298460194104L;

    public static final String DEFAULT_MONEY_SYMBOL = "$";

    private final String moneySymbol;

    public StashSettings() {
        this.moneySymbol = StashSettings.DEFAULT_MONEY_SYMBOL;
    }

    public StashSettings(String moneySymbol) {
        this.moneySymbol = moneySymbol;
    }

    public String getMoneySymbol() {
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
        return this.moneySymbol.equals(ss.moneySymbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.moneySymbol);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Money Symbol : " + this.moneySymbol);
        return sb.toString();
    }
}
