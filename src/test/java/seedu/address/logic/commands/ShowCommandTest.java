package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CourseManager;
import seedu.address.model.ModuleManager;
import seedu.address.model.ProfileManager;
import seedu.address.model.profile.Name;

public class ShowCommandTest {

    // No profile has been added, user inputs "show n/John" and "show n/123"
    @Test
    public void execute_nameNoProfile_throwsCommandException() {
        ShowCommand showCommandJohn = new ShowCommand(new Name("John"));
        assertThrows(CommandException.class, "Profile does not exist.", () ->
                showCommandJohn.execute(new ProfileManager(), new CourseManager(), new ModuleManager()));
        ShowCommand showCommand123 = new ShowCommand(new Name("123"));
        assertThrows(CommandException.class, "Profile does not exist.", () ->
                showCommand123.execute(new ProfileManager(), new CourseManager(), new ModuleManager()));
    }

    // No profile has been added, user inputs "show n/"
    // Note that the CommandException would have been thrown in ShowCommandParser
    @Test
    public void constructor_nullNameNoProfile_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ShowCommand(new Name(null)));
    }

    // Valid course, first letter of each word capitalised, e.g. show c/Computer Science
    // Require CourseManager stub (to be implemented)
    /*@Test
    public void execute_validCourseName_success() {
        CourseName courseNameFirstLetterCap = null;
        CourseName courseNameAllCap = null;
        CourseName courseNameNoCap = null;
        try {
            courseNameFirstLetterCap = new CourseName("Computer Science");
            courseNameAllCap = new CourseName("COMPUTER SCIENCE");
            courseNameNoCap = new CourseName("computer science");
        } catch (ParseException e) {
            fail();
        }
        ShowCommand showCommandFirstLetterCap = new ShowCommand(courseNameFirstLetterCap);
        ShowCommand showCommandAllCap = new ShowCommand(courseNameAllCap);
        ShowCommand showCommandNoCap = new ShowCommand(courseNameNoCap);
        try {
            System.out.println(showCommandFirstLetterCap.execute(
                    new ProfileManager(), new CourseManager(), new ModuleManager()).toString());
            //assertTrue(showCommandFirstLetterCap.execute(
                    new ProfileManager(), new CourseManager(), new ModuleManager()).toString());
        } catch (CommandException e) {
            fail();
        }
    }*/


}
