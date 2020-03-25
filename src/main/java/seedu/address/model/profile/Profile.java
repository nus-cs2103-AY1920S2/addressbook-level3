package seedu.address.model.profile;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SEMESTER;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModuleList;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.personal.Deadline;

/**
 * Represents a Profile in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Profile {

    // Identity fields
    private static HashMap<Integer, ModuleList> moduleHash;
    private static int currentSemester = 0;
    private static String specialisation;
    private static Name name;
    private static CourseName courseName;
    private List<Deadline> deadlines = new ArrayList<Deadline>();


    /**
     * Every field must be present and not null.
     */
    public Profile(Name name, CourseName courseName, int currentSemester, String specialisation) {
        requireAllNonNull(name);
        requireAllNonNull(courseName);
        requireAllNonNull(currentSemester);
        Profile.name = name;
        Profile.courseName = courseName;
        Profile.currentSemester = currentSemester;
        Profile.specialisation = specialisation;
        Profile.moduleHash = new HashMap<>();
    }

    /**
     * Adds a module to the hashmap with the key being the semester
     */
    public static void addModule(Integer semester, Module module) {
        if (!moduleHash.isEmpty() && moduleHash.containsKey(semester)) {
            moduleHash.get(semester).addModule(module);
        } else {
            ModuleList moduleList = new ModuleList();
            moduleList.addModule(module);
            moduleHash.put(semester, moduleList);
        }
    }

    public Name getName() {
        return name;
    }

    public static Name getStaticName() {
        return name;
    }

    public CourseName getCourseName() {
        return courseName;
    }

    public static int getCurrentSemester() {
        return currentSemester;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public static void setName(Name name) {
        Profile.name = name;
    }

    public static void setCourse(CourseName courseName) {
        Profile.courseName = courseName;
    }

    public static void setCurrentSemester(int currentSemester) {
        Profile.currentSemester = currentSemester;
    }

    public static void setSpecialisation(String specialisation) {
        Profile.specialisation = specialisation;
    }

    public static ModuleList getModules(Integer semester) throws ParseException {
        if (!moduleHash.containsKey(semester)) {
            throw new ParseException(MESSAGE_INVALID_SEMESTER);
        }
        return moduleHash.get(semester);
    }

    public static HashMap<Integer, ModuleList> getAllModules() {
        return moduleHash;
    }

    public Set<Map.Entry<Integer, ModuleList>> getMappings() {
        return moduleHash.entrySet();
    }

    public static HashMap<Integer, ModuleList> getHashMap() {
        return moduleHash;
    }

    public List<Deadline> getDeadlines() {

        ModuleList modules = moduleHash.get(currentSemester); // Deadlines should only be from the current semester
        List<Deadline> deadlineList = new ArrayList<>();
        for (Module module: modules) {
            deadlineList.addAll(module.getDeadlines());
        }
        return deadlines;
    }

    public int getModuleSemester(ModuleCode moduleCode) {
        for (int semester: moduleHash.keySet()) {
            for (Module module: moduleHash.get(semester)) {
                if (module.getModuleCode().equals(moduleCode)) {
                    return semester;
                }
            }
        }
        throw new NoSuchElementException(name.toString() + " is not taking " + moduleCode.toString());
    }

    public Module getModule(ModuleCode moduleCode) throws ParseException {
        for (ModuleList moduleList: moduleHash.values()) {
            for (Module module: moduleList) {
                if (module.getModuleCode().equals(moduleCode)) {
                    return module;
                }
            }
        }
        throw new ParseException(name.toString() + " is not taking " + moduleCode.toString());
    }

    /**
     * Returns true if a module with module code {@code moduleCode} exists in {@code moduleHash}.
     */
    public boolean hasModule(ModuleCode moduleCode) {
        for (ModuleList moduleList: moduleHash.values()) {
            if (moduleList.hasModuleWithModuleCode(moduleCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a module with module code {@code moduleCode}.
     */
    public void deleteModule(ModuleCode moduleCode) throws ParseException {
        if (hasModule(moduleCode)) {
            int semester = getModuleSemester(moduleCode);
            moduleHash.get(semester).removeModuleWithModuleCode(moduleCode);
            return;
        }
        throw new ParseException(name.toString() + " is not taking " + moduleCode.toString());
    }


    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameProfile(Profile otherProfile) {
        if (otherProfile == this) {
            return true;
        }

        return otherProfile != null
                && otherProfile.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Profile)) {
            return false;
        }

        Profile otherProfile = (Profile) other;
        return otherProfile.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }

}
