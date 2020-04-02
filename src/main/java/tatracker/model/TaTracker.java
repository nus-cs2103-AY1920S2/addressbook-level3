package tatracker.model;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;

import tatracker.model.group.Group;
import tatracker.model.group.GroupNotFoundException;
import tatracker.model.group.UniqueGroupList;
import tatracker.model.module.Module;
import tatracker.model.module.ModuleNotFoundException;
import tatracker.model.module.UniqueModuleList;
import tatracker.model.session.Session;
import tatracker.model.session.UniqueDoneSessionList;
import tatracker.model.session.UniqueSessionList;
import tatracker.model.student.Student;
import tatracker.model.student.UniqueStudentList;


/**
 * Wraps all data at the ta-tracker level
 * Duplicates are not allowed (by .isSameSession comparison)
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class TaTracker implements ReadOnlyTaTracker {

    private static final long DEFAULT_HOURS = 0;
    private static final int DEFAULT_RATE = 40;

    private static Group currentlyShownGroup;
    private static Module currentlyShownModule;
    private static Module currentlyShownModuleClaim;

    private int rate;
    private static String currClaimFilter;

    private final UniqueSessionList sessions;
    private final UniqueDoneSessionList doneSessions;
    private final UniqueModuleList modules;
    private final UniqueGroupList currentlyShownGroups;
    private final UniqueStudentList currentlyShownStudents;


    public TaTracker() {
        sessions = new UniqueSessionList();
        doneSessions = new UniqueDoneSessionList();
        modules = new UniqueModuleList();
        currentlyShownGroups = new UniqueGroupList();
        currentlyShownStudents = new UniqueStudentList();
        currentlyShownGroup = null;
        currentlyShownModule = null;
        currentlyShownModuleClaim = null;

        currClaimFilter = "";

        rate = DEFAULT_RATE;
    }

    /**
     * Creates a TaTracker using the Students in the {@code toBeCopied}
     */
    public TaTracker(ReadOnlyTaTracker toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code TaTracker} with {@code newData}.
     */
    public void resetData(ReadOnlyTaTracker newData) {
        requireNonNull(newData);

        setSessions(newData.getSessionList());
        setDoneSessionList(newData.getDoneSessionList());
        setModules(newData.getModuleList());
        setCurrentlyShownGroups(newData.getCurrentlyShownGroupList());
        setCurrentlyShownStudents(newData.getCurrentlyShownStudentList());

        this.rate = newData.getRate();
    }

    // ======== Filter Methods ================================================

    /**
     *Sets the currently used filter under Claim View.
     */
    public void setCurrClaimFilter(String module) {
        requireNonNull(module);
        currClaimFilter = module;
    }

    /**
     * Get the currently used filter under Claim View.
     */
    public String getCurrClaimFilter() {
        return currClaimFilter;
    }

    // ======== Session Methods ================================================

    /**
     * Returns true if a session with the same identity as {@code session} exists in the ta-tracker.
     */
    public boolean hasSession(Session session) {
        requireNonNull(session);
        return sessions.contains(session);
    }

    /**
     * Adds a session to the ta-tracker.
     * The session must not already exist in the ta-tracker.
     */
    public void addSession(Session s) {
        sessions.add(s);
    }

    /**
     * Returns true if a session with the same identity as {@code session} exists in the ta-tracker.
     * Removes {@code session} from this {@code TaTracker}.
     * {@code session} must exist in the ta-tracker.
     */
    public void removeSession(Session session) {
        sessions.remove(session);
    }

    /**
     * Replaces the given session {@code target} in the list with {@code editedSession}.
     * {@code target} must exist in the ta-tracker.
     * The session identity of {@code editedSession} must not be the same as another
     * existing session in the ta-tracker.
     */
    public void setSession(Session target, Session editedSession) {
        requireNonNull(editedSession);

        sessions.setSession(target, editedSession);
    }

    /**
     * Replaces the contents of the session list with {@code sessions}.
     * {@code sessions} must not contain duplicate sessions.
     */
    public void setSessions(List<Session> sessions) {
        this.sessions.setSessions(sessions);
    }

    @Override
    public ObservableList<Session> getSessionList() {
        return sessions.asUnmodifiableObservableList();
    }

    // ======== Done Session Methods =================================================

    /**
     * Adds a completed session to the list of done sessions.
     */
    public void addDoneSession(Session s) {
        doneSessions.add(s);
    }

    @Override
    public long getTotalHours() {
        return doneSessions.asUnmodifiableObservableList()
                .stream()
                .map(Session::getDurationToNearestHour)
                .reduce(Duration.ZERO, Duration::plus)
                .toHours();
    }

    @Override
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public long getTotalEarnings() {
        return rate * getTotalHours();
    }

    /**
     * Replaces the contents of the donesession list with {@code donesessions}.
     * {@code donesessions} must not contain duplicate donesessions.
     */
    public void setDoneSessionList(List<Session> donesessions) {
        this.doneSessions.setSessions(donesessions);
    }

    @Override
    public ObservableList<Session> getDoneSessionList() {
        return doneSessions.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Student> getCompleteStudentList() {
        UniqueStudentList completeStudentList = new UniqueStudentList();

        List<Student> allStudents = new ArrayList<>();
        for (Module module : modules) {
            for (Group group : module.getGroupList()) {
                allStudents.addAll(group.getStudentList());
            }
        }
        completeStudentList.setStudents(allStudents);
        return completeStudentList.asUnmodifiableObservableList();
    }

    public void setCurrentlyShownModuleClaim(String moduleCode) {
        currentlyShownModuleClaim = modules.getModule(moduleCode);
    }

    public static Module getCurrentlyShownModuleClaim() {
        if (currentlyShownModuleClaim == null) {
            System.out.println("no filter");
        } else {
            System.out.println("reached");
            System.out.println(currentlyShownModuleClaim.getIdentifier());
            System.out.println(currentlyShownModuleClaim.getName());
        }
        return currentlyShownModuleClaim;
    }

    // ======== Module Methods =================================================

    /**
     * Returns module from TATracker.
     */
    public Module getModule(String moduleId) {
        return modules.getModule(moduleId);
    }

    /**
     * Returns module from TATracker.
     */
    public Module getModule(int index) {
        return modules.get(index);
    }

    /**
     * Returns true if a module with the same module code exists in the TATracker.
     */
    public boolean hasModule(String moduleCode) {
        requireNonNull(moduleCode);
        return modules.contains(new Module(moduleCode));
    }

    /**
     * Adds a module to the TATracker.
     */
    public void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Removes module with same module code from TA-Tracker.
     */
    public void deleteModule(Module module) {
        for (Session session : sessions) {
            if (session.getModuleCode().equals(module.getIdentifier())) {
                sessions.remove(session);
            }
        }
        modules.remove(module);
    }

    /**
     * Removes {@code key} from this {@code TaTracker}.
     * {@code key} must exist in the ta-tracker.
     */
    public void removeModule(Module key) {
        modules.remove(key);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the ta-tracker.
     * The module identity of {@code editedModule} must not be the same as another existing module in the tracker.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Sorts modules alphabetically.
     */
    public void sortModulesAlphabetically() {
        for (Module module : modules) {
            module.sortGroupsAlphabetically();
        }
    }

    /**
     * Sorts modules by rating in ascending order.
     */
    public void sortModulesByRatingAscending() {
        for (Module module : modules) {
            module.sortGroupsByRatingAscending();
        }
    }

    /**
     * Sorts modules alphabetically.
     */
    public void sortModulesByRatingDescending() {
        for (Module module : modules) {
            module.sortGroupsByRatingDescending();
        }
    }

    /**
     * Sorts modules by matric number.
     */
    public void sortModulesByMatricNumber() {
        for (Module module : modules) {
            module.sortGroupsByMatricNumber();
        }
    }

    /**
     * Replaces the contents of the modules list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    public static Module getCurrentlyShownModule() {
        return currentlyShownModule;
    }

    public void setCurrentlyShownModule(Module module) {
        currentlyShownModule = module;
    }

    // ======== Group Methods ==================================================

    /**
     * Returns true if a group with the same group code exists in the TATracker.
     */
    public boolean hasGroup(String groupCode, String moduleCode) {
        requireNonNull(groupCode, moduleCode);

        if (!hasModule(moduleCode)) {
            return false;
        }

        Module module = getModule(moduleCode);
        return module.hasGroup(new Group(groupCode));
    }

    /**
     * Adds a group to the TATracker.
     */
    public void addGroup(Group group, Module targetModule) {
        if (!hasModule(targetModule.getIdentifier())) {
            throw new ModuleNotFoundException();
        }
        Module module = getModule(targetModule.getIdentifier());
        module.addGroup(group);
    }

    /**
     * Removes {@code key} from this {@code TaTracker}.
     * {@code key} must exist in the ta-tracker.
     */
    public void removeGroup(Group group, Module targetModule) {
        if (!hasModule(targetModule.getIdentifier())) {
            throw new ModuleNotFoundException();
        }
        Module module = getModule(targetModule.getIdentifier());
        module.deleteGroup(group);
    }

    /**
     * Replaces the given group {@code target} in the list with {@code editedGroup}.
     * {@code target} must exist in the ta-tracker.
     * The group identity of {@code editedGroup} must not be the same as another existing group in the tracker.
     */
    public void setGroup(Group target, Group editedGroup, Module targetModule) {
        requireNonNull(editedGroup);

        if (!hasModule(targetModule.getIdentifier())) {
            throw new ModuleNotFoundException();
        }

        Module module = getModule(targetModule.getIdentifier());
        module.setGroup(target, editedGroup);
    }

    /**
     * Replaces the contents of the group list with {@code groups}.
     * {@code groups} must not contain duplicate groups.
     */
    public void setCurrentlyShownGroups(List<Group> groups) {
        this.currentlyShownGroups.setGroups(groups);
    }

    /**
     * Replaces the contents of the group list with the groups at the given
     * module index.
     */
    public void setCurrentlyShownGroups(int n) {
        setCurrentlyShownModule(modules.get(n));
        setCurrentlyShownGroups(((modules.get(n)).getGroupList()));
    }

    /**
     * Updates the currently shown groups to be that of the currently shown module
     * code.
     */
    public void updateCurrentlyShownGroups(String moduleCode) {
        setCurrentlyShownModule(modules.getModule(moduleCode));
        setCurrentlyShownGroups((modules.getModule(moduleCode)).getGroupList());
    }

    @Override
    public ObservableList<Group> getCurrentlyShownGroupList() {
        return currentlyShownGroups.asUnmodifiableObservableList();
    }

    public static Group getCurrentlyShownGroup() {
        return currentlyShownGroup;
    }

    public void setCurrentlyShownGroup(Group group) {
        currentlyShownGroup = group;
    }

    // ======== Student Methods ================================================

    /**
     * Returns true if a given student with the same identity as {@code student}
     * exists in a module group that is in TaTracker.
     * @param targetGroup group to check if {@code student} is enrolled in.
     * @param targetModule module that contains {@code group}.
     */
    public boolean hasStudent(Student student, String targetGroup, String targetModule) {
        requireNonNull(student);

        if (!hasGroup(targetGroup, targetModule)) {
            return false;
        }

        Module module = getModule(targetModule);
        Group group = module.getGroup(targetGroup);
        return group.hasStudent(student);
    }

    /**
     * Returns true if a student with the same identity as {@code student} exists in the ta-tracker.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return currentlyShownStudents.contains(student);
    }

    /**
     * Adds the given student into a module group that is in TaTracker.
     * @param student student to add, which must not already exist in the TaTracker module group.
     * @param targetGroup group to add {@code student} into, which must exist in the TaTracker module.
     * @param targetModule module to add {@code student} into, which must exist in the TaTracker.
     */
    public void addStudent(Student student, String targetGroup, String targetModule) {
        if (!hasModule(targetModule)) {
            throw new ModuleNotFoundException();
        }

        if (!hasGroup(targetGroup, targetModule)) {
            throw new GroupNotFoundException();
        }

        Module module = getModule(targetModule);
        Group group = module.getGroup(targetGroup);
        group.addStudent(student);
    }

    /**
     * Adds a student to the ta-tracker.
     * The student must not already exist in the ta-tracker.
     */
    public void addStudent(Student p) {
        currentlyShownStudents.add(p);
    }

    /**
     * Deletes the given student {@code target} from a module group that is in TaTracker.
     * @param target student to delete, which must exist in the TaTracker module group.
     * @param targetGroup group to delete student {@code target} from, which must exist in the TaTracker module.
     * @param targetModule module to delete student {@code target} from, which must exist in the TaTracker.
     */
    public void deleteStudent(Student target, String targetGroup, String targetModule) {
        if (!hasModule(targetModule)) {
            throw new ModuleNotFoundException();
        }

        if (!hasGroup(targetGroup, targetModule)) {
            throw new GroupNotFoundException();
        }

        Module module = getModule(targetModule);
        Group group = module.getGroup(targetGroup);
        group.deleteStudent(target);
    }

    /**
     * Removes {@code key} from this {@code TaTracker}.
     * {@code key} must exist in the ta-tracker.
     */
    public void removeStudent(Student key) {
        currentlyShownStudents.remove(key);
    }

    /**
     * Replaces the given student {@code target} in a TaTracker module group with {@code editedStudent}.
     * @param target student to edit, which must exist in the TaTracker module group.
     * @param editedStudent the edited student {@code target}.
     *                      The identity of {@code editedStudent} must be the same as {@code target}.
     * @param targetGroup group with the student to edit, which must exist in the TaTracker module.
     * @param targetModule module with the student to edit, which must exist in the TaTracker.
     */
    public void setStudent(Student target, Student editedStudent, Group targetGroup, Module targetModule) {
        if (!hasModule(targetModule.getIdentifier())) {
            throw new ModuleNotFoundException();
        }

        if (!hasGroup(targetGroup.getIdentifier(), targetModule.getIdentifier())) {
            throw new GroupNotFoundException();
        }

        Module module = getModule(targetModule.getIdentifier());
        Group group = module.getGroup(targetGroup.getIdentifier());
        group.setStudent(target, editedStudent);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the ta-tracker.
     * The student identity of {@code editedStudent} must not be the same as another existing student in the tracker.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        currentlyShownStudents.setStudent(target, editedStudent);
    }

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setCurrentlyShownStudents(List<Student> students) {
        this.currentlyShownStudents.setStudents(students);
    }

    /**
     * Replaces the contents of the student list with the students at the given
     * group index of the given module.
     */
    public void setCurrentlyShownStudents(String moduleCode, int n) {
        setCurrentlyShownModule(modules.getModule(moduleCode));
        setCurrentlyShownGroup(currentlyShownModule.get(n));
        setCurrentlyShownStudents(currentlyShownGroup.getStudentList());
    }

    /**
     * Replaces the contents of the student list with the students at the given
     * group index of the given module.
     */
    public void setCurrentlyShownStudents(int moduleIndex, int groupIndex) {
        setCurrentlyShownModule(modules.get(moduleIndex));
        setCurrentlyShownGroup(currentlyShownModule.get(groupIndex));
        setCurrentlyShownStudents(((modules.get(moduleIndex).get(groupIndex)).getStudentList()));
    }

    /**
     * Updates the currently shown groups to be that of the currently shown module
     * code.
     */
    public void updateCurrentlyShownStudents(String groupCode, String moduleCode) {
        setCurrentlyShownModule(modules.getModule(moduleCode));
        setCurrentlyShownGroup(currentlyShownModule.getGroup(groupCode));
        setCurrentlyShownStudents(((modules.getModule(moduleCode)).getGroup(groupCode)).getStudentList());
    }

    @Override
    public ObservableList<Student> getCurrentlyShownStudentList() {
        return currentlyShownStudents.asUnmodifiableObservableList();
    }

    // ======== Utility Methods ================================================

    @Override
    public int hashCode() {
        return modules.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaTracker // instanceof handles nulls
                && modules.equals(((TaTracker) other).modules));
    }

    @Override
    public String toString() {
        return modules.asUnmodifiableObservableList().size() + " students";
        // TODO: refine later
    }
}
