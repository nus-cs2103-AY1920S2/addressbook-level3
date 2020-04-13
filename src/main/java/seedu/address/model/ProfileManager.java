package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.CourseFocusArea;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.personal.Deadline;

//@@author chanckben
/**
 * Represents the in-memory model of the profile list data.
 */
public class ProfileManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ProfileManager.class);

    private final ProfileList profileList;
    private ObservableList<Deadline> deadlineList;
    private final UserPrefs userPrefs;
    private final FilteredList<Profile> filteredProfiles;
    private SortedList<Deadline> sortedDeadlines;

    private Optional<Object> displayedView = Optional.empty();

    public ProfileManager(ProfileList profileList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(profileList, userPrefs);

        logger.fine("Initializing with MODdy " + profileList + " and user prefs " + userPrefs);

        this.profileList = profileList;
        this.userPrefs = new UserPrefs(userPrefs);
        filteredProfiles = new FilteredList<>(profileList.getProfileList());
        deadlineList = FXCollections.observableArrayList();
        sortedDeadlines = deadlineList.sorted(new DateTimeComparator());
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
    public boolean hasProfile(Name name) {
        requireNonNull(name);
        return profileList.hasProfileWithName(name);
    }

    @Override
    public Profile getProfile(Name name) {
        return profileList.getProfileWithName(name);
    }

    @Override
    public void deleteProfile(Profile target) {
        profileList.deleteProfile(target);
    }

    @Override
    public void addProfile(Profile profile) {
        profileList.addProfile(profile);
    }

    @Override
    public void setProfile(Profile target, Profile editedProfile) {
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

    public boolean hasOneProfile() {
        return profileList.getProfileList().size() == 1;
    }

    /**
     * To be used in the case of only one profile. Does not take into account the name of the user.
     * Consider temporarily storing the name of the current user in memory (when dealing with multiple profiles.
     */
    public Profile getFirstProfile() {
        return profileList.getProfileList().get(0);
    }

    public ObservableList<Deadline> getSortedDeadlineList() {
        return sortedDeadlines;
    }

    @Override
    public void addDeadline(Deadline deadline) {
        requireNonNull(deadline);
        this.deadlineList.add(deadline);
    }

    @Override
    public void deleteDeadline(Deadline deadline) {
        requireNonNull(deadline);
        Iterator<Deadline> iter = this.deadlineList.iterator();
        Boolean flag = false;
        while (iter.hasNext()) {
            Deadline dl = iter.next();
            if (dl.getModuleCode().equals(deadline.getModuleCode())
                    && dl.getDescription().toUpperCase().equals(deadline.getDescription().toUpperCase())) {
                iter.remove();
                flag = true;
            }
        }

        /*if (!flag) {
            throw new DeadlineNotFoundException();
        }*/
    }

    /**
     * To be used when deadline is edited.
     */
    public void replaceDeadline(Deadline oldDeadline, Deadline newDeadline) {
        requireAllNonNull(oldDeadline, newDeadline);
        Iterator<Deadline> iter = this.deadlineList.iterator();
        Boolean flag = false;
        while (iter.hasNext()) {
            Deadline dl = iter.next();
            if (dl.getModuleCode().equals(oldDeadline.getModuleCode())
                    && dl.getDescription().equals(oldDeadline.getDescription())) {
                iter.remove();
                flag = true;
            }
        }
        if (flag) {
            this.deadlineList.add(newDeadline);
        }
    }

    @Override
    public void clearDeadlineList() {
        this.deadlineList.clear();
    }

    @Override
    public void loadDeadlines() {
        this.deadlineList.addAll(this.profileList.getProfileList().get(0).getDeadlines());
    }

    @Override
    public void deleteModuleDeadlines(ModuleCode mc) {
        Iterator<Deadline> iter = this.deadlineList.iterator();
        while (iter.hasNext()) {
            Deadline dl = iter.next();
            if (dl.getModuleCode().toString().equals(mc.toString())) {
                iter.remove();
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ProfileManager)) {
            return false;
        }

        // state check
        ProfileManager other = (ProfileManager) obj;
        return profileList.equals(other.profileList)
                && userPrefs.equals(other.userPrefs);
        //&& filteredPersons.equals(other.filteredPersons);
    }

    //MODULE LIST VIEW
    @Override
    public Optional<Object> getDisplayedView() {
        return this.displayedView;
    }

    @Override
    public void setDisplayedView(ObservableList<Module> toDisplay) {
        this.displayedView = Optional.ofNullable(toDisplay);
    }

    @Override
    public void setDisplayedView(Profile toDisplay) {
        this.displayedView = Optional.ofNullable(toDisplay);
    }

    @Override
    public void setDisplayedView(Module toDisplay) {
        this.displayedView = Optional.ofNullable(toDisplay);
    }

    @Override
    public void setDisplayedView(Course toDisplay) {
        this.displayedView = Optional.ofNullable(toDisplay);
    }

    @Override
    public void setDisplayedView(CourseFocusArea toDisplay) {
        this.displayedView = Optional.ofNullable(toDisplay);
    }

    @Override
    public void setNewDeadlineList(Profile editedProfile) {
        if (editedProfile.getDeadlines() != null) {
            this.deadlineList.addAll(editedProfile.getDeadlines());
        }
    }

    @Override
    public void deleteModuleFromDeadlineList(ModuleCode moduleCode) {
        for (Deadline deadline : this.deadlineList) {
            if (deadline.getModuleCode().equals(moduleCode)) {
                this.deadlineList.remove(deadline);
            }
        }
    }

}

/**
 * Comparator to compare date and time in Deadline object.
 */
class DateTimeComparator implements Comparator<Deadline> {

    @Override
    public int compare(Deadline d1, Deadline d2) {
        if (d1.getDate() != null && d2.getDate() != null) {
            if (d1.getDate().equals(d2.getDate())) {
                return d1.getTime().compareTo(d2.getTime());
            } else {
                return d1.getDate().compareTo(d2.getDate());
            }
        } else if (d1.getDate() == null && d2.getDate() != null) {
            return 1;
        } else if (d1.getDate() != null && d2.getDate() == null) {
            return -1;
        } else {
            return d1.getModuleCode().compareTo(d2.getModuleCode());
        }
    }
}
