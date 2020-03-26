package csdev.couponstash.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import csdev.couponstash.commons.core.StashSettings;
import csdev.couponstash.commons.moneysymbol.MoneySymbol;

/**
 * Jackson-friendly version of {@link StashSettings}.
 * Unlike StashSettings and MoneySymbol, this class
 * is immutable.
 */
public class JsonAdaptedStashSettings {
    private final String moneySymbol;

    /**
     * Constructs a JsonAdaptedStashSettings using the
     * properties from the JSON file.
     * @param ms String representing money symbol.
     */
    @JsonCreator
    public JsonAdaptedStashSettings(@JsonProperty("moneySymbol") String ms) {

        this.moneySymbol = ms;
    }

    /**
     * Constructs a JsonAdaptedStashSettings using the
     * application's StashSettings. When this constructor
     * is called, the current String set in MoneySymbol
     * will be used to create the JsonAdaptedStashSettings.
     *
     * @param ss The application's StashSettings.
     */
    public JsonAdaptedStashSettings(StashSettings ss) {
        this.moneySymbol = ss.getMoneySymbol().getString();
    }

    /**
     * Converts this JsonAdaptedStashSettings to the
     * normal StashSettings used in Coupon Stash.
     * @return StashSettings object used in the
     *         Coupon Stash application.
     */
    public StashSettings convertToNormalType() {
        // ensure that money symbol is set to the String
        MoneySymbol ms = MoneySymbol.getMoneySymbol(moneySymbol);
        ms.setString(moneySymbol);
        return new StashSettings(ms);
    }
}
