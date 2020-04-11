package tatracker.logic.commands.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.logic.commands.CommandTestUtil.assertFilterSessionCommandSuccess;
import static tatracker.logic.commands.session.FilterSessionCommand.MESSAGE_FILTERED_SESSIONS_SUCCESS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import tatracker.model.Model;
import tatracker.model.ModelManager;
import tatracker.model.UserPrefs;
import tatracker.model.session.SessionPredicate;
import tatracker.model.session.SessionType;
import tatracker.model.util.SampleDataUtil;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterSessionCommand}.
 */
public class FilterSessionCommandTest {

    private Model model = new ModelManager(SampleDataUtil.getSampleTaTracker(), new UserPrefs());
    private Model expectedModel = new ModelManager(SampleDataUtil.getSampleTaTracker(), new UserPrefs());
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy");

    @Test
    public void equals() {
        SessionPredicate firstPredicate = new SessionPredicate();
        firstPredicate.setDate(LocalDate.of(2020, 05, 20));
        firstPredicate.setModuleCode("CS3243");
        firstPredicate.setSessionType(SessionType.LAB);

        SessionPredicate secondPredicate = new SessionPredicate();
        secondPredicate.setDate(LocalDate.of(2020, 05, 30));
        secondPredicate.setModuleCode("CS2103T");
        secondPredicate.setSessionType(SessionType.GRADING);

        FilterSessionCommand filterFirstCommand = new FilterSessionCommand(firstPredicate);
        FilterSessionCommand filterSecondCommand = new FilterSessionCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterSessionCommand findFirstCommandCopy = new FilterSessionCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different params -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noSessionFound() {
        SessionPredicate predicate = new SessionPredicate();
        FilterSessionCommand command = new FilterSessionCommand(predicate);
        expectedModel.updateFilteredSessionList(predicate);
        assertFilterSessionCommandSuccess(command, model, MESSAGE_FILTERED_SESSIONS_SUCCESS, expectedModel);
    }

    @Test
    public void execute_singleKeywords_multipleSessionsFound() {

        //module only
        String moduleCode = "CS3243";

        String expectedMsg = new StringBuilder(FilterSessionCommand.MESSAGE_FILTERED_SESSIONS_SUCCESS)
                .append("\nModule: ")
                .append(moduleCode)
                .toString();

        SessionPredicate predicate = new SessionPredicate();
        predicate.setModuleCode(moduleCode);

        FilterSessionCommand command = new FilterSessionCommand(predicate);
        expectedModel.updateFilteredSessionList(predicate);

        assertFilterSessionCommandSuccess(command, model, expectedMsg, expectedModel);
        assertEquals(expectedModel.getFilteredSessionList(), model.getFilteredSessionList());

        //date only
        LocalDate date = LocalDate.of(2020, 05, 29);
        String dateString = dateFormat.format(date);

        expectedMsg = new StringBuilder(MESSAGE_FILTERED_SESSIONS_SUCCESS)
                .append("\nDate: ")
                .append(dateString)
                .toString();

        predicate = new SessionPredicate();
        predicate.setDate(date);
        command = new FilterSessionCommand(predicate);
        expectedModel.updateFilteredSessionList(predicate);
        assertFilterSessionCommandSuccess(command, model, expectedMsg, expectedModel);
        assertEquals(expectedModel.getFilteredSessionList(), model.getFilteredSessionList());

        //session type only
        String type = SessionType.CONSULTATION.toString();

        expectedMsg = new StringBuilder(MESSAGE_FILTERED_SESSIONS_SUCCESS)
                .append("\nType: ")
                .append(type)
                .toString();

        predicate = new SessionPredicate();
        predicate.setSessionType(SessionType.CONSULTATION);
        command = new FilterSessionCommand(predicate);
        String sessionTypeFilter = predicate.getSessionType().map(SessionType::toString).orElse("");
        expectedModel.updateFilteredSessionList(predicate);
        assertFilterSessionCommandSuccess(command, model, expectedMsg, expectedModel);
        assertEquals(expectedModel.getFilteredSessionList(), model.getFilteredSessionList());
    }

    @Test
    public void execute_multipleKeywords_multipleSessionsFound() {

        //module, date and sessiontype -> sessions with either one of the keyword will be shown
        String module = "CS3243";

        LocalDate date = LocalDate.of(2020, 05, 29);
        String dateString = dateFormat.format(date);

        String type = SessionType.LAB.toString();

        String expectedMsg = new StringBuilder(FilterSessionCommand.MESSAGE_FILTERED_SESSIONS_SUCCESS)
                .append("\nDate: ").append(dateString)
                .append("\nModule: ").append(module)
                .append("\nType: ").append(type)
                .toString();

        SessionPredicate predicate = new SessionPredicate();
        predicate.setModuleCode("CS3243");
        predicate.setDate(date);
        predicate.setSessionType(SessionType.LAB);
        FilterSessionCommand command = new FilterSessionCommand(predicate);
        expectedModel.updateFilteredSessionList(predicate);
        assertFilterSessionCommandSuccess(command, model, expectedMsg, expectedModel);
        assertEquals(expectedModel.getFilteredSessionList(), model.getFilteredSessionList());


        //module and date
        predicate = new SessionPredicate();
        predicate.setModuleCode("CS3243");
        predicate.setDate(date);
        command = new FilterSessionCommand(predicate);

        expectedMsg = new StringBuilder(FilterSessionCommand.MESSAGE_FILTERED_SESSIONS_SUCCESS)
                .append("\nDate: ").append(dateString)
                .append("\nModule: ").append(module)
                .toString();

        expectedModel.updateFilteredSessionList(predicate);
        assertFilterSessionCommandSuccess(command, model, expectedMsg, expectedModel);
        assertEquals(expectedModel.getFilteredSessionList(), model.getFilteredSessionList());


        //module and type
        predicate = new SessionPredicate();
        predicate.setModuleCode("CS3243");
        predicate.setSessionType(SessionType.LAB);
        command = new FilterSessionCommand(predicate);

        expectedMsg = new StringBuilder(FilterSessionCommand.MESSAGE_FILTERED_SESSIONS_SUCCESS)
                .append("\nModule: ").append(module)
                .append("\nType: ").append(type)
                .toString();

        expectedModel.updateFilteredSessionList(predicate);
        assertFilterSessionCommandSuccess(command, model, expectedMsg, expectedModel);
        assertEquals(expectedModel.getFilteredSessionList(), model.getFilteredSessionList());


        //date and sessiontype
        predicate = new SessionPredicate();
        predicate.setDate(date);
        predicate.setSessionType(SessionType.LAB);
        command = new FilterSessionCommand(predicate);

        expectedMsg = new StringBuilder(FilterSessionCommand.MESSAGE_FILTERED_SESSIONS_SUCCESS)
                .append("\nDate: ").append(dateString)
                .append("\nType: ").append(type)
                .toString();

        expectedModel.updateFilteredSessionList(predicate);
        assertFilterSessionCommandSuccess(command, model, expectedMsg, expectedModel);
        assertEquals(expectedModel.getFilteredSessionList(), model.getFilteredSessionList());

    }
}
