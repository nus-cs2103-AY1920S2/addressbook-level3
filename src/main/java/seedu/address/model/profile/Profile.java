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
    private HashMap<Integer, ModuleList> semModHashMap;
    private int currentSemester = 0;
    private String specialisation;
    private Name name;
    private CourseName courseName;
    private Cap cap;

    /**
     * Every field must be present and not null.
     */
    public Profile(Name name, CourseName courseName, int currentSemester, String specialisation) {
        requireAllNonNull(name);
        requireAllNonNull(courseName);
        requireAllNonNull(currentSemester);
        this.name = name;
        this.courseName = courseName;
        this.currentSemester = currentSemester;
        this.specialisation = specialisation;
        this.semModHashMap = new HashMap<>();
        this.cap = new Cap();
    }


    /**
     * Adds a module to the hashmap with the key being the semester
     */
    public void addModule(Integer semester, Module module) {
        if (!semModHashMap.isEmpty() && semModHashMap.containsKey(semester)) {
            semModHashMap.get(semester).addModule(module);
        } else {
            ModuleList moduleList = new ModuleList();
            moduleList.addModule(module);
            semModHashMap.put(semester, moduleList);
        }

        int id = 0;
        if (semModHashMap.containsKey(currentSemester)) {
            for (Module mod : semModHashMap.get(currentSemester)) { //for colourising module names
                mod.setTag(id++);
            }
        }
    }

    public Name getName() {
        return name;
    }

    public CourseName getCourseName() {
        return courseName;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setCourse(CourseName courseName) {
        this.courseName = courseName;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public ModuleList getModules(Integer semester) throws ParseException {
        if (!semModHashMap.containsKey(semester)) {
            throw new ParseException(MESSAGE_INVALID_SEMESTER);
        }
        return semModHashMap.get(semester);
    }

    public List<ModuleCode> getAllModuleCodesBefore(int semester) {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        for (int sem: semModHashMap.keySet()) {
            if (sem < semester) {
                moduleCodes.addAll(semModHashMap.get(sem).getModuleCodes());
            }
            //moduleCodes.addAll(moduleList.getModuleCodes());
        }
        return moduleCodes;
    }

    public HashMap<Integer, ModuleList> getAllModules() {
        return semModHashMap;
    }

    public Set<Map.Entry<Integer, ModuleList>> getMappings() {
        return semModHashMap.entrySet();
    }

    public HashMap<Integer, ModuleList> getSemModHashMap() {
        return semModHashMap;
    }

    public List<Deadline> getDeadlines() {
        ModuleList modules = semModHashMap.get(currentSemester); // Deadlines should only be from the current semester
        List<Deadline> deadlineList = new ArrayList<>();
        for (Module module: modules) {
            deadlineList.addAll(module.getDeadlines());
        }
        return deadlineList;
    }

    public void updateCap() {
        cap.updateCap(semModHashMap);
    }

    public Cap getCap() {
        return cap;
    }

    public ModuleList getCurModules() {
        return semModHashMap.get(currentSemester);
    }

    public int getModuleSemester(ModuleCode moduleCode) {
        for (int semester: semModHashMap.keySet()) {
            for (Module module: semModHashMap.get(semester)) {
                if (module.getModuleCode().equals(moduleCode)) {
                    return semester;
                }
            }
        }
        throw new NoSuchElementException(name.toString() + " is not taking " + moduleCode.toString());
    }

    public Module getModule(ModuleCode moduleCode) throws ParseException {
        for (ModuleList moduleList: semModHashMap.values()) {
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
        for (ModuleList moduleList: semModHashMap.values()) {
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
            semModHashMap.get(semester).removeModuleWithModuleCode(moduleCode);
            return;
        }
        throw new ParseException(name.toString() + " is not taking " + moduleCode.toString());
    }

    /**
     * Returns key of the given value
     */
    public <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
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
