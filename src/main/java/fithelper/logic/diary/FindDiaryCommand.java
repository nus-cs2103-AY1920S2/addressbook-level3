package fithelper.logic.diary;

import static fithelper.logic.commands.CommandResult.DisplayedPage.DIARY;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_KEYWORD;
import static java.util.Objects.requireNonNull;

import fithelper.commons.core.Messages;
import fithelper.logic.commands.Command;
import fithelper.logic.commands.CommandResult;
import fithelper.model.Model;
import fithelper.model.diary.DiaryContentContainsKeywordsPredicate;
import fithelper.model.diary.DiaryDatePredicate;

/**
 * Finds and lists all diaries in fitHelper whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 * If the parameter contains date, the diary of the specific date is displayed.
 * Otherwise, the keyword field is considered and should not be empty. All diaries containing the keywords, regardless
 * of the diary date, are displayed.
 */
public class FindDiaryCommand extends Command {

    public static final String COMMAND_WORD = "findDiary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all diaries whose names contain any of "
            + "the specified date "
            + "or the specified keywords. "
            + "If neither DATE nor KEYWORD is specified, all diaries will be listed. \n"
            + "Parameters: "
            + PREFIX_DATE + "DATE (optional)"
            + PREFIX_KEYWORD + "[keyword list]"
            + "Example: " + COMMAND_WORD
            + PREFIX_DATE + "2020-03-31 "
            + PREFIX_KEYWORD + "gym ";

    private final DiaryContentContainsKeywordsPredicate wordPredicate;
    private final DiaryDatePredicate datePredicate;

    public FindDiaryCommand(DiaryContentContainsKeywordsPredicate predicate,
                            DiaryDatePredicate datePredicate) {
        this.wordPredicate = predicate;
        this.datePredicate = datePredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String feedback = "";
        if (this.datePredicate == null && this.wordPredicate == null) {
            model.updateFilteredDiaryList(Model.PREDICATE_SHOW_ALL_DIARIES);
        } else if (this.datePredicate == null) {
            model.updateFilteredDiaryList(wordPredicate);
        } else {
            model.updateFilteredDiaryList(datePredicate);
        }
        feedback = String.format(Messages.MESSAGE_DIARY_LISTED_OVERVIEW, model.getFilteredDiaryList().size());
        return new CommandResult(feedback, DIARY, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindDiaryCommand // instanceof handles nulls
                && wordPredicate.equals(((FindDiaryCommand) other).wordPredicate)); // state check
    }
}
