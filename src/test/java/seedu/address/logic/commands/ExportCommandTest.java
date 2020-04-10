package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.client.Client;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.ExerciseBuilder;

public class ExportCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    private Client clientWithoutExercises = new ClientBuilder().build();
    private Client clientWithExercises = new ClientBuilder()
            .withExercisesInExerciseList(new ExerciseBuilder().build()).build();
    private String clientWithExercisesCsvFileName = clientWithExercises.getName().fullName + ".csv";

    @Test
    public void execute_noClientInView_throwsCommandException() {
        assertThrows(CommandException.class, () -> new ExportCommand().execute(model));
    }

    @Test
    public void execute_clientHasNoExercises_throwsCommandException() {
        model.setClientInView(clientWithoutExercises);
        assertThrows(CommandException.class, () -> new ExportCommand().execute(model));
    }

    @Test
    public void execute_clientWithExercises_success() {
        model.setClientInView(clientWithExercises);
        expectedModel.setClientInView(clientWithExercises);

        CommandResult expectedCommandResult = new CommandResult(
                String.format(MESSAGE_SUCCESS, clientWithExercisesCsvFileName));

        assertCommandSuccess(new ExportCommand(), model, expectedCommandResult, expectedModel);
    }
}
