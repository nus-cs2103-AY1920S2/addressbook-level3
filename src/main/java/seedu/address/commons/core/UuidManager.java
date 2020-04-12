package seedu.address.commons.core;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelGeneric.AddressBookGeneric;
import seedu.address.model.modelGeneric.ModelObject;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStudent.Student;

import java.util.Random;

public class UuidManager {
    // If 10 continuous UUID creation has collision then possibly something wrong with system
    private static final Integer numAttemps = 10;
    private static final Integer IDLength = 5;

    public static boolean containsID(ModelObject object, ID id) {
        if (object instanceof Course) {
            return new AddressBookGeneric<Course>().has(id);
        } else if (object instanceof Student) {
            return new AddressBookGeneric<Student>().has(id);
        } else if (object instanceof Staff) {
            return new AddressBookGeneric<Staff>().has(id);
        }
        return new AddressBookGeneric<ModelObject>().has(id);
    }

    public static ID assignNewUUID(ModelObject object) throws ParseException {
        Random random = new Random();
        for (int i = 0; i < numAttemps; i++) {
            ID newID = new ID(((Integer) random.nextInt((int) Math.pow(10, IDLength))).toString());
            if (containsID(object, newID) == false) {
                return newID;
            }
        }
        throw new ParseException("Unable to obtain ID");
    }
}
