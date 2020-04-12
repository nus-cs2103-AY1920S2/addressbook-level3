package seedu.address.model;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.BaseManager;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelAssignment.AssignmentAddressBook;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelCourse.CourseAddressBook;
import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelFinance.FinanceAddressBook;
import seedu.address.model.modelGeneric.AddressBookGeneric;
import seedu.address.model.modelGeneric.ModelObject;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;
import seedu.address.model.modelObjectTags.CompositeID;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelObjectTags.Person;
import seedu.address.model.modelProgress.Progress;
import seedu.address.model.modelProgress.ProgressAddressBook;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStaff.StaffAddressBook;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.modelStudent.StudentAddressBook;
import seedu.address.ui.MainWindow;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends BaseManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final StaffAddressBook staffAddressBook;
    private final StudentAddressBook studentAddressBook;
    private final FinanceAddressBook financeAddressBook;
    private final CourseAddressBook courseAddressBook;
    private final AssignmentAddressBook assignmentAddressBook;
    private final ProgressAddressBook progressAddressBook;

    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Staff> filteredStaffs;
    private final FilteredList<Student> filteredStudents;
    private final FilteredList<Finance> filteredFinances;
    private final FilteredList<Course> filteredCourses;
    private final FilteredList<Assignment> filteredAssignments;
    private final FilteredList<Progress> filteredProgresses;

    private Predicate<Student> dataStudentPredicate = PREDICATE_SHOW_ALL_STUDENTS;
    private Predicate<Staff> dataStaffPredicate = PREDICATE_SHOW_ALL_STAFFS;
    private Predicate<Finance> dataFinancePredicate = PREDICATE_SHOW_ALL_FINANCES;
    private Predicate<Course> dataCoursePredicate = PREDICATE_SHOW_ALL_COURSES;
    private Predicate<Assignment> dataAssignmentPredicate = PREDICATE_SHOW_ALL_ASSIGNMENTS;

    private Predicate<Student> extraStudentPredicate = PREDICATE_HIDE_ALL_STUDENTS;
    private Predicate<Staff> extraStaffPredicate = PREDICATE_HIDE_ALL_STAFFS;
    private Predicate<Finance> extraFinancePredicate = PREDICATE_HIDE_ALL_FINANCES;
    private Predicate<Course> extraStudentCoursePredicate = PREDICATE_HIDE_ALL_COURSES;
    private Predicate<Course> extraStaffCoursePredicate = PREDICATE_HIDE_ALL_COURSES;
    private Predicate<Assignment> extraAssignmentPredicate = PREDICATE_HIDE_ALL_ASSIGNMENTS;
    private MainWindow mainWindow;
    public static Model instance;

    public static Model getInstance() {
        return instance;
    }

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyAddressBookGeneric<Staff> staffAddressBook,
                        ReadOnlyAddressBookGeneric<Student> studentAddressBook, ReadOnlyAddressBookGeneric<Finance> financeAddressBook,
                        ReadOnlyAddressBookGeneric<Course> courseAddressBook, ReadOnlyAddressBookGeneric<Assignment> assignmentAddressBook,
                        ReadOnlyAddressBookGeneric<Progress> progressAssignmentBook, ReadOnlyUserPrefs userPrefs) {
        super();

        requireAllNonNull(staffAddressBook, studentAddressBook, financeAddressBook, courseAddressBook,
                assignmentAddressBook, progressAssignmentBook, userPrefs);

        logger.fine("Initializing with address book: " + studentAddressBook
                + "Initializing with staff address book: " + staffAddressBook
                + "Initializing with address address book: " + assignmentAddressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.staffAddressBook = new StaffAddressBook(staffAddressBook);
        this.studentAddressBook = new StudentAddressBook(studentAddressBook);
        this.financeAddressBook = new FinanceAddressBook(financeAddressBook);
        this.courseAddressBook = new CourseAddressBook(courseAddressBook);
        this.assignmentAddressBook = new AssignmentAddressBook(assignmentAddressBook);
        this.progressAddressBook = new ProgressAddressBook(progressAssignmentBook);

        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredStaffs = new FilteredList<>(this.staffAddressBook.getList());
        filteredStudents = new FilteredList<>(this.studentAddressBook.getList());
        filteredFinances = new FilteredList<>(this.financeAddressBook.getList());
        filteredCourses = new FilteredList<>(this.courseAddressBook.getList());
        filteredAssignments = new FilteredList<>(this.assignmentAddressBook.getList());
        filteredProgresses = new FilteredList<>(this.progressAddressBook.getList());
        instance = this;
    }

    /**
     * Creates a new ModelManager.
     */
    public ModelManager() {
        this(new AddressBook(), new StaffAddressBook(), new StudentAddressBook(),
                new FinanceAddressBook(), new CourseAddressBook(),
                new AssignmentAddressBook(), new ProgressAddressBook(),
                new UserPrefs());
    }

    /**
     * Get main Window.
     */
    public MainWindow getMainWindow() {
        return this.mainWindow;
    }

    /**
     * Set this main window to a main window.
     */
    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }
    //=========== UserPrefs ==================================================================================

    /**
     * Get an unmodifiable view of User Prefs.
     */
    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    /**
     * Set User Prefs.
     */
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    /**
     * Get GUI Settings.
     */
    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    /**
     * Set GUI Settings.
     */
    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    /**
     * Get Address Book File Path.
     */
    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    /**
     * Set Address Book File Path.
     */
    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    /**
     * Get Staff Address Book File Path.
     */
    @Override
    public Path getStaffAddressBookFilePath() {
        return userPrefs.getStaffAddressBookFilePath();
    }

    /**
     * Set Staff Address Book File Path.
     */
    @Override
    public void setStaffAddressBookFilePath(Path staffAddressBookFilePath) {
        requireNonNull(staffAddressBookFilePath);
        userPrefs.setStaffAddressBookFilePath(staffAddressBookFilePath);
    }

    /**
     * Get Student Address Book File Path.
     */
    @Override
    public Path getStudentAddressBookFilePath() {
        return userPrefs.getStudentAddressBookFilePath();
    }

    /**
     * Set Student Address Book File Path.
     */
    @Override
    public void setStudentAddressBookFilePath(Path studentAddressBookFilePath) {
        requireNonNull(studentAddressBookFilePath);
        userPrefs.setStudentAddressBookFilePath(studentAddressBookFilePath);
    }

    /**
     * Get Finance Address Book File Path.
     */
    @Override
    public Path getFinanceAddressBookFilePath() {
        return userPrefs.getFinanceAddressBookFilePath();
    }

    /**
     * Set Finance Address Book File Path.
     */
    @Override
    public void setFinanceAddressBookFilePath(Path financeAddressBookFilePath) {
        requireNonNull(financeAddressBookFilePath);
        userPrefs.setFinanceAddressBookFilePath(financeAddressBookFilePath);
    }

    /**
     * Get Course Address Book File Path.
     */
    @Override
    public Path getCourseAddressBookFilePath() {
        return userPrefs.getCourseAddressBookFilePath();
    }

    /**
     * Set Course Address Book File Path.
     */
    @Override
    public void setCourseAddressBookFilePath(Path courseAddressBookFilePath) {
        requireNonNull(courseAddressBookFilePath);
        userPrefs.setCourseAddressBookFilePath(courseAddressBookFilePath);
    }

    /**
     * Get Assignment Address Book File Path.
     */
    @Override
    public Path getAssignmentAddressBookFilePath() {
        return userPrefs.getAssignmentAddressBookFilePath();
    }

    /**
     * Set Assignment Address Book File Path.
     */
    @Override
    public void setAssignmentAddressBookFilePath(Path assignmentAddressBookFilePath) {
        requireNonNull(assignmentAddressBookFilePath);
        userPrefs.setAssignmentAddressBookFilePath(assignmentAddressBookFilePath);
    }

    /**
     * Returns the user prefs' assignment address book file path.
     */
    @Override
    public Path getProgressAddressBookFilePath() {
        return userPrefs.getProgressAddressBookFilePath();
    }

    /**
     * Sets the user prefs' address book file path.
     *
     * @param progressAddressBookFilePath the progress AddressBook file path.
     */
    @Override
    public void setProgressAddressBookFilePath(Path progressAddressBookFilePath) {
        requireNonNull(progressAddressBookFilePath);
        userPrefs.setProgressAddressBookFilePath(progressAddressBookFilePath);
    }

    /**
     * Returns un unmodifiable view of address book.
     */
    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    // ================================== FACTORY HELPERS  ==================================

    /**
     * Returns Entity type of given ModelObject.
     *
     * @param obj The Model Object to get Entity Type.
     */
    @Override
    public Constants.ENTITY_TYPE modelObjectToEntityType(ModelObject obj) throws CommandException {
        if (obj instanceof Staff) {
            return Constants.ENTITY_TYPE.STAFF;
        } else if (obj instanceof Student) {
            return Constants.ENTITY_TYPE.STUDENT;
        } else if (obj instanceof Finance) {
            return Constants.ENTITY_TYPE.FINANCE;
        } else if (obj instanceof Course) {
            return Constants.ENTITY_TYPE.COURSE;
        } else if (obj instanceof Assignment) {
            return Constants.ENTITY_TYPE.ASSIGNMENT;
        } else if (obj instanceof Progress) {
            return Constants.ENTITY_TYPE.PROGRESS;
        }
        throw new CommandException(
                "This command is accessing non-existent entity or entity not extending from ModelObject");
    }

    /**
     * Returns the List of given Entity type.
     *
     * @param type the Entity type to get corresponding list.
     */
    private List<Object> getEntityFactory(Constants.ENTITY_TYPE type) throws CommandException {
        if (type == Constants.ENTITY_TYPE.STAFF) {
            return Arrays.asList(
                    this.staffAddressBook,
                    PREDICATE_SHOW_ALL_STAFFS,
                    filteredStaffs,
                    Constants.ENTITY_TYPE.STAFF);
        } else if (type == Constants.ENTITY_TYPE.STUDENT) {
            return Arrays.asList(
                    this.studentAddressBook,
                    PREDICATE_SHOW_ALL_STUDENTS,
                    filteredStudents,
                    Constants.ENTITY_TYPE.STUDENT);
        } else if (type == Constants.ENTITY_TYPE.FINANCE) {
            return Arrays.asList(
                    this.financeAddressBook,
                    PREDICATE_SHOW_ALL_FINANCES,
                    filteredFinances,
                    Constants.ENTITY_TYPE.FINANCE);
        } else if (type == Constants.ENTITY_TYPE.COURSE) {
            return Arrays.asList(
                    this.courseAddressBook,
                    PREDICATE_SHOW_ALL_COURSES,
                    filteredCourses,
                    Constants.ENTITY_TYPE.COURSE);
        } else if (type == Constants.ENTITY_TYPE.ASSIGNMENT) {
            return Arrays.asList(
                    this.assignmentAddressBook,
                    PREDICATE_SHOW_ALL_ASSIGNMENTS,
                    filteredAssignments,
                    Constants.ENTITY_TYPE.ASSIGNMENT);
        } else if (type == Constants.ENTITY_TYPE.PROGRESS) {
            return Arrays.asList(
                    this.progressAddressBook,
                    PREDICATE_SHOW_ALL_PROGRESSES,
                    filteredProgresses,
                    Constants.ENTITY_TYPE.PROGRESS);
        }
        throw new CommandException(
                "This command is accessing non-existent entity or entity not extending from ModelObject");
    }

    /**
     * Returns the List of given object.
     *
     * @param obj the object to get corresponding list.
     */
    public List<Object> getEntityFactory(ModelObject obj) throws CommandException {
        return getEntityFactory(modelObjectToEntityType(obj));
    }

    /**
     * Get the AddressBook of the object.
     */
    public AddressBookGeneric getAddressBook(ModelObject obj) throws CommandException {
        return (AddressBookGeneric) getEntityFactory(obj).get(0);
    }

    /**
     * Get the AddressBook of the entity type.
     */
    public AddressBookGeneric getAddressBook(Constants.ENTITY_TYPE type) throws CommandException {
        return (AddressBookGeneric) getEntityFactory(type).get(0);
    }

    /**
     * Get the unmodifiable view of AddressBook of the object.
     */
    public ReadOnlyAddressBookGeneric getReadOnlyAddressBook(ModelObject obj)
            throws CommandException {
        return (ReadOnlyAddressBookGeneric) getEntityFactory(obj).get(0);
    }

    /**
     * Get the unmodifiable view of AddressBook of the entity type.
     */
    public ReadOnlyAddressBookGeneric getReadOnlyAddressBook(Constants.ENTITY_TYPE type) throws CommandException {
        return (ReadOnlyAddressBookGeneric) getEntityFactory(type).get(0);
    }

    /**
     * Get Predicate of entity type.
     */
    public Predicate getPredicateAll(Constants.ENTITY_TYPE type) throws CommandException {
        return (Predicate) getEntityFactory(type).get(1);
    }

    /**
     * Get Predicate of object.
     */
    private Predicate getPredicateAll(ModelObject obj) throws CommandException {
        return (Predicate) getEntityFactory(obj).get(1);
    }

    /**
     * Get the filtered list of the entity type.
     */
    private FilteredList getFilterList(Constants.ENTITY_TYPE type) throws CommandException {
        return (FilteredList) getEntityFactory(type).get(2);
    }

    /**
     * Get filter list of object.
     */
    private FilteredList getFilterList(ModelObject obj) throws CommandException {
        return (FilteredList) getEntityFactory(obj).get(2);
    }

    /**
     * Get entity type of type.
     */
    private Constants.ENTITY_TYPE getEntityType(Constants.ENTITY_TYPE type) throws CommandException {
        return (Constants.ENTITY_TYPE) getEntityFactory(type).get(3);
    }

    /**
     * Get the entity type of the object.
     */
    public Constants.ENTITY_TYPE getEntityType(ModelObject obj) throws CommandException {
        return (Constants.ENTITY_TYPE) getEntityFactory(obj).get(3);
    }

    // ======================================================================================================

    /**
     * Get staff Address Book.
     */
    @Override
    public ReadOnlyAddressBookGeneric<Staff> getStaffAddressBook() {
        return staffAddressBook;
    }

    /**
     * Set staff Address Book.
     */
    @Override
    public void setStaffAddressBook(ReadOnlyAddressBookGeneric<Staff> staffAddressBook) {
        this.staffAddressBook.resetData(staffAddressBook);
        postDataStorageChangeEvent(staffAddressBook, Constants.ENTITY_TYPE.STAFF);
    }


    // =================================== CRUD METHODS =====================================================

    /**
     * Indicate if address book has an object with given id and entity type.
     */
    public boolean has(ID id, Constants.ENTITY_TYPE type) throws CommandException {
        requireAllNonNull(id, type);
        return getAddressBook(type).has(id);
    }

    /**
     * Get object from address book with given id and entity type.
     */
    @Override
    public ModelObject get(ID id, Constants.ENTITY_TYPE type) throws CommandException {
        requireAllNonNull(id, type);
        ModelObject obj = getAddressBook(type).get(id);
        return obj.clone();
    }

    /**
     * Indicate if address book has an object.
     */
    public boolean has(ModelObject obj) throws CommandException {
        requireNonNull(obj);
        return getAddressBook(obj).has(obj);
    }

    /**
     * Delete an object from address book.
     */
    @Override
    public void delete(ModelObject obj) throws CommandException {
        // Very important: Post this event before you really delete object
        postDeleteEntityEvent(obj.getId(), modelObjectToEntityType(obj));
        getAddressBook(obj).remove(obj);
        getFilterList(obj).setPredicate(getPredicateAll(obj));
        postDataStorageChangeEvent(getReadOnlyAddressBook(obj), getEntityType(obj));
    }

    /**
     * Add an object to address book.
     */
    @Override
    public void add(ModelObject obj) throws CommandException {
        getAddressBook(obj).add(obj);
        getFilterList(obj).setPredicate(getPredicateAll(obj));
        postDataStorageChangeEvent(getReadOnlyAddressBook(obj), getEntityType(obj));
    }

    /**
     * Get Index of an object in address book.
     */
    @Override
    public Index getIndex(ModelObject obj) throws CommandException {
        return getAddressBook(obj).getIndex(obj);
    }

    /**
     * Add an object to address book at given index.
     */
    @Override
    public void addAtIndex(ModelObject obj, Integer index) throws CommandException {
        getAddressBook(obj).addAtIndex(obj, index);
        getFilterList(obj).setPredicate(getPredicateAll(obj));
        postDataStorageChangeEvent(getReadOnlyAddressBook(obj), getEntityType(obj));
    }

    /**
     * Set an object in address book to a new object.
     */
    @Override
    public void set(ModelObject target, ModelObject editedTarget) throws CommandException {
        requireAllNonNull(target, editedTarget);
        getAddressBook(target).set(target, editedTarget);
        postDataStorageChangeEvent(getReadOnlyAddressBook(target), getEntityType(target));
    }
    // =========================== CRUD METHODS FOR PROGRESS =====================================================

    /**
     * Indicate if address book has a given progress.
     */
    @Override
    public boolean hasProgress(ID assignmentID, ID studentID) throws CommandException {
        CompositeID target = new CompositeID(assignmentID, studentID);
        return has(target, Constants.ENTITY_TYPE.PROGRESS);
    }

    /**
     * Get a progress by ID of assignment and ID of student.
     */
    @Override
    public Progress getProgress(ID assignmentID, ID studentID) throws CommandException {
        CompositeID target = new CompositeID(assignmentID, studentID);
        return (Progress) get(target, Constants.ENTITY_TYPE.PROGRESS);
    }

    /**
     * Remove a progress by ID of assignment and ID of student.
     */
    @Override
    public void removeProgress(ID assignmentID, ID studentID) throws CommandException {
        CompositeID target = new CompositeID(assignmentID, studentID);
        progressAddressBook.remove(target);
    }

    // =====================================================================================================

    ///

    /**
     * Get student Address Book.
     */
    @Override
    public ReadOnlyAddressBookGeneric<Student> getStudentAddressBook() {
        return studentAddressBook;
    }

    /**
     * Set student Address Book.
     */
    @Override
    public void setStudentAddressBook(ReadOnlyAddressBookGeneric<Student> studentAddressBook) {
        this.studentAddressBook.resetData(studentAddressBook);
        postDataStorageChangeEvent(studentAddressBook, Constants.ENTITY_TYPE.STUDENT);
    }

    ///

    /**
     * Get Finance Address Book.
     */
    @Override
    public ReadOnlyAddressBookGeneric<Finance> getFinanceAddressBook() {
        return financeAddressBook;
    }

    /**
     * Set Finance Address Book.
     */
    @Override
    public void setFinanceAddressBook(ReadOnlyAddressBookGeneric<Finance> financeAddressBook) {
        this.financeAddressBook.resetData(financeAddressBook);
        postDataStorageChangeEvent(financeAddressBook, Constants.ENTITY_TYPE.FINANCE);
    }

    ///

    /**
     * Get Course Address Book.
     */
    @Override
    public ReadOnlyAddressBookGeneric<Course> getCourseAddressBook() {
        return courseAddressBook;
    }

    /**
     * Set Course Address Book.
     */
    @Override
    public void setCourseAddressBook(ReadOnlyAddressBookGeneric<Course> courseAddressBook) {
        this.courseAddressBook.resetData(courseAddressBook);
        postDataStorageChangeEvent(courseAddressBook, Constants.ENTITY_TYPE.COURSE);
    }

    ///

    /**
     * Get Assignment Address Book.
     */
    @Override
    public ReadOnlyAddressBookGeneric<Assignment> getAssignmentAddressBook() {
        return assignmentAddressBook;
    }

    /**
     * Set Assignment Address Book.
     */
    @Override
    public void setAssignmentAddressBook(
            ReadOnlyAddressBookGeneric<Assignment> assignmentAddressBook) {
        this.assignmentAddressBook.resetData(assignmentAddressBook);
        postDataStorageChangeEvent(assignmentAddressBook, Constants.ENTITY_TYPE.ASSIGNMENT);
    }

    /**
     * Get Progress Address Book.
     */
    @Override
    public ReadOnlyAddressBookGeneric<Progress> getProgressAddressBook() {
        return progressAddressBook;
    }

    /**
     * Set Progress Address Book.
     */
    @Override
    public void setProgressAddressBook(ReadOnlyAddressBookGeneric<Progress> progressAddressBook) {
        this.progressAddressBook.resetData(progressAddressBook);
        postDataStorageChangeEvent(progressAddressBook, Constants.ENTITY_TYPE.PROGRESS);
    }

    //=========== Filtered List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    /**
     * Update filtered person list.
     */
    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Staff} backed by the internal list of {@code
     * versionedStaffAddressBook}
     */
    @Override
    public ObservableList<Staff> getFilteredStaffList() {
        return filteredStaffs;
    }

    /**
     * Update filtered staff list.
     */
    @Override
    public void updateFilteredStaffList(Predicate<Staff> predicate) {
        requireNonNull(predicate);
        filteredStaffs.setPredicate(predicate);
        dataStaffPredicate = predicate;
    }

    /**
     * Update observed data filtered staff list.
     */
    @Override
    public void updateObservedDataFilteredStaffList(Predicate<Staff> predicate) {
        requireNonNull(predicate);
        filteredStaffs.setPredicate(predicate);
    }

    /**
     * Update Extra filtered staff list.
     */
    @Override
    public void updateExtraFilteredStaffList(Predicate<Staff> predicate) {
        requireNonNull(predicate);
        filteredStaffs.setPredicate(predicate);
        extraStaffPredicate = predicate;
    }

    /**
     * Update Observed Extra filtered staff list.
     */
    @Override
    public void updateObservedExtraFilteredStaffList(Predicate<Staff> predicate) {
        requireNonNull(predicate);
        filteredStaffs.setPredicate(predicate);
        filteredStaffs.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedStudentAddressBook}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    /**
     * Update Filtered Student list.
     */
    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
        dataStudentPredicate = predicate;
    }

    /**
     * Update Observed Data Filtered Student list.
     */
    @Override
    public void updateObservedDataFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    /**
     * Update Extra Filtered Student List.
     */
    @Override
    public void updateExtraFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
        extraStudentPredicate = predicate;
    }

    /**
     * Update Observed Extra Filtered Student List.
     */
    @Override
    public void updateObservedExtraFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Finance} backed by the internal list of
     * {@code versionedFinanceAddressBook}
     */
    @Override
    public ObservableList<Finance> getFilteredFinanceList() {
        return filteredFinances;
    }

    /**
     * Update Filtered Finance List.
     */
    @Override
    public void updateFilteredFinanceList(Predicate<Finance> predicate) {
        requireNonNull(predicate);
        filteredFinances.setPredicate(predicate);
        dataFinancePredicate = predicate;
    }

    /**
     * Update Observed Data Filtered Finance List.
     */
    @Override
    public void updateObservedDataFilteredFinanceList(Predicate<Finance> predicate) {
        requireNonNull(predicate);
        filteredFinances.setPredicate(predicate);
    }

    /**
     * Update Extra Filtered Finance List.
     */
    @Override
    public void updateExtraFilteredFinanceList(Predicate<Finance> predicate) {
        requireNonNull(predicate);
        filteredFinances.setPredicate(predicate);
        extraFinancePredicate = predicate;
    }

    /**
     * Update Observed Extra Filtered Finance List.
     */
    @Override
    public void updateObservedExtraFilteredFinanceList(Predicate<Finance> predicate) {
        requireNonNull(predicate);
        filteredFinances.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Course} backed by the internal list of
     * {@code versionedCourseAddressBook}
     */
    @Override
    public ObservableList<Course> getFilteredCourseList() {
        return filteredCourses;
    }

    /**
     * Update Filtered Course List.
     */
    @Override
    public void updateFilteredCourseList(Predicate<Course> predicate) {
        requireNonNull(predicate);
        filteredCourses.setPredicate(predicate);
        dataCoursePredicate = predicate;
    }

    /**
     * Update Observed Data Filtered Course List.
     */
    @Override
    public void updateObservedDataFilteredCourseList(Predicate<Course> predicate) {
        requireNonNull(predicate);
        filteredCourses.setPredicate(predicate);
    }

    /**
     * Update Extra Filtered Student Course List.
     */
    @Override
    public void updateExtraFilteredStudentCourseList(Predicate<Course> predicate) {
        requireNonNull(predicate);
        filteredCourses.setPredicate(predicate);
        extraStudentCoursePredicate = predicate;
    }

    /**
     * Update Extra Filtered Staff Course List.
     */
    @Override
    public void updateExtraFilteredStaffCourseList(Predicate<Course> predicate) {
        requireNonNull(predicate);
        filteredCourses.setPredicate(predicate);
        extraStaffCoursePredicate = predicate;
    }

    /**
     * Update Observed Extra Filtered Course List.
     */
    @Override
    public void updateObservedExtraFilteredCourseList(Predicate<Course> predicate) {
        requireNonNull(predicate);
        filteredCourses.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Assignment} backed by the internal list of
     * {@code versionedAssignmentAddressBook}
     */
    @Override
    public ObservableList<Assignment> getFilteredAssignmentList() {
        return filteredAssignments;
    }

    /**
     * Update Filtered Assignment List.
     */
    @Override
    public void updateFilteredAssignmentList(Predicate<Assignment> predicate) {
        requireNonNull(predicate);
        filteredAssignments.setPredicate(predicate);
        dataAssignmentPredicate = predicate;
    }

    /**
     * Update Observed Data Filtered Assignment List.
     */
    @Override
    public void updateObservedDataFilteredAssignmentList(Predicate<Assignment> predicate) {
        requireNonNull(predicate);
        filteredAssignments.setPredicate(predicate);
    }

    /**
     * Update Extra Filtered Assignment List.
     */
    @Override
    public void updateExtraFilteredAssignmentList(Predicate<Assignment> predicate) {
        requireNonNull(predicate);
        filteredAssignments.setPredicate(predicate);
        extraAssignmentPredicate = predicate;
    }

    /**
     * Update Observed Extra Filtered Assignment List.
     */
    @Override
    public void updateObservedExtraFilteredAssignmentList(Predicate<Assignment> predicate) {
        requireNonNull(predicate);
        filteredAssignments.setPredicate(predicate);
    }


    /**
     * Returns an unmodifiable view of the filtered finance list
     */
    @Override
    public ObservableList<Progress> getFilteredProgressList() {
        return filteredProgresses;
    }

    /**
     * Updates the filter of the filtered progress list to filter by the given {@code predicate}.
     *
     * @param predicate input
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredProgressList(Predicate<Progress> predicate) {
        requireNonNull(predicate);
        filteredProgresses.setPredicate(predicate);
    }

    /**
     * Override equals method to compare two objs.
     */
    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        //
        ModelManager other = (ModelManager) obj;
        return userPrefs.equals(other.userPrefs)
                && staffAddressBook.equals(other.staffAddressBook)
                && studentAddressBook.equals(other.studentAddressBook)
                && courseAddressBook.equals(other.courseAddressBook)
                && financeAddressBook.equals(other.financeAddressBook)
                && assignmentAddressBook.equals(other.assignmentAddressBook)
                && progressAddressBook.equals(other.progressAddressBook)
                && filteredStaffs.equals(other.filteredStaffs)
                && filteredStudents.equals(other.filteredStudents)
                && filteredCourses.equals(other.filteredCourses)
                && filteredFinances.equals(other.filteredFinances)
                && filteredAssignments.equals(other.filteredAssignments)
                && filteredProgresses.equals(other.filteredProgresses);
    }

    // ========================== Getters for Predicates =========================

    /**
     * Get Data Student Predicate.
     *
     * @return data Student Predicate.
     */
    public Predicate<Student> getDataStudentPredicate() {
        return dataStudentPredicate;
    }

    /**
     * Get Data Staff Predicate.
     *
     * @return data staff Predicate.
     */
    public Predicate<Staff> getDataStaffPredicate() {
        return dataStaffPredicate;
    }

    /**
     * Get Data Finance Predicate.
     *
     * @return data Finance Predicate.
     */
    public Predicate<Finance> getDataFinancePredicate() {
        return dataFinancePredicate;
    }

    /**
     * Get Data Course Predicate.
     *
     * @return data course Predicate.
     */
    public Predicate<Course> getDataCoursePredicate() {
        return dataCoursePredicate;
    }

    /**
     * Get Data Assignment Predicate.
     *
     * @return data assignment Predicate.
     */
    public Predicate<Assignment> getDataAssignmentPredicate() {
        return dataAssignmentPredicate;
    }

    /**
     * Get Extra Student Predicate.
     *
     * @return extra student Predicate.
     */
    public Predicate<Student> getExtraStudentPredicate() {
        return extraStudentPredicate;
    }

    /**
     * Get Extra Staff Predicate.
     *
     * @return extra staff Predicate.
     */
    public Predicate<Staff> getExtraStaffPredicate() {
        return extraStaffPredicate;
    }

    /**
     * Get Extra Finance Predicate.
     *
     * @return extra finance Predicate.
     */
    public Predicate<Finance> getExtraFinancePredicate() {
        return extraFinancePredicate;
    }

    /**
     * Get Extra Student Course Predicate.
     *
     * @return extra student course Predicate.
     */
    public Predicate<Course> getExtraStudentCoursePredicate() {
        return extraStudentCoursePredicate;
    }

    /**
     * Get Extra Staff Course Predicate.
     *
     * @return extra staff course Predicate.
     */
    public Predicate<Course> getExtraStaffCoursePredicate() {
        return extraStaffCoursePredicate;
    }

    /**
     * Get Extra Assignment Predicate.
     *
     * @return extra Assignment Predicate.
     */
    public Predicate<Assignment> getExtraAssignmentPredicate() {
        return extraAssignmentPredicate;
    }
}
