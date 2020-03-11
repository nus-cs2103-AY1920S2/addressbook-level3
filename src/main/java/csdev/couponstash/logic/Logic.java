package csdev.couponstash.logic;

import java.nio.file.Path;

import csdev.couponstash.commons.core.GuiSettings;
import csdev.couponstash.logic.commands.CommandResult;
import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.logic.parser.exceptions.ParseException;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.ReadOnlyCouponStash;
import csdev.couponstash.model.coupon.Coupon;

import javafx.collections.ObservableList;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the CouponStash.
     *
     * @see Model#getCouponStash()
     */
    ReadOnlyCouponStash getCouponStash();

    /** Returns an unmodifiable view of the filtered list of coupons */
    ObservableList<Coupon> getFilteredCouponList();

    /**
     * Returns the user prefs' CouponStash file path.
     */
    Path getCouponStashFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
