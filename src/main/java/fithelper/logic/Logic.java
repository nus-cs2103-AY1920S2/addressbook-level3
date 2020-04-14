package fithelper.logic;

import java.time.LocalDate;

import fithelper.commons.exceptions.IllegalValueException;
import fithelper.logic.commands.CommandResult;
import fithelper.logic.exceptions.CommandException;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.model.ReadOnlyUserProfile;
import fithelper.model.ReadOnlyWeightRecords;
import fithelper.model.diary.Diary;
import fithelper.model.entry.Entry;
import fithelper.model.weight.Weight;
import javafx.collections.ObservableList;

import jfxtras.icalendarfx.components.VEvent;

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
    CommandResult execute(String commandText) throws CommandException, IllegalValueException;

    /**
     * Returns the FitHelper.
     *
     * @see fithelper.model.Model#getFitHelper()
     */
    ReadOnlyFitHelper getFitHelper();

    /**
     * Returns the User Profile.
     *
     * @see fithelper.model.Model#getUserProfile()
     */
    ReadOnlyUserProfile getUserProfile();

    /**
     * Returns the Weight Records.
     *
     * @see fithelper.model.Model#getWeightRecords() ()
     */
    ReadOnlyWeightRecords getWeightRecords();

    /** Returns an unmodifiable view of the filtered list of weight*/
    ObservableList<Weight> getFilteredWeightList();

    ObservableList<VEvent> getVEvents();

    /** Returns an unmodifiable view of the filtered list of food entries*/
    ObservableList<Diary> getFilteredDiaryList();

    /** Returns an unmodifiable view of the filtered list of food entries*/
    ObservableList<Entry> getFilteredFoodEntryList();

    /** Returns an unmodifiable view of the filtered list of sports entries*/
    ObservableList<Entry> getFilteredSportsEntryList();

    /** Returns an unmodifiable view of the filtered list of sports entries*/
    ObservableList<Entry> getFilteredReminderEntryList();

    /** Returns an unmodifiable view of the filtered list of food entries*/
    ObservableList<Entry> getFilteredTodayFoodEntryList();

    /** Returns an unmodifiable view of the filtered list of sports entries*/
    ObservableList<Entry> getFilteredTodaySportsEntryList();

    LocalDate getCalendarDate();

    String getCalendarMode();

    LocalDate getCalendarShow();

    void setCalendarShow();
}
