package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;

/**
 * Represents the in-memory model of the profile list data.
 */
public class ProfileManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ProfileManager.class);

    private final ProfileList profileList;
    private final UserPrefs userPrefs;
    private final FilteredList<Profile> filteredProfiles;

    public ProfileManager(ProfileList profileList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(profileList, userPrefs);

        logger.fine("Initializing with address book: " + profileList + " and user prefs " + userPrefs);

        this.profileList = profileList;
        this.userPrefs = new UserPrefs(userPrefs);
        filteredProfiles = new FilteredList<>(profileList.getProfileList());
    }

    public ProfileManager() {
        this(new ProfileList(), new UserPrefs());
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getProfileListFilePath() {
        return userPrefs.getProfileListFilePath();
    }

    @Override
    public void setProfileListFilePath(Path profileListFilePath) {
        requireNonNull(profileListFilePath);
        userPrefs.setProfileListFilePath(profileListFilePath);
    }

    @Override
    public void setProfileList(ProfileList profileList) {
        this.profileList.resetData(profileList);
    }

    @Override
    public ProfileList getProfileList() {
        return profileList;
    }

    @Override
    public boolean hasPerson(Profile profile) {
        return profileList.hasProfileWithName(profile.getName());
    }

    @Override
    public void deletePerson(Profile target) {
        profileList.deleteProfile(target);
    }

    @Override
    public void addPerson(Profile profile) {
        profileList.addProfile(profile);
    }

    @Override
    public void setPerson(Profile target, Profile editedProfile) {
        requireAllNonNull(target, editedProfile);

        profileList.setProfile(target, editedProfile);
    }

    @Override
    public ObservableList<Profile> getFilteredPersonList() {
        return filteredProfiles;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Profile> predicate) {
        requireNonNull(predicate);
        filteredProfiles.setPredicate(predicate);
    }

    @Override
    public boolean hasProfile(Name name) {
        return profileList.hasProfileWithName(name);
    }

    @Override
    public Profile getProfile(Name name) {
        return profileList.getProfileWithName(name);
    }

    /**
     * To be used in the case of only one profile. Does not take into account the name of the user.
     * Consider temporarily storing the name of the current user in memory (when dealing with multiple profiles.
     */
    public Profile getFirstProfile() {
        return profileList.getProfileList().get(0);
    }
}
