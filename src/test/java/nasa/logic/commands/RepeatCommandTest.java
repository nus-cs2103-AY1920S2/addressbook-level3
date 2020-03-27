package nasa.logic.commands;

import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nasa.testutil.Assert.assertThrows;
import static nasa.testutil.TypicalModules.CS2103T;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nasa.commons.core.index.Index;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.activity.Name;

class RepeatCommandTest {

    Model model = new ModelManager();;

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RepeatCommand(null, null, null));
    }

    @Test
    public void constructor_scheduleIndexOutOfBounds() {

        Name name = new Name("Homework");

        Index correct_index = Index.fromZeroBased(1);
        Index wrong_index = Index.fromZeroBased(4);

        model.addModule(CS2103T);

        CommandResult expectedResult = new CommandResult(String.format("%s for %s %s %s", RepeatCommand.MESSAGE_SUCCESS,
                CS2103T.getModuleCode(), name, correct_index));

        RepeatCommand repeatCommand = new RepeatCommand(CS2103T.getModuleCode(), name, correct_index);

        RepeatCommand incorrect_repeatCommand = new RepeatCommand(CS2103T.getModuleCode(), name, wrong_index);


        assertEquals(expectedResult, repeatCommand.execute(model));

        assertThrows(IllegalArgumentException.class, () -> incorrect_repeatCommand.execute(model));
    }
}