package csdev.couponstash.testutil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import csdev.couponstash.commons.core.GuiSettings;
import csdev.couponstash.commons.core.StashSettings;
import csdev.couponstash.commons.moneysymbol.MoneySymbol;
import csdev.couponstash.model.ReadOnlyUserPrefs;
import csdev.couponstash.model.UserPrefs;

/**
 * A class that extends UserPrefs, allowing access to
 * a StashSettingsStub that contains a MoneySymbolStub.
 *
 * Irrelevant methods fail with an AssertionError.
 */
public class UserPrefsStub extends UserPrefs {
    private final StashSettingsStub stashSettings;

    public UserPrefsStub(MoneySymbolStub moneySymbol) {
        this.stashSettings = new StashSettingsStub(moneySymbol);
    }

    @Override
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        return new GuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
    }

    @Override
    public StashSettings getStashSettings() {
        return this.stashSettings;
    }

    @Override
    public void setStashSettings(StashSettings ss) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getCouponStashFilePath() {
        return Paths.get("");
    }

    @Override
    public void setCouponStashFilePath(Path couponStashFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefsStub)) { //this handles null as well.
            return false;
        }

        UserPrefsStub o = (UserPrefsStub) other;

        return stashSettings.equals(o.stashSettings);
    }

    @Override
    public int hashCode() {
        return stashSettings.hashCode();
    }

    /**
     * A class that contains a MoneySymbolStub, this symbol
     * is immutable and cannot be set.
     */
    private static class StashSettingsStub extends StashSettings {
        private final MoneySymbolStub moneySymbol;

        public StashSettingsStub(MoneySymbolStub moneySymbol) {
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
            if (!(o instanceof StashSettingsStub)) {
                return false;
            }

            StashSettingsStub ss = (StashSettingsStub) o;
            return this.moneySymbol.equals(ss.moneySymbol);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.moneySymbol);
        }

        @Override
        public String toString() {
            return "Money Symbol : " + this.moneySymbol.getString();
        }
    }
}
