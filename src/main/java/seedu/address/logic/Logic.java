package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.calender.Task;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.notes.Notes;
import seedu.address.model.nusmodule.NusModule;
import seedu.address.model.person.Person;
import seedu.address.model.studentprofile.Profile;


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
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' diary book file path.
     */
    Path getDiaryBookFilePath();

    /** Returns an unmodifiable view of list of diaries */
    ObservableList<DiaryEntry> getDiaryList();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    //=========== Notes Module ==================================================================================
    /**
     * Returns an list of String that contains what is currently in the folder
     * */
    ObservableList<Notes> getFilesInFolderList();


    //=========== Calender Module ==================================================================================

    /**
     *
     * Returns a list of deadline Tasks
     */
    ObservableList<Task> getDeadlineTaskList();


    //=========== Profile Module ==================================================================================
    ObservableList<NusModule> getModulesListTaken();

    ObservableValue<String> getMajor();

    Profile getProfile();

    Path getModuleBookFilePath();

}
