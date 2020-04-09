package tatracker.logic.commands.session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import tatracker.model.session.Session;
import tatracker.model.session.SessionType;
import tatracker.testutil.sessions.SessionBuilder;

public class AddSessionCommandTest {

    private static final String DEFAULT_START = LocalDateTime
            .of(2020, 05, 20, 17, 30).toString();
    private static final String DEFAULT_END = LocalDateTime
            .of(2020, 05, 20, 19, 30).toString();
    private static final String DEFAULT_MODULE = "CS2103T";
    private static final String DEFAULT_TYPE = SessionType.TUTORIAL.toString();
    private static final String DEFAULT_DESCRIPTION = "finishes his tutorial";
    private static final int DEFAULT_RECURRING = 2;

    @Test
    public void constructor_nullSession_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddSessionCommand(null));
    }

    /* @Test
    public void parse_invalidValue_failure() throws ParseException {

        //start end after end time
        Session invalidSession = new SessionBuilder()
                .withModule(DEFAULT_MODULE)
                .withStartTime(DEFAULT_END)
                .withEndTime(DEFAULT_START)
                .withRecurring(DEFAULT_RECURRING)
                .withSessionType(DEFAULT_TYPE)
                .withDescription(DEFAULT_DESCRIPTION).build();

        AddSessionCommand addSessionCommand = new AddSessionCommand(invalidSession);
        ModelStub modelStub = new ModelStubWithSession(invalidSession);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_SESSION_TIMES, ()
                -> addSessionCommand.execute(modelStub));
    } */

    /*@Test
    public void execute_sessionAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingSessionAdded modelStub = new ModelStubAcceptingSessionAdded();
        Session validSession = new SessionBuilder().build();

        CommandResult commandResult = new AddSessionCommand(validSession).execute(modelStub);

        assertEquals(String.format(AddSessionCommand.MESSAGE_SUCCESS, validSession), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validSession), modelStub.sessionsAdded);
    } */

    /* @Test
    public void execute_duplicateSession_throwsCommandException() {
        Session validSession = new SessionBuilder().build();
        AddSessionCommand addSessionCommand = new AddSessionCommand(validSession);
        ModelStub modelStub = new ModelStubWithSession(validSession);

        assertThrows(CommandException.class, AddSessionCommand.MESSAGE_DUPLICATE_SESSION, ()
                -> addSessionCommand.execute(modelStub));
    } */

    @Test
    public void equals() {
        Session first = new SessionBuilder().withModule("CS3243").build();
        Session second = new SessionBuilder().withModule("CS2103T").build();
        AddSessionCommand addCS3243Command = new AddSessionCommand(first);
        AddSessionCommand addCS2103TCommand = new AddSessionCommand(second);

        // same object -> returns true
        assertTrue(addCS2103TCommand.equals(addCS2103TCommand));

        // same values -> returns true
        AddSessionCommand addCS2103TCommandCopy = new AddSessionCommand(second);
        assertTrue(addCS2103TCommand.equals(addCS2103TCommandCopy));

        // different types -> returns false
        assertFalse(addCS2103TCommand.equals(1));

        // null -> returns false
        assertFalse(addCS2103TCommand.equals(null));

        // different food -> returns false
        assertFalse(addCS2103TCommand.equals(addCS3243Command));
    }
}
