package seedu.address.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import seedu.address.commons.core.BaseManager;
import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.person.ID;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static seedu.address.logic.parser.CliSyntax.*;

// TODO: Think of better name?
public class DetailManager extends BaseManager {
    enum TYPE {
        STUDENT_DETAILS,
        COURSE_DETAILS
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
        IdMapping = new HashMap<Prefix, ID>();
        instance = this;
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
        }
    }

    public void updateStudentDetailsMap(ID studentID) throws CommandException {
        Student student = (Student)model.get(studentID, Constants.ENTITY_TYPE.STUDENT);
        studentDetailsMap.put("details", student);
    }
}
