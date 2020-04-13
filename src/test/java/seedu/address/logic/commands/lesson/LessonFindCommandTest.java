package seedu.address.logic.commands.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import java.time.DayOfWeek;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code LessonFindCommand}.
 */
public class LessonFindCommandTest {
    private Model model = new ModelManager(getTypicalModManager(), new UserPrefs());

    @Test
    public void execute_validDayLessonList_success() throws CommandException {
        DayOfWeek day = DayOfWeek.WEDNESDAY;
        LessonFindCommand command = new LessonFindCommand(day);
        CommandResult result = command.execute(model);
        String resultString = result.getFeedbackToUser();
        String expectedResult = "Classes on " + day.toString() + "\n" + "\u2022 "
                + model.findLessonByDay(DayOfWeek.WEDNESDAY).get(0).toString() + "\n";
        assertEquals(resultString, expectedResult);
    }

    @Test
    public void execute_dayWithNoLessonLessonList_noLessonsFound() throws CommandException {
        String result = new LessonFindCommand(DayOfWeek.SATURDAY).execute(model).getFeedbackToUser();
        String expectedResult = "No class on SATURDAY";
        assertEquals(result, expectedResult);
    }

    @Test
    public void equals() {
        LessonFindCommand firstCommand = new LessonFindCommand(DayOfWeek.WEDNESDAY);
        LessonFindCommand secondCommand = new LessonFindCommand(DayOfWeek.FRIDAY);

        // same object should return true
        assertTrue(firstCommand.equals(firstCommand));

        // same values should return true
        LessonFindCommand firstCommandCopy = new LessonFindCommand(DayOfWeek.WEDNESDAY);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types should return false
        assertFalse(firstCommand.equals(1));

        // null should return false
        assertFalse(firstCommand.equals(null));

        // different day should return false
        assertFalse(firstCommand.equals(secondCommand));
    }
}
