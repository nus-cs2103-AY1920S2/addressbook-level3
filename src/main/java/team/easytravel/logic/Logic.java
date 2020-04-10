package team.easytravel.logic;

import java.util.List;

import javafx.collections.ObservableList;
import team.easytravel.commons.core.GuiSettings;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.logic.parser.exceptions.ParseException;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpense;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.listmanagers.transportbooking.TransportBooking;
import team.easytravel.model.trip.DayScheduleEntry;
import team.easytravel.model.trip.Trip;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns an unmodifiable view of the filtered list of activities.
     *
     * @return the unmodifiable view of the filtered list of activities.
     */
    ObservableList<Activity> getFilteredActivityList();

    /**
     * Returns an unmodifiable view of the filtered list of transport bookings.
     *
     * @return the unmodifiable view of the filtered list of transport bookings.
     */
    ObservableList<TransportBooking> getFilteredTransportBookingList();

    /**
     * Returns an unmodifiable view of the filtered list of accommodation bookings.
     *
     * @return the unmodifiable view of the filtered list of accommodation bookings.
     */
    ObservableList<AccommodationBooking> getFilteredAccommodationBookingList();

    /**
     * Returns an unmodifiable view of the filtered list of Help Questions.
     *
     * @return the unmodifiable view of the filtered list of Help Questions.
     */
    ObservableList<String> getHelpQuestions();

    /**
     * Returns an unmodifiable view of the filtered list of fixed expenses.
     *
     * @return the unmodifiable view of the filtered list of fixed expenses.
     */
    ObservableList<FixedExpense> getFilteredFixedExpenseList();

    /**
     * Returns an unmodifiable view of the filtered packing list.
     *
     * @return the unmodifiable view of the filtered packing list.
     */
    ObservableList<PackingListItem> getFilteredPackingList();

    /**
     * Returns an unmodifiable view of the schedule.
     *
     * @return the unmodifiable view of the schedule.
     */
    List<ObservableList<DayScheduleEntry>> getScheduleList();

    /**
     * Returns true if a trip is set. Else returns false.
     *
     * @return true if a trip is set.
     */
    boolean hasTrip();

    /**
     * Returns a trip
     *
     * @return trip
     */
    Trip getTrip();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
