package seedu.address.logic.export;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.client.Client;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.ExerciseBuilder;

public class ExporterTest {

    private Client clientWithoutExercises = new ClientBuilder().build();
    private Client clientWithExercises = new ClientBuilder()
            .withExercisesInExerciseList(new ExerciseBuilder().build()).build();

    @Test
    public void exportExercisesAsCsv_clientWithoutExercises_throwsCommandException() {
        assertThrows(CommandException.class, () -> Exporter.exportExercisesAsCsv(clientWithoutExercises));
    }

    @Test
    public void exportExercisesAsCsv_clientWithExercises_success() {
        assertDoesNotThrow(() -> Exporter.exportExercisesAsCsv(clientWithExercises));
    }

}
