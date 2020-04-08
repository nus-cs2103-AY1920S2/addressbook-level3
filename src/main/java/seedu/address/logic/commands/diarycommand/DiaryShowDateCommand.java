package seedu.address.logic.commands.diarycommand;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Dummy java docs.
 */
public class DiaryShowDateCommand extends DiaryShowCommand {
    public static final String MESSAGE_SUCCESS = "Entry ids with given date: ";

    private final LocalDate date;

    public DiaryShowDateCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isExistingDate(date)) {
            throw new CommandException("Cannot find diary entry with the given date!");
        }

        List<Integer> ids = model.getListOfIdsByDate(date);

        return new CommandResult(MESSAGE_SUCCESS + " " + ids + ". Use diaryShow id/ENTRY_ID "
                + "to see a particular entry.");
    }
}
