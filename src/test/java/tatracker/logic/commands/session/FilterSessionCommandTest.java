package tatracker.logic.commands.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static tatracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tatracker.logic.commands.session.FilterSessionCommand.MESSAGE_FILTERED_SESSIONS_SUCCESS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tatracker.logic.commands.CommandResult.Action;
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

    @BeforeEach
    public void setup() {
        model.updateFilteredSessionList(Model.PREDICATE_SHOW_ALL_SESSIONS);
        expectedModel.updateFilteredSessionList(Model.PREDICATE_SHOW_ALL_SESSIONS);
    }

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
        // String expectedMsg = Messages.getInvalidCommandMessage(FilterSessionCommand.DETAILS.getUsage());
        SessionPredicate predicate = new SessionPredicate();
        FilterSessionCommand command = new FilterSessionCommand(predicate);
        expectedModel.updateFilteredSessionList(predicate);
        assertCommandSuccess(command, model, MESSAGE_FILTERED_SESSIONS_SUCCESS, expectedModel, Action.FILTER_SESSION);
        assertEquals(Collections.emptyList(), model.getFilteredSessionList());
    }

    @Test
    public void execute_singleKeywords_multipleSessionsFound() {
        //module only
        String moduleCode = "CS3243";

        String expectedMsg = new StringBuilder(MESSAGE_FILTERED_SESSIONS_SUCCESS)
                .append("\nModule: ")
                .append(moduleCode)
                .toString();

        SessionPredicate predicate = new SessionPredicate();
        predicate.setModuleCode(moduleCode);

        FilterSessionCommand command = new FilterSessionCommand(predicate);

        // Session extraSession = new Session();
        // extraSession.setModuleCode(moduleCode);
        //
        // expectedModel.addSession(extraSession);
        expectedModel.updateFilteredSessionList(predicate);

        assertCommandSuccess(command, model, expectedMsg, expectedModel, Action.FILTER_SESSION);
        assertEquals(expectedModel.getFilteredSessionList(), model.getFilteredSessionList());

        // //date only
        // SessionPredicate predicate = new SessionPredicate();
        // predicate.setDate(LocalDate.of(2020, 05, 29));
        // FilterSessionCommand command = new FilterSessionCommand(predicate);
        // String dateFilter = predicate.getDate().map(dateFormat::format).orElse("");
        // if (predicate.getDate().isPresent()) {
        //     expectedMsg.append("\nDate: ").append(dateFilter);
        // }
        // expectedModel.updateFilteredSessionList(predicate);
        // assertCommandSuccess(command, model, expectedMsg.toString(), expectedModel);
        // assertEquals(getTypicalTaTrackerWithSessions(), model.getFilteredSessionList());
        //
        // //session type only
        // SessionPredicate predicate = new SessionPredicate();
        // predicate.setSessionType(SessionType.CONSULTATION);
        // FilterSessionCommand command = new FilterSessionCommand(predicate);
        // String sessionTypeFilter = predicate.getSessionType().map(SessionType::toString).orElse("");
        // if (predicate.getSessionType().isPresent()) {
        //     expectedMsg.append("\nType: ").append(sessionTypeFilter);
        // }
        // expectedModel.updateFilteredSessionList(predicate);
        // assertCommandSuccess(command, model, expectedMsg.toString(), expectedModel);
        // assertEquals(getTypicalTaTrackerWithSessions(), model.getFilteredSessionList());
    }

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
