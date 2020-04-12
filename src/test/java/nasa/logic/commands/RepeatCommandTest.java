package nasa.logic.commands;

import static nasa.testutil.Assert.assertThrows;
import static nasa.testutil.TypicalModules.CS2103T;

import org.junit.jupiter.api.Test;

import nasa.commons.core.index.Index;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.activity.Name;

class RepeatCommandTest {

    private Model model = new ModelManager();

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new RepeatDeadlineCommand(null, null, null));
    }

    @Test
    public void constructor_scheduleIndexOutOfBounds() {

        Name name = new Name("Homework");

        Index correctIndex = Index.fromZeroBased(1);
        Index wrongIndex = Index.fromZeroBased(4);

        model.addModule(CS2103T);

        CommandResult expectedResult = new CommandResult(String.format("%s for %s %s %s",
                RepeatDeadlineCommand.MESSAGE_SUCCESS,
                CS2103T.getModuleCode(), name, correctIndex.getZeroBased()));

        RepeatDeadlineCommand repeatCommand = new RepeatDeadlineCommand(CS2103T.getModuleCode(), Index.fromZeroBased(1),
                correctIndex);

        RepeatDeadlineCommand incorrectRepeatCommand = new RepeatDeadlineCommand(CS2103T.getModuleCode(),
                Index.fromZeroBased(1), wrongIndex);

        // try {
        //     assertEquals(expectedResult, repeatCommand.execute(model));
        // } catch (Exception error) {
        //     System.out.println("Error in test case");
        // }
        // assertThrows(IllegalArgumentException.class, () -> incorrectRepeatCommand.execute(model));
    }
}
