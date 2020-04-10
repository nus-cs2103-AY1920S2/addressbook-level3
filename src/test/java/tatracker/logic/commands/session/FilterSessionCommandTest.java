package tatracker.logic.commands.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tatracker.testutil.TypicalTaTracker.getTypicalTaTracker;
import static tatracker.testutil.TypicalTaTracker.getTypicalTaTrackerWithSessions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.model.Model;
import tatracker.model.ModelManager;
import tatracker.model.UserPrefs;
import tatracker.model.session.Session;
import tatracker.model.session.SessionPredicate;
import tatracker.model.session.SessionType;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterSessionCommand}.
 */
public class FilterSessionCommandTest {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy");

    private Model model = new ModelManager(getTypicalTaTracker(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTaTracker(), new UserPrefs());

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

    /*@Test
    public void execute_zeroKeywords_noSessionFound() {
        String expectedMsg = Messages.getInvalidCommandMessage(FilterSessionCommand.DETAILS.getUsage());
        SessionPredicate predicate = new SessionPredicate();
        FilterSessionCommand command = new FilterSessionCommand(predicate);
        expectedModel.updateFilteredSessionList(predicate);
        assertCommandSuccess(command, model, expectedMsg, expectedModel);
        //assertEquals(Collections.emptyList(), model.getFilteredSessionList());
    } */

    /*@Test
    public void execute_singleKeywords_multipleSessionsFound() {
        StringBuilder expectedMsg = new StringBuilder(FilterSessionCommand.MESSAGE_FILTERED_SESSIONS_SUCCESS);

        //module only
        SessionPredicate predicate = new SessionPredicate();
        predicate.setModuleCode("CS3243");
        FilterSessionCommand command = new FilterSessionCommand(predicate);
        String moduleFilter = predicate.getModuleCode().orElse("");
        if (predicate.getModuleCode().isPresent()) {
            expectedMsg.append("\nModule: ").append(moduleFilter);
        }
        expectedModel.updateFilteredSessionList(predicate);
        assertCommandSuccess(command, model, expectedMsg.toString(), expectedModel);
        assertEquals(getTypicalTaTrackerWithSessions(), model.getFilteredSessionList());

        //date only
        SessionPredicate predicate = new SessionPredicate();
        predicate.setDate(LocalDate.of(2020, 05, 29));
        FilterSessionCommand command = new FilterSessionCommand(predicate);
        String dateFilter = predicate.getDate().map(dateFormat::format).orElse("");
        if (predicate.getDate().isPresent()) {
            expectedMsg.append("\nDate: ").append(dateFilter);
        }
        expectedModel.updateFilteredSessionList(predicate);
        assertCommandSuccess(command, model, expectedMsg.toString(), expectedModel);
        assertEquals(getTypicalTaTrackerWithSessions(), model.getFilteredSessionList());

        //session type only
        SessionPredicate predicate = new SessionPredicate();
        predicate.setSessionType(SessionType.CONSULTATION);
        FilterSessionCommand command = new FilterSessionCommand(predicate);
        String sessionTypeFilter = predicate.getSessionType().map(SessionType::toString).orElse("");
        if (predicate.getSessionType().isPresent()) {
            expectedMsg.append("\nType: ").append(sessionTypeFilter);
        }
        expectedModel.updateFilteredSessionList(predicate);
        assertCommandSuccess(command, model, expectedMsg.toString(), expectedModel);
        assertEquals(getTypicalTaTrackerWithSessions(), model.getFilteredSessionList());
    } */

    /*@Test
    public void execute_multipleKeywords_multipleSessionsFound() {
        StringBuilder expectedMsg = new StringBuilder(FilterSessionCommand.MESSAGE_FILTERED_SESSIONS_SUCCESS);

        //module, date and sessiontype -> sessions with either one of the keyword will be shown
        SessionPredicate predicate = new SessionPredicate();
        predicate.setModuleCode("CS3243");
        predicate.setDate(LocalDate.of(2020, 05, 29));
        predicate.setSessionType(SessionType.LAB);
        FilterSessionCommand command = new FilterSessionCommand(predicate);
        String moduleFilter = predicate.getModuleCode().orElse("");
        String dateFilter = predicate.getDate().map(dateFormat::format).orElse("");
        if (predicate.getDate().isPresent()) {
            expectedMsg.append("\nDate: ").append(dateFilter);
        }
        if (predicate.getModuleCode().isPresent()) {
            expectedMsg.append("\nModule: ").append(moduleFilter);
        }
        String sessionTypeFilter = predicate.getSessionType().map(SessionType::toString).orElse("");
        if (predicate.getSessionType().isPresent()) {
            expectedMsg.append("\nType: ").append(sessionTypeFilter);
        }
        expectedModel.updateFilteredSessionList(predicate);
        assertCommandSuccess(command, model, expectedMsg.toString(), expectedModel);
        assertEquals(getTypicalTaTrackerWithSessions(), model.getFilteredSessionList());

        //module and date
        predicate = new SessionPredicate();
        predicate.setModuleCode("CS3243");
        predicate.setDate(LocalDate.of(2020, 05, 29));
        predicate.setSessionType(SessionType.LAB);
        command = new FilterSessionCommand(predicate);
        moduleFilter = predicate.getModuleCode().orElse("");
        dateFilter = predicate.getDate().map(dateFormat::format).orElse("");
        if (predicate.getDate().isPresent()) {
            expectedMsg.append("\nDate: ").append(dateFilter);
        }
        if (predicate.getModuleCode().isPresent()) {
            expectedMsg.append("\nModule: ").append(moduleFilter);
        }
        expectedModel.updateFilteredSessionList(predicate);
        assertCommandSuccess(command, model, expectedMsg.toString(), expectedModel);
        assertEquals(getTypicalTaTrackerWithSessions(), model.getFilteredSessionList());

        //module and type
        predicate = new SessionPredicate();
        predicate.setModuleCode("CS3243");
        predicate.setDate(LocalDate.of(2020, 05, 29));
        predicate.setSessionType(SessionType.LAB);
        command = new FilterSessionCommand(predicate);
        moduleFilter = predicate.getModuleCode().orElse("");
        if (predicate.getModuleCode().isPresent()) {
            expectedMsg.append("\nModule: ").append(moduleFilter);
        }
        sessionTypeFilter = predicate.getSessionType().map(SessionType::toString).orElse("");
        if (predicate.getSessionType().isPresent()) {
            expectedMsg.append("\nType: ").append(sessionTypeFilter);
        }
        expectedModel.updateFilteredSessionList(predicate);
        assertCommandSuccess(command, model, expectedMsg.toString(), expectedModel);
        assertEquals(getTypicalTaTrackerWithSessions(), model.getFilteredSessionList());

        //date and sessiontype
        predicate = new SessionPredicate();
        predicate.setModuleCode("CS3243");
        predicate.setDate(LocalDate.of(2020, 05, 29));
        predicate.setSessionType(SessionType.LAB);
        command = new FilterSessionCommand(predicate);
        dateFilter = predicate.getDate().map(dateFormat::format).orElse("");
        if (predicate.getDate().isPresent()) {
            expectedMsg.append("\nDate: ").append(dateFilter);
        }
        sessionTypeFilter = predicate.getSessionType().map(SessionType::toString).orElse("");
        if (predicate.getSessionType().isPresent()) {
            expectedMsg.append("\nType: ").append(sessionTypeFilter);
        }
        expectedModel.updateFilteredSessionList(predicate);
        assertCommandSuccess(command, model, expectedMsg.toString(), expectedModel);
        assertEquals(getTypicalTaTrackerWithSessions(), model.getFilteredSessionList());
    } */
}
