package seedu.address.logic.commands.tutorials;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CSV_FILEPATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.event.tutorial.TutorialExportCsv;

/**
 * Exports attendance of a tutorial into a csv file specified by the user
 */
public class ExportTutorialAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "exportAttendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the attendance of all enrolled students"
        + "in a tutorial group into a CSV file in the path stated by the user.\n"
        + "Parameters: "
        + PREFIX_TUTORIAL_INDEX + "TUTORIAL_INDEX (must be a positive integer) "
        + PREFIX_CSV_FILEPATH + "CSV_FILEPATH (ends in .csv)\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TUTORIAL_INDEX + "1 "
        + PREFIX_CSV_FILEPATH + "../attendance.csv";

    public static final String MESSAGE_SUCCESS = "Attendance from tutorial %1$s exported to %2$s. ";

    private final Index tutorialIndex;
    private final Path csvFilePath;

    /**
     * Creates a CopyTutorialEmailsCommand to copy emails of student in {@code tutorial} to clipboard.
     */
    public ExportTutorialAttendanceCommand(Index tutorialIndex, Path csvFilePath) {
        this.tutorialIndex = tutorialIndex;
        this.csvFilePath = csvFilePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        if (tutorialIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
        }

        Tutorial target = lastShownList.get(tutorialIndex.getZeroBased());
        try {
            new TutorialExportCsv(target, csvFilePath).exportAttendance();
        } catch (IOException exception) {
            throw new CommandException("There was a problem writing into the file path specified");
        }

        return new CommandResult(String.format(
            MESSAGE_SUCCESS,
            target.getModCode() + " " + target.getTutorialName(),
            csvFilePath));
    }
}
