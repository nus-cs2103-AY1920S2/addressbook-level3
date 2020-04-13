package seedu.address.logic.commands.tutorials;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_WEEK;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.tutorial.Tutorial;

/**
 * Lists students and attendance for a particular week for a given Tutorial in TAble.
 */
public class ListAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "listAttendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists students and attendance for "
            + "an existing tutorial in a given week.\n"
            + "Parameters: "
            + PREFIX_TUTORIAL_INDEX + "TUTORIAL_INDEX (must be a positive integer) "
            + PREFIX_TUTORIAL_WEEK + "SEMESTER_WEEK (between 3 to 13 inclusive)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_INDEX + "1 "
            + PREFIX_TUTORIAL_WEEK + "10";

    public static final String MESSAGE_SUCCESS = COMMAND_WORD + ": Listed students and attendance for %1$s %2$s "
            + "in week %3$s\n";

    private final Index tutorialIndex;
    private final int week;

    /**
     * Creates a ListAttendanceCommand to list students and attendance of {@code tutorial} in a given {@code week}.
     */
    public ListAttendanceCommand(Index tutorialIndex, int week) {
        this.tutorialIndex = tutorialIndex;
        this.week = week;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        if (tutorialIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
        }

        Tutorial tutorialToShow = lastShownList.get(tutorialIndex.getZeroBased());

        return new CommandResult(String.format(MESSAGE_SUCCESS, tutorialToShow.getModCode(),
                tutorialToShow.getTutorialName(), week), tutorialToShow, week - 3);
    }
}
