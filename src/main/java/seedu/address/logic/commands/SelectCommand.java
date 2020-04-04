package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.manager.DetailManager;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.AssignmentIDContainsKeywordsPredicate;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelCourse.CourseIDContainsKeywordsPredicate;
import seedu.address.model.modelFinance.FinanceIDContainsKeywordsPredicate;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.modelStudent.StudentIDContainsKeywordsPredicate;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStaff.StaffIDContainsKeywordsPredicate;
import seedu.address.model.person.ID;

/**
 * Finds and lists all courses in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SelectCommand extends Command {

  public static final String COMMAND_WORD = "select";

  public static final String MESSAGE_USAGE =
      COMMAND_WORD + ": Finds all courses whose names contain any of "
          + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
          + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
          + "Example: " + COMMAND_WORD + " alice bob charlie";

  public DetailManager detailManager = DetailManager.getInstance();

  List<ArgumentTokenizer.PrefixPosition> positions;

  List<ID> selectMetaDataIDs;

  public SelectCommand(List<ArgumentTokenizer.PrefixPosition> positions, List<ID> selectMetaDataIDs) {
    this.positions = positions;
    this.selectMetaDataIDs = selectMetaDataIDs;
    this.detailManager = DetailManager.getInstance();
  }

  @Override
  public CommandResult execute(Model model) throws CommandException {
    requireNonNull(model);
    detailManager.updateDetails(positions, selectMetaDataIDs);
    return new CommandResult("a");
  }

}
