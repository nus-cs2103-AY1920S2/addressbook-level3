package seedu.address.logic.commands.interviewsubcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERVIEW;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalInternshipApplications.getTypicalInternshipDiaryWithInterviews;
import static seedu.address.testutil.TypicalInterviews.NUS;
import static seedu.address.testutil.TypicalInterviews.ONLINE;
import static seedu.address.testutil.TypicalInterviews.ORCHARD_TOWER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.interview.Interview;
import seedu.address.testutil.InterviewBuilder;

/**
 * Contains integration tests with model and unit test for {@code InterviewAddCommand}.
 */
public class InterviewAddCommandTest {

    private Model model = new ModelManager(getTypicalInternshipDiaryWithInterviews(), new UserPrefs());

    @Test
    public void constructor_nullInterview_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new InterviewAddCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, null));
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new InterviewAddCommand(null, new InterviewBuilder().build()));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        InterviewAddCommand command = new InterviewAddCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
                new InterviewBuilder().build());
        assertThrows(NullPointerException.class, () ->
                command.execute(null));
    }

    @Test
    public void execute_validArguments_addSuccess() throws CommandException {
        Interview interview = new InterviewBuilder(ORCHARD_TOWER).build();
        InterviewAddCommand command = new InterviewAddCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, interview);
        CommandResult result = command.execute(model);
        assertEquals(String.format(InterviewAddCommand.MESSAGE_SUCCESS, interview), result.getFeedbackToUser());
        InternshipApplication internshipApplication = model.getFilteredInternshipApplicationList()
                .get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        assertTrue(internshipApplication.hasInterview(interview));
    }

    @Test
    public void execute_duplicateInterview_throwsCommandException() {
        InternshipApplication internshipApplication = model.getFilteredInternshipApplicationList()
                .get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        Interview interview = internshipApplication
                .getInterview(INDEX_FIRST_INTERVIEW.getZeroBased());
        InterviewAddCommand command = new InterviewAddCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, interview);
        assertThrows(CommandException.class,
                String.format(InterviewAddCommand.MESSAGE_DUPLICATE_INTERVIEW, internshipApplication), () ->
                        command.execute(model));
    }

    @Test
    public void equals() {
        InterviewAddCommand command = new InterviewAddCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, NUS);
        InterviewAddCommand copy = new InterviewAddCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, NUS);

        //same object -> returns true
        assertTrue(command.equals(command));

        //different object but same variables -> returns true
        assertTrue(command.equals(copy));

        //null -> returns false
        assertFalse(command.equals(null));

        //different type -> returns false
        assertFalse(command.equals(5));

        //different index -> returns false
        copy = new InterviewAddCommand(INDEX_SECOND_INTERNSHIP_APPLICATION, NUS);
        assertFalse(command.equals(copy));

        //different interview -> returns false
        copy = new InterviewAddCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, ONLINE);
        assertFalse(command.equals(copy));
    }
}
