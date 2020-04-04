package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.BaseManager;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
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
import seedu.address.model.modelProgress.Progress;
import seedu.address.model.modelProgress.ProgressAddressBook;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStaff.StaffAddressBook;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.modelStudent.StudentAddressBook;
import seedu.address.model.person.CompositeID;
import seedu.address.model.person.ID;
import seedu.address.model.person.Person;
import seedu.address.ui.MainWindow;

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

  private static ModelManager instance;

  // Whenever other managers refer to model manager, modelManager must already be created
  public static ModelManager getInstance() {
    return instance;
  }

  /**
   * Initializes a ModelManager with the given addressBook and userPrefs.
   */
  public ModelManager(ReadOnlyAddressBook addressBook,
      ReadOnlyAddressBookGeneric<Staff> staffAddressBook,
      ReadOnlyAddressBookGeneric<Student> studentAddressBook,
      ReadOnlyAddressBookGeneric<Finance> financeAddressBook,
      ReadOnlyAddressBookGeneric<Course> courseAddressBook,
      ReadOnlyAddressBookGeneric<Assignment> assignmentAddressBook,
      ReadOnlyAddressBookGeneric<Progress> progressAssignmentBook,
      ReadOnlyUserPrefs userPrefs) {
    super();

    requireAllNonNull(staffAddressBook, studentAddressBook, financeAddressBook, courseAddressBook,
        assignmentAddressBook, progressAssignmentBook, userPrefs);

    logger.info("Model Manager check:" + assignmentAddressBook.toString());

    logger.info("Model Manager check:" + assignmentAddressBook.toString());

    logger.fine("Initializing with address book: " + studentAddressBook
        + "Initializing with staff address book: " + staffAddressBook
        + "Initializing with address address book: " + assignmentAddressBook
        + " and user prefs " + userPrefs);

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

    for (Course course : filteredCourses) {
      course.processAssignedStudents(filteredStudents);
      course.processAssignedStaff(filteredStaffs);
    }

    for (Student student : filteredStudents) {
      student.processAssignedCourses(filteredCourses);
    }

    for (Staff staff : filteredStaffs) {
      staff.processAssignedCourses(filteredCourses);
    }

    instance = this;

  }

  public ModelManager() {
    this(new AddressBook(), new StaffAddressBook(), new StudentAddressBook(),
        new FinanceAddressBook(), new CourseAddressBook(),
        new AssignmentAddressBook(), new ProgressAddressBook(),
        new UserPrefs());
  }

  public MainWindow getMainWindow(){
    return this.mainWindow;
  }

  public void setMainWindow(MainWindow mainWindow){
    this.mainWindow = mainWindow;
  }
  //=========== UserPrefs ==================================================================================

  @Override
  public ReadOnlyUserPrefs getUserPrefs() {
    return userPrefs;
  }

  @Override
  public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
    requireNonNull(userPrefs);
    this.userPrefs.resetData(userPrefs);
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
  public Path getAddressBookFilePath() {
    return userPrefs.getAddressBookFilePath();
  }

  @Override
  public void setAddressBookFilePath(Path addressBookFilePath) {
    requireNonNull(addressBookFilePath);
    userPrefs.setAddressBookFilePath(addressBookFilePath);
  }

  @Override
  public Path getStaffAddressBookFilePath() {
    return userPrefs.getStaffAddressBookFilePath();
  }

  @Override
  public void setStaffAddressBookFilePath(Path staffAddressBookFilePath) {
    requireNonNull(staffAddressBookFilePath);
    userPrefs.setStaffAddressBookFilePath(staffAddressBookFilePath);
  }

  @Override
  public Path getStudentAddressBookFilePath() {
    return userPrefs.getStudentAddressBookFilePath();
  }

  @Override
  public void setStudentAddressBookFilePath(Path studentAddressBookFilePath) {
    requireNonNull(studentAddressBookFilePath);
    userPrefs.setStudentAddressBookFilePath(studentAddressBookFilePath);
  }

  @Override
  public Path getFinanceAddressBookFilePath() {
    return userPrefs.getFinanceAddressBookFilePath();
  }

  @Override
  public void setFinanceAddressBookFilePath(Path financeAddressBookFilePath) {
    requireNonNull(financeAddressBookFilePath);
    userPrefs.setFinanceAddressBookFilePath(financeAddressBookFilePath);
  }

  @Override
  public Path getCourseAddressBookFilePath() {
    return userPrefs.getCourseAddressBookFilePath();
  }

  @Override
  public void setCourseAddressBookFilePath(Path courseAddressBookFilePath) {
    requireNonNull(courseAddressBookFilePath);
    userPrefs.setCourseAddressBookFilePath(courseAddressBookFilePath);
  }

  @Override
  public Path getAssignmentAddressBookFilePath() {
    return userPrefs.getAssignmentAddressBookFilePath();
  }

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
   * @param progressAddressBookFilePath
   */
  @Override
  public void setProgressAddressBookFilePath(Path progressAddressBookFilePath) {
    requireNonNull(progressAddressBookFilePath);
    userPrefs.setProgressAddressBookFilePath(progressAddressBookFilePath);
  }

  @Override
  public ReadOnlyAddressBook getAddressBook() {
    return addressBook;
  }

  //=========== AddressBook ================================================================================
  ///


  // ================================== FACTORY HELPERS =================================================
  private List<Object> getEntityFactory(ModelObject obj) throws CommandException {
    if (obj instanceof Staff) {
      return Arrays.asList(
          this.staffAddressBook,
          PREDICATE_SHOW_ALL_STAFFS,
          filteredStaffs,
          Constants.ENTITY_TYPE.STAFF);
    } else if (obj instanceof Student) {
      return Arrays.asList(
          this.studentAddressBook,
          PREDICATE_SHOW_ALL_STUDENTS,
          filteredStudents,
          Constants.ENTITY_TYPE.STUDENT);
    } else if (obj instanceof Finance) {
      return Arrays.asList(
          this.financeAddressBook,
          PREDICATE_SHOW_ALL_FINANCES,
          filteredFinances,
          Constants.ENTITY_TYPE.FINANCE);
    } else if (obj instanceof Course) {
      return Arrays.asList(
          this.courseAddressBook,
          PREDICATE_SHOW_ALL_COURSES,
          filteredCourses,
          Constants.ENTITY_TYPE.COURSE);
    } else if (obj instanceof Assignment) {
      return Arrays.asList(
          this.assignmentAddressBook,
          PREDICATE_SHOW_ALL_ASSIGNMENTS,
          filteredAssignments,
          Constants.ENTITY_TYPE.ASSIGNMENT);
    } else if (obj instanceof Progress) {
      return Arrays.asList(
              this.progressAddressBook,
              PREDICATE_SHOW_ALL_PROGRESSES,
              filteredProgresses,
              Constants.ENTITY_TYPE.PROGRESS);
    }
    throw new CommandException(
        "This command is accessing non-existent entity or entity not extending from ModelObject");
  }

  private AddressBookGeneric getAddressBook(ModelObject obj) throws CommandException {
    return (AddressBookGeneric) getEntityFactory(obj).get(0);
  }

  private ReadOnlyAddressBookGeneric getReadOnlyAddressBook(ModelObject obj)
      throws CommandException {
    return (ReadOnlyAddressBookGeneric) getEntityFactory(obj).get(0);
  }

  private Predicate getPredicateAll(ModelObject obj) throws CommandException {
    return (Predicate) getEntityFactory(obj).get(1);
  }

  @Override
  public ReadOnlyAddressBookGeneric<Staff> getStaffAddressBook() {
    return staffAddressBook;
  }

  @Override
  public void setStaffAddressBook(ReadOnlyAddressBookGeneric<Staff> staffAddressBook) {
    this.staffAddressBook.resetData(staffAddressBook);
  }


  private FilteredList getFilterList(ModelObject obj) throws CommandException {
    return (FilteredList) getEntityFactory(obj).get(2);

  }

  private Constants.ENTITY_TYPE getEntityType(ModelObject obj) throws CommandException {
    return (Constants.ENTITY_TYPE) getEntityFactory(obj).get(3);
  }
  // ======================================================================================================

  // =================================== CRUD METHODS =====================================================
  public boolean has(ModelObject obj) throws CommandException {
    requireNonNull(obj);
    return getAddressBook(obj).has(obj);
  }

  @Override
  public void delete(ModelObject obj) throws CommandException {
    getAddressBook(obj).remove(obj);
    getFilterList(obj).setPredicate(getPredicateAll(obj));
    postDataStorageChangeEvent(getReadOnlyAddressBook(obj), getEntityType(obj));
  }

  @Override
  public void add(ModelObject obj) throws CommandException {
    getAddressBook(obj).add(obj);
    getFilterList(obj).setPredicate(getPredicateAll(obj));
    postDataStorageChangeEvent(getReadOnlyAddressBook(obj), getEntityType(obj));
  }

  @Override
  public void addAtIndex(ModelObject obj, Integer index) throws CommandException {
    getAddressBook(obj).addAtIndex(obj, index);
    getFilterList(obj).setPredicate(getPredicateAll(obj));
    postDataStorageChangeEvent(getReadOnlyAddressBook(obj), getEntityType(obj));
  }

  @Override
  public void set(ModelObject target, ModelObject editedTarget) throws CommandException {
    requireAllNonNull(target, editedTarget);
    getAddressBook(target).set(target, editedTarget);
    postDataStorageChangeEvent(getReadOnlyAddressBook(target), getEntityType(target));
  }

  // =========================== CRUD METHODS DONE VIA ID =====================================================

  @Override
  public boolean hasStudent(ID studentID) {
    return studentAddressBook.has(studentID);
  }

  @Override
  public Student getStudent(ID studentID) {
    return studentAddressBook.get(studentID);
  }

  @Override
  public boolean hasCourse(ID courseID) {
    return courseAddressBook.has(courseID);
  }

  @Override
  public Course getCourse(ID courseID) {
    return courseAddressBook.get(courseID);
  }

  @Override
  public boolean hasAssignment(ID assignmentID) {
    return assignmentAddressBook.has(assignmentID);
  }

  @Override
  public Assignment getAssignment(ID assignmentID) {
    return assignmentAddressBook.get(assignmentID);
  }

  @Override
  public boolean hasStaff(ID staffID) {
    return staffAddressBook.has(staffID);
  }

  @Override
  public Staff getStaff(ID staffID) {
    return staffAddressBook.get(staffID);
  }

  @Override
  public boolean hasProgress(ID assignmentID, ID studentID) throws CommandException {
    CompositeID target = new CompositeID(assignmentID, studentID);
    return progressAddressBook.has(target);
  }

  @Override
  public Progress getProgress(ID assignmentID, ID studentID) throws CommandException {
    CompositeID target = new CompositeID(assignmentID, studentID);
    return progressAddressBook.get(target);
  }

  // =====================================================================================================

  ///
  @Override
  public ReadOnlyAddressBookGeneric<Student> getStudentAddressBook() {
    return studentAddressBook;
  }


  @Override
  public void setStudentAddressBook(ReadOnlyAddressBookGeneric<Student> studentAddressBook) {
    this.studentAddressBook.resetData(studentAddressBook);
  }

  ///
  @Override
  public ReadOnlyAddressBookGeneric<Finance> getFinanceAddressBook() {
    return financeAddressBook;
  }


  @Override
  public void setFinanceAddressBook(ReadOnlyAddressBookGeneric<Finance> financeAddressBook) {
    this.financeAddressBook.resetData(financeAddressBook);
  }

  ///
  @Override
  public ReadOnlyAddressBookGeneric<Course> getCourseAddressBook() {
    return courseAddressBook;
  }


  @Override
  public void setCourseAddressBook(ReadOnlyAddressBookGeneric<Course> courseAddressBook) {
    this.courseAddressBook.resetData(courseAddressBook);
  }

  ///
  @Override
  public ReadOnlyAddressBookGeneric<Assignment> getAssignmentAddressBook() {
    return assignmentAddressBook;
  }

  @Override
  public void setAssignmentAddressBook(
      ReadOnlyAddressBookGeneric<Assignment> assignmentAddressBook) {
    this.assignmentAddressBook.resetData(assignmentAddressBook);
  }

  @Override
  public ReadOnlyAddressBookGeneric<Progress> getProgressAddressBook() {
    return progressAddressBook;
  }

  @Override
  public void setProgressAddressBook(ReadOnlyAddressBookGeneric<Progress> progressAddressBook) {
    this.progressAddressBook.resetData(progressAddressBook);
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

  @Override
  public void updateFilteredStaffList(Predicate<Staff> predicate) {
    requireNonNull(predicate);
    filteredStaffs.setPredicate(predicate);
    dataStaffPredicate = predicate;
  }

  @Override
  public void updateObservedDataFilteredStaffList(Predicate<Staff> predicate) {
    requireNonNull(predicate);
    filteredStaffs.setPredicate(predicate);
  }

  @Override
  public void updateExtraFilteredStaffList(Predicate<Staff> predicate) {
    requireNonNull(predicate);
    filteredStaffs.setPredicate(predicate);
    extraStaffPredicate = predicate;
  }

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

  @Override
  public void updateFilteredStudentList(Predicate<Student> predicate) {
    requireNonNull(predicate);
    filteredStudents.setPredicate(predicate);
    dataStudentPredicate = predicate;
  }

  @Override
  public void updateObservedDataFilteredStudentList(Predicate<Student> predicate) {
    requireNonNull(predicate);
    filteredStudents.setPredicate(predicate);
  }

  @Override
  public void updateExtraFilteredStudentList(Predicate<Student> predicate) {
    requireNonNull(predicate);
    filteredStudents.setPredicate(predicate);
    extraStudentPredicate = predicate;
  }

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

  @Override
  public void updateFilteredFinanceList(Predicate<Finance> predicate) {
    requireNonNull(predicate);
    filteredFinances.setPredicate(predicate);
    dataFinancePredicate = predicate;
  }

  @Override
  public void updateObservedDataFilteredFinanceList(Predicate<Finance> predicate) {
    requireNonNull(predicate);
    filteredFinances.setPredicate(predicate);
  }

  @Override
  public void updateExtraFilteredFinanceList(Predicate<Finance> predicate) {
    requireNonNull(predicate);
    filteredFinances.setPredicate(predicate);
    extraFinancePredicate = predicate;
  }

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

  @Override
  public void updateFilteredCourseList(Predicate<Course> predicate) {
    requireNonNull(predicate);
    filteredCourses.setPredicate(predicate);
    dataCoursePredicate = predicate;
  }

  @Override
  public void updateObservedDataFilteredCourseList(Predicate<Course> predicate) {
    requireNonNull(predicate);
    filteredCourses.setPredicate(predicate);
  }

  @Override
  public void updateExtraFilteredStudentCourseList(Predicate<Course> predicate) {
    requireNonNull(predicate);
    filteredCourses.setPredicate(predicate);
    extraStudentCoursePredicate = predicate;
  }

  @Override
  public void updateExtraFilteredStaffCourseList(Predicate<Course> predicate) {
    requireNonNull(predicate);
    filteredCourses.setPredicate(predicate);
    extraStaffCoursePredicate = predicate;
  }

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

  @Override
  public void updateFilteredAssignmentList(Predicate<Assignment> predicate) {
    requireNonNull(predicate);
    filteredAssignments.setPredicate(predicate);
    dataAssignmentPredicate = predicate;
  }

  @Override
  public void updateObservedDataFilteredAssignmentList(Predicate<Assignment> predicate) {
    requireNonNull(predicate);
    filteredAssignments.setPredicate(predicate);
  }

  @Override
  public void updateExtraFilteredAssignmentList(Predicate<Assignment> predicate) {
    requireNonNull(predicate);
    filteredAssignments.setPredicate(predicate);
    extraAssignmentPredicate = predicate;
  }

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
   * Updates the filter of the filtered assignment list to filter by the given {@code predicate}.
   *
   * @param predicate
   * @throws NullPointerException if {@code predicate} is null.
   */
  @Override
  public void updateFilteredProgressList(Predicate<Progress> predicate) {
    requireNonNull(predicate);
    filteredProgresses.setPredicate(predicate);
  }

  // ========================== For Assigning of X TO Y =========================

  public void assignStudentToCourse(ID studentID, ID courseID) throws CommandException {
    Course foundCourse = getCourse(courseID);
    Student foundStudent = getStudent(studentID);

    foundCourse.addStudent(studentID);
    foundStudent.addCourse(courseID);
    foundCourse.processAssignedStudents(
        (FilteredList<Student>) getFilteredStudentList());
    foundStudent.processAssignedCourses(
        (FilteredList<Course>) getFilteredCourseList());
    updateFilteredCourseList(PREDICATE_SHOW_ALL_COURSES);
    updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

    set(foundCourse, foundCourse);
    set(foundStudent, foundStudent);
  }

  public void assignAssignmentToCourse(ID assignmentID, ID courseID) throws CommandException {
    Course foundCourse = getCourse(courseID);
    Assignment foundAssignment = getAssignment(assignmentID);

    foundCourse.addAssignment(assignmentID);
    foundAssignment.addCourseID(courseID);

    set(foundCourse, foundCourse);
    set(foundAssignment, foundAssignment);

  }

  public void assignTeacherToCourse(ID staffID, ID courseID) throws CommandException {
    Course foundCourse = getCourse(courseID);
    Staff foundTeacher = getStaff(staffID);

    foundCourse.addStaff(staffID);
    foundTeacher.addCourse(courseID);

    foundCourse.processAssignedStaff(
        (FilteredList<Staff>) getFilteredStaffList());
    foundTeacher.processAssignedCourses(
        (FilteredList<Course>) getFilteredCourseList());
    set(foundCourse, foundCourse);
    set(foundTeacher, foundTeacher);
  }

  // ========================== For Unassigning of X FROM Y =========================

  public void unassignAssignmentFromCourse(ID assignmentID, ID courseID) throws CommandException {
    Course foundCourse = getCourse(courseID);
    Assignment foundAssignment = getAssignment(assignmentID);

    foundCourse.removeAssignment(assignmentID);
    foundAssignment.removeCourseID(courseID);

    requireAllNonNull(foundCourse, foundCourse);
    getAddressBook(foundCourse).set(foundCourse, foundCourse);
    postDataStorageChangeEvent(getReadOnlyAddressBook(foundCourse), getEntityType(foundCourse));

    requireAllNonNull(foundAssignment, foundAssignment);
    getAddressBook(foundAssignment).set(foundAssignment, foundAssignment);
    postDataStorageChangeEvent(getReadOnlyAddressBook(foundAssignment),
        getEntityType(foundAssignment));

  }

  public void unassignStudentFromCourse(ID studentID, ID courseID) throws CommandException {
    Course foundCourse = getCourse(courseID);
    Student foundStudent = getStudent(studentID);

    foundCourse.removeStudent(studentID);
    foundStudent.removeCourse(courseID);
    foundCourse.processAssignedStudents(
        (FilteredList<Student>) getFilteredStudentList());
    foundStudent.processAssignedCourses(
        (FilteredList<Course>) getFilteredCourseList());
    updateFilteredCourseList(PREDICATE_SHOW_ALL_COURSES);
    updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

    requireAllNonNull(foundCourse, foundCourse);
    getAddressBook(foundCourse).set(foundCourse, foundCourse);
    postDataStorageChangeEvent(getReadOnlyAddressBook(foundCourse), getEntityType(foundCourse));

    requireAllNonNull(foundStudent, foundStudent);
    getAddressBook(foundStudent).set(foundStudent, foundStudent);
    postDataStorageChangeEvent(getReadOnlyAddressBook(foundStudent), getEntityType(foundStudent));
  }


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

  public Predicate<Student> getDataStudentPredicate() {
    return dataStudentPredicate;
  }

  public Predicate<Staff> getDataStaffPredicate() {
    return dataStaffPredicate;
  }

  public Predicate<Finance> getDataFinancePredicate() {
    return dataFinancePredicate;
  }

  public Predicate<Course> getDataCoursePredicate() {
    return dataCoursePredicate;
  }

  public Predicate<Assignment> getDataAssignmentPredicate() {
    return dataAssignmentPredicate;
  }

  public Predicate<Student> getExtraStudentPredicate() {
    return extraStudentPredicate;
  }

  public Predicate<Staff> getExtraStaffPredicate() {
    return extraStaffPredicate;
  }

  public Predicate<Finance> getExtraFinancePredicate() {
    return extraFinancePredicate;
  }

  public Predicate<Course> getExtraStudentCoursePredicate() {
    return extraStudentCoursePredicate;
  }

  public Predicate<Course> getExtraStaffCoursePredicate() {
    return extraStaffCoursePredicate;
  }

  public Predicate<Assignment> getExtraAssignmentPredicate() {
    return extraAssignmentPredicate;
  }
}
