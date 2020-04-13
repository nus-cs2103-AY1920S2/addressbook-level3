package nasa.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

public class ExportCalendarCommandTest {

    @Test
    public void equals() {

        // null file paths
        ExportCalendarCommand command = new ExportCalendarCommand(null);
        ExportCalendarCommand otherCommand = new ExportCalendarCommand(null);
        assertEquals(command, otherCommand);

        // same file paths
        command = new ExportCalendarCommand(Path.of("./Calendar"));
        otherCommand = new ExportCalendarCommand(Path.of("./Calendar"));
        assertEquals(command, otherCommand);

        // different file paths
        command = new ExportCalendarCommand(Path.of("./Calendar"));
        otherCommand = new ExportCalendarCommand(Path.of("./Test"));
        assertNotEquals(command, otherCommand);
    }
}
