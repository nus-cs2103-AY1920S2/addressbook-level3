package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.group.Group;
import seedu.address.model.person.ActivityList;
import seedu.address.model.person.Name;
import seedu.address.model.person.PlaceList;
import seedu.address.model.person.TimeList;


public class JsonAdaptedGroupTest {

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        Group g1 = new Group(new Name("soc friends"), new PlaceList(new ArrayList<String>()),
                new ActivityList(new ArrayList<String>()), new TimeList(new ArrayList<String>()));
        JsonAdaptedGroup group = new JsonAdaptedGroup(g1);
        assertEquals(g1, group.toModelType());
    }
}
