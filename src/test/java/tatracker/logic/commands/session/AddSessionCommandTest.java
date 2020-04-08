package tatracker.logic.commands.session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import tatracker.model.session.Session;
import tatracker.testutil.sessions.SessionBuilder;

public class AddSessionCommandTest {

    @Test
    public void constructor_nullSession_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddSessionCommand(null));
    }

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
        Session CS3243 = new SessionBuilder().withModule("CS3243").build();
        Session CS2103T = new SessionBuilder().withModule("CS2103T").build();
        AddSessionCommand addCS3243Command = new AddSessionCommand(CS3243);
        AddSessionCommand addCS2103TCommand = new AddSessionCommand(CS2103T);

        // same object -> returns true
        assertTrue(addCS2103TCommand.equals(addCS2103TCommand));

        // same values -> returns true
        AddSessionCommand addCS2103TCommandCopy = new AddSessionCommand(CS2103T);
        assertTrue(addCS2103TCommand.equals(addCS2103TCommandCopy));

        // different types -> returns false
        assertFalse(addCS2103TCommand.equals(1));

        // null -> returns false
        assertFalse(addCS2103TCommand.equals(null));

        // different food -> returns false
        assertFalse(addCS2103TCommand.equals(addCS3243Command));
    }
}
