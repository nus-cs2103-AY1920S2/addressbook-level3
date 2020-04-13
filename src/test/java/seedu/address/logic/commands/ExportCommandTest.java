package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

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
        ExportCommand exportCommand = new ExportCommand();

        assertCommandFailure(exportCommand, model, ExportCommand.MESSAGE_CLIENT_NOT_IN_VIEW);
    }

    @Test
    public void execute_clientWithoutExercises_throwsCommandException() {
        model.setClientInView(clientWithoutExercises);

        ExportCommand exportCommand = new ExportCommand();

        assertCommandFailure(exportCommand, model, ExportCommand.MESSAGE_NO_EXERCISES);
    }

    @Test
    public void execute_clientWithExercises_success() {
        model.setClientInView(clientWithExercises);
        expectedModel.setClientInView(clientWithExercises);

        CommandResult expectedCommandResult = new CommandResult(
                String.format(ExportCommand.MESSAGE_SUCCESS, clientWithExercisesCsvFileName));

        assertCommandSuccess(new ExportCommand(), model, expectedCommandResult, expectedModel);
    }
}
