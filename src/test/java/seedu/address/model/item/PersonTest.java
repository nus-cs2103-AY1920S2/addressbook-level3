package seedu.address.model.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.model.util.ItemUtil.DEFAULT_USER;

import org.junit.jupiter.api.Test;

import seedu.address.model.item.field.Cap;
import seedu.address.model.item.field.Description;
import seedu.address.model.item.field.DisplayPicture;
import seedu.address.model.item.field.Email;
import seedu.address.model.item.field.Github;
import seedu.address.model.item.field.Major;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Phone;
import seedu.address.model.item.field.Time;
import seedu.address.model.item.field.University;

public class PersonTest {
    private Person defaultUser = DEFAULT_USER;
    private Person user1 = new Person(new DisplayPicture("/images/Duke.png"),
            new Name("Your Name"), new Description("Your Description"), new Phone("000"),
            new Email("youremail@gmail.com"), new Github("yourgithub"), new University("Your University"),
            new Major("Your Major"), new Time("12-9999"), new Time("12-9999"), new Cap("0.0 5.0"));
    private Person user2 = new Person(new DisplayPicture("/images/Duke.png"),
            new Name("My Name"), new Description("My Description"), new Phone("000"), new Email("myemail@gmail.com"),
            new Github("mygithub"), new University("My University"), new Major("My Major"), new Time("12-9999"),
            new Time("12-9999"), new Cap("0.0 5.0"));

    @Test
    public void testToString() {
        assertEquals(user1.toString(), defaultUser.toString());
        assertNotEquals(user1.toString(), user2.toString());
    }

    @Test
    public void equal() {
        assertNotEquals(user1, null);
        assertNotEquals(user1, 5);
        assertNotEquals(user1, user2);
        assertEquals(user1, defaultUser);
    }
}
