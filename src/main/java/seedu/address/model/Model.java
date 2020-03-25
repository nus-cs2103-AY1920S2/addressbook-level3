package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.module.personal.Deadline;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Profile> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getProfileListFilePath();
    //Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setProfileListFilePath(Path profileListFilePath);
    //void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setProfileList(ProfileList profileList);
    //void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ProfileList getProfileList();
    //ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a profile with the same identity as {@code profile} exists in the address book.
     */
    boolean hasPerson(Profile profile);

    /**
     * Deletes the given profile.
     * The profile must exist in the address book.
     */
    void deletePerson(Profile target);

    /**
     * Adds the given profile.
     * {@code profile} must not already exist in the address book.
     */
    void addPerson(Profile profile);

    /**
     * Replaces the given profile {@code target} with {@code editedProfile}.
     * {@code target} must exist in the address book.
     * The profile identity of {@code editedProfile} must not be the same as another existing profile
     * in the address book.
     */
    void setPerson(Profile target, Profile editedProfile);

    /** Returns an unmodifiable view of the filtered profile list */
    ObservableList<Profile> getFilteredPersonList();

    ObservableList<Deadline> getFilteredDeadlineList();

    /**
     * Updates the filter of the filtered profile list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Profile> predicate);

    boolean hasProfile(Name name);

    Profile getProfile(Name name);

    Profile getFirstProfile();

    void addDeadline(Deadline deadline);

    void deleteDeadline(Deadline deadline);

    void clearDeadlineList();

    void loadDeadlines();

}
