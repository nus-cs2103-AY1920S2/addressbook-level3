package seedu.address.commons.core;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelCourse.CourseAddressBook;
import seedu.address.model.modelGeneric.AddressBookGeneric;
import seedu.address.model.modelGeneric.ModelObject;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.modelTeacher.Teacher;
import seedu.address.model.person.ID;

import java.util.UUID;

public class UuidManager {
    // If 10 continuous UUID creation has collision then possibly something wrong with system
    private static final Integer numAttemps = 10;

    public static boolean containsID(ModelObject object, ID id) {
        if (object instanceof Course) {
            return new AddressBookGeneric<Course>().containsID(id);
        } else if (object instanceof Student) {
            return new AddressBookGeneric<Student>().containsID(id);
        } else if (object instanceof Teacher) {
            return new AddressBookGeneric<Teacher>().containsID(id);
        }
        return new AddressBookGeneric<ModelObject>().containsID(id);
    }

    public static ID assignNewUUID(ModelObject object) throws ParseException {
        for (int i = 0; i < numAttemps; i++) {
            ID newID = new ID(UUID.randomUUID().toString());
            if (containsID(object, newID) == false) {
                return newID;
            }
        }
        throw new ParseException("Unable to obtain ID");
    }
}
