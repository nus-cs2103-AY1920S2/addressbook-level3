//@@author chuayijing
package tatracker.logic.commands.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static tatracker.logic.commands.CommandTestUtil.assertFilterSessionCommandSuccess;
import static tatracker.logic.commands.session.FilterSessionCommand.MESSAGE_FILTERED_SESSIONS_SUCCESS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
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
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy");

    private Model model = new ModelManager(SampleDataUtil.getSampleTaTracker(), new UserPrefs());
    private Model expectedModel = new ModelManager(SampleDataUtil.getSampleTaTracker(), new UserPrefs());

    //@@ PotatoCombat
    @BeforeEach
    public void setup() {
        model.updateFilteredSessionList(Model.PREDICATE_SHOW_ALL_SESSIONS);
        expectedModel.updateFilteredSessionList(Model.PREDICATE_SHOW_ALL_SESSIONS);
    }

    //@@ chuayijing
    @Test
    public void equals() {
        SessionPredicate firstPredicate = new SessionPredicate();
        firstPredicate.setDate(LocalDate.of(2020, 5, 20));
        firstPredicate.setModuleCode("CS3243");
        firstPredicate.setSessionType(SessionType.LAB);

        SessionPredicate secondPredicate = new SessionPredicate();
        secondPredicate.setDate(LocalDate.of(2020, 5, 30));
        secondPredicate.setModuleCode("CS2103T");
        secondPredicate.setSessionType(SessionType.GRADING);

        FilterSessionCommand filterFirstCommand = new FilterSessionCommand(firstPredicate);
        FilterSessionCommand filterSecondCommand = new FilterSessionCommand(secondPredicate);

        // same object -> returns true
        assertEquals(filterFirstCommand, filterFirstCommand);

        // same values -> returns true
        FilterSessionCommand findFirstCommandCopy = new FilterSessionCommand(firstPredicate);
        assertEquals(filterFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, filterFirstCommand);

        // null -> returns false
        assertNotEquals(null, filterFirstCommand);

        // different params -> returns false
        assertNotEquals(filterFirstCommand, filterSecondCommand);
    }

    @Test
    public void execute_zeroKeywords_noSessionFound() {
        SessionPredicate predicate = new SessionPredicate();
        FilterSessionCommand command = new FilterSessionCommand(predicate);
        expectedModel.updateFilteredSessionList(predicate);
        assertFilterSessionCommandSuccess(command, model, MESSAGE_FILTERED_SESSIONS_SUCCESS, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSessionList());
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
