//@@author chuayijing

package tatracker.logic.commands.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static tatracker.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.ModelStub;
import tatracker.model.ModelStub.ModelStubAcceptingSessionAdded;
import tatracker.model.ModelStub.ModelStubWithSession;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
import tatracker.testutil.sessions.SessionBuilder;

public class AddSessionCommandTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.of(2020, 5, 20);
    private static final LocalTime DEFAULT_START = LocalTime.of(17, 30);
    private static final LocalTime DEFAULT_END = LocalTime.of(19, 30);
    private static final String DEFAULT_MODULE = "CS2103T";
    private static final String DEFAULT_TYPE = "tutorial";
    private static final String DEFAULT_DESCRIPTION = "finishes his tutorial";
    private static final int DEFAULT_RECURRING = 2;

    private static final Module CS2103T = new Module(SessionBuilder.DEFAULT_MODULE, "Software Engineering");

    @Test
    public void constructor_nullSession_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddSessionCommand(null));
    }

    @Test
    public void parse_invalidValue_failure() {

        //start end after end time
        Session invalidSession = new SessionBuilder()
                .withModule(DEFAULT_MODULE)
                .withDate(DEFAULT_DATE)
                .withStartTime(DEFAULT_END)
                .withEndTime(DEFAULT_START)
                .withRecurring(DEFAULT_RECURRING)
                .withSessionType(DEFAULT_TYPE)
                .withDescription(DEFAULT_DESCRIPTION).build();

        AddSessionCommand addSessionCommand = new AddSessionCommand(invalidSession);
        ModelStub modelStub = new ModelStubWithSession(invalidSession);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_SESSION_TIMES, ()
            -> addSessionCommand.execute(modelStub));
    }

    @Test
    public void execute_sessionAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingSessionAdded modelStub = new ModelStubAcceptingSessionAdded();
        modelStub.addModule(CS2103T);
        Session validSession = new SessionBuilder().build();

        CommandResult commandResult = new AddSessionCommand(validSession).execute(modelStub);

        assertEquals(String.format(AddSessionCommand.MESSAGE_ADD_SESSION_SUCCESS,
                validSession.getMinimalDescription()), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validSession), modelStub.sessionsAdded);
    }

    @Test
    public void execute_duplicateSession_throwsCommandException() {
        Session validSession = new SessionBuilder().build();
        AddSessionCommand addSessionCommand = new AddSessionCommand(validSession);

        ModelStub modelStub = new ModelStubWithSession(validSession);

        assertThrows(CommandException.class, Messages.MESSAGE_DUPLICATE_SESSION, ()
            -> addSessionCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Session first = new SessionBuilder().withModule("CS3243").build();
        Session second = new SessionBuilder().withModule("CS2103T").build();
        AddSessionCommand addCS3243Command = new AddSessionCommand(first);
        AddSessionCommand addCS2103TCommand = new AddSessionCommand(second);

        // same object -> returns true
        assertEquals(addCS2103TCommand, addCS2103TCommand);

        // same values -> returns true
        AddSessionCommand addCS2103TCommandCopy = new AddSessionCommand(second);
        assertEquals(addCS2103TCommand, addCS2103TCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addCS2103TCommand);

        // null -> returns false
        assertNotEquals(null, addCS2103TCommand);

        // different food -> returns false
        assertNotEquals(addCS2103TCommand, addCS3243Command);
    }
}
