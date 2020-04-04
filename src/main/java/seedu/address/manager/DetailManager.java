package seedu.address.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.BaseManager;
import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelProgress.Progress;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;


import java.util.*;

import static seedu.address.logic.parser.CliSyntax.*;

// TODO: Think of better name?
public class DetailManager extends BaseManager {
    enum TYPE {
        STUDENT_DETAILS,
        STUDENT_COURSE_DETAILS,
        COURSE_DETAILS,
        COURSE_STUDENT_DETAILS,
    }

    public TYPE type;

    private static DetailManager instance;

    public static DetailManager getInstance() {
        return instance;
    }

    private static Model model = ModelManager.getInstance();
    // Detail Manager will have ObservableLists that LogicManager will also contain.

    private ObservableMap<String, Object> studentDetailsMap;
    private ObservableMap<String, Object> courseDetailsMap;

    HashMap<Prefix, ID> IdMapping;

    public DetailManager() {
        studentDetailsMap = FXCollections.observableMap(new HashMap<String, Object>());
        courseDetailsMap = FXCollections.observableMap(new HashMap<String, Object>());
        initializeStudentDetailsMap();
        IdMapping = new HashMap<Prefix, ID>();
        instance = this;
    }

    private void initializeStudentDetailsMap() {
        Set<ID> assignedCourses = new HashSet<ID>();
        assignedCourses.add(new ID("11"));
        assignedCourses.add(new ID("12"));
        Set<Tag> assignedTags = new HashSet<Tag>();
        assignedTags.add(new Tag("Cool"));
        assignedTags.add(new Tag("CS"));
        Student fakeStudent = new Student(new Name("Tommy"), new ID("1231111111111"), assignedCourses, assignedTags);
        fakeStudent.processAssignedCourses((FilteredList<Course>) model.getFilteredCourseList());
        studentDetailsMap.put("details", fakeStudent);
        studentDetailsMap.put("courses", FXCollections.observableList(new ArrayList<Course>()));
    }

    public ObservableMap<String, Object> getFilteredStudentDetailsMap() {
        return this.studentDetailsMap;
    }

    public ObservableMap<String, Object> getFilteredCourseDetailsMap() {
        return this.courseDetailsMap;
    }

    // Select commands will update the observable lists here
    public void updateType(List<ArgumentTokenizer.PrefixPosition> positions,
                         List<ID> selectMetaDataIDs) {
        this.type = this.getType(positions);
        for (int i = 0; i < positions.size(); i ++) {
            IdMapping.put(positions.get(i).getPrefix(), selectMetaDataIDs.get(i));
        }
    }

    public TYPE getType(List<ArgumentTokenizer.PrefixPosition> positions) {
        if (positions.get(0).getPrefix().equals(PREFIX_STUDENTID)) {
            if (positions.size() == 1) {
                return TYPE.STUDENT_DETAILS;
            }

            if (positions.get(1).getPrefix().equals(PREFIX_COURSEID)) {
                return TYPE.STUDENT_COURSE_DETAILS;
            }

        } else if (positions.get(0).getPrefix().equals(PREFIX_COURSEID)) {
            if (positions.size() == 1) {
                return TYPE.COURSE_DETAILS;
            }

            if (positions.get(1).getPrefix().equals(PREFIX_STUDENTID)) {
                return TYPE.COURSE_STUDENT_DETAILS;
            }
        }
        return TYPE.COURSE_DETAILS;
    }

    public void updateDetails(List<ArgumentTokenizer.PrefixPosition> positions,
                             List<ID> selectMetaDataIDs) throws CommandException {
        updateType(positions, selectMetaDataIDs);
        if (type.equals(TYPE.STUDENT_DETAILS)) {
            updateStudentDetailsMap(IdMapping.get(PREFIX_STUDENTID));
        } else if (type.equals(TYPE.STUDENT_COURSE_DETAILS)) {
            updateStudentDetailsMap(IdMapping.get(PREFIX_STUDENTID));
            updateProgressStudentCourse(IdMapping.get(PREFIX_STUDENTID), IdMapping.get(PREFIX_COURSEID));
        } else if (type.equals(TYPE.COURSE_DETAILS)) {
            updateCourseDetailsMap(IdMapping.get(PREFIX_COURSEID));
        } else if (type.equals(TYPE.COURSE_STUDENT_DETAILS)) {
            updateCourseDetailsMap(IdMapping.get(PREFIX_COURSEID));
            updateProgressCourseStudent(IdMapping.get(PREFIX_COURSEID), IdMapping.get(PREFIX_STUDENTID));
        }
    }

    // ################# Update student details map ##################################################3
    public void updateStudentDetailsMap(ID studentID) throws CommandException {
        Student student = (Student)model.get(studentID, Constants.ENTITY_TYPE.STUDENT);
        studentDetailsMap.put("details", student);

        ObservableList<HashMap> courses = FXCollections.observableArrayList();
        Set<ID> courseIDs = student.getAssignedCoursesID();
        for (ID courseID: courseIDs) {
            HashMap<String, Object> courseDetail = new HashMap<String, Object>();
            courseDetail.put("info", (Course)model.getAddressBook(Constants.ENTITY_TYPE.COURSE).get(courseID));
            courseDetail.put("progress_list",
                    FXCollections.observableArrayList(new ArrayList<Progress>()));
            courseDetail.put("number_of_done_progress", ProgressManager.getNumberOfProgressesDone(courseID, studentID));
            courses.add(courseDetail);
        }
        studentDetailsMap.put("courses", courses);
    }

    public void updateProgressStudentCourse(ID studentID, ID courseID) throws CommandException {
        for (HashMap<String, Object> courseDetail: (ObservableList<HashMap>)studentDetailsMap.get("courses")) {
            Course course = (Course)courseDetail.get("info");
            if (course.getId().equals(courseID)) {
                courseDetail.put("progress_list",
                        FXCollections.observableArrayList(new ArrayList<Progress>(ProgressManager.getProgress(courseID, studentID))));
            } else {
                courseDetail.put("progress_list",
                        FXCollections.observableArrayList(new ArrayList<Progress>()));
            }
        }
    }
    // #######################################################################################################



    // ###############  Update course details map ###########################################################
    public void updateCourseDetailsMap(ID courseID) throws CommandException {
        Course course = (Course)model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        courseDetailsMap.put("details", course);

        ObservableList<HashMap> students = FXCollections.observableArrayList();
        Set<ID> studentIDs = course.getAssignedStudentsID();
        for (ID studentID: studentIDs) {
            HashMap<String, Object> studentDetail = new HashMap<String, Object>();
            studentDetail.put("info", (Student)model.getAddressBook(Constants.ENTITY_TYPE.STUDENT).get(studentID));
            studentDetail.put("progress_list",
                    FXCollections.observableArrayList(new ArrayList<Progress>()));
            studentDetail.put("number_of_done_progress", ProgressManager.getNumberOfProgressesDone(courseID, studentID));
            students.add(studentDetail);
        }
        courseDetailsMap.put("students", students);
    }

    public void updateProgressCourseStudent(ID courseID, ID studentID) throws CommandException {
        for (HashMap<String, Object> studentDetail: (ObservableList<HashMap>)courseDetailsMap.get("students")) {
            Student student = (Student)studentDetail.get("info");
            if (student.getId().equals(studentID)) {
                studentDetail.put("progress_list",
                        FXCollections.observableArrayList(new ArrayList<Progress>(ProgressManager.getProgress(courseID, studentID))));
            } else {
                studentDetail.put("progress_list",
                        FXCollections.observableArrayList(new ArrayList<Progress>()));
            }
        }
    }
    // #######################################################################################################



}
