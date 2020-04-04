package seedu.address.manager;

import javafx.collections.FXCollections;
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
        COURSE_STUDENT
    }

    public TYPE type;

    private static DetailManager instance;

    public static DetailManager getInstance() {
        return instance;
    }

    private static Model model = ModelManager.getInstance();
    // Detail Manager will have ObservableLists that LogicManager will also contain.

    private ObservableMap<String, Object> studentDetailsMap;

    HashMap<Prefix, ID> IdMapping;

    public DetailManager() {
        studentDetailsMap = FXCollections.observableMap(new HashMap<String, Object>());
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
            return TYPE.STUDENT_DETAILS;
        } else if (positions.get(0).getPrefix().equals(PREFIX_COURSEID)) {
            return TYPE.COURSE_DETAILS;
        }
        return TYPE.COURSE_DETAILS;
    }

    public void updateDetails(List<ArgumentTokenizer.PrefixPosition> positions,
                             List<ID> selectMetaDataIDs) throws CommandException {
        updateType(positions, selectMetaDataIDs);

        if (type.equals(TYPE.STUDENT_DETAILS)) {
            updateStudentDetailsMap(IdMapping.get(PREFIX_STUDENTID));
        } else if
    }

    // ################# Update student details map ##################################################3
    public void updateStudentDetailsMap(ID studentID) throws CommandException {
        Student student = (Student)model.get(studentID, Constants.ENTITY_TYPE.STUDENT);
        studentDetailsMap.put("details", student);
    }

    public void updateStudentDetailsMap(ID studentID, ID courseID) throws CommandException {

    }

    // #######################################################################################################


    // ###############  Update course details map ###########################################################

}
