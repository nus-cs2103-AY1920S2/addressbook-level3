package seedu.address.logic.commands.academics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.academics.Assessment;

/**
 * Adds an academic progress report to address book.
 */
public class AcademicsMarkCommand extends AcademicsCommand {

    public static final String MESSAGE_USAGE = "This command marks students' work for the assessment identified. \n"
            + "Format: " + COMMAND_WORD + " "
            + PREFIX_MARK + " ASSESSMENT INDEX (must be a positive integer) "
            + PREFIX_STUDENT + "STUDENT NAME-SCORE ...\n"
            + "Example: academics mark 1 stu/Simon Lam-80\n"
            + "Example: academics mark 1 stu/Simon Lam-80 stu/Gerren Seow-70\n";

    public static final String MESSAGE_SUCCESS = "Academics marked following submissions:\n";

    private Index index;
    private HashMap<String, Integer> submissions = new HashMap<>();

    public AcademicsMarkCommand(Index index, List<String> students) throws CommandException {
        requireAllNonNull(index, students);
        this.index = index;
        for (String submission : students) {
            String[] submissionArray = submission.split("-");
            if (submissionArray.length < 2) {
                throw new CommandException(Messages.MESSAGE_MISSING_SCORE);
            }
            String student = submissionArray[0];
            int mark = Integer.parseInt(submissionArray[1]);
            submissions.put(student, mark);
        }
    }

    /**
     * Formats list of students.
     */
    public String formatStudents() {
        String formatted = "";
        Iterator<Map.Entry<String, Integer>> stringIterator = submissions.entrySet().iterator();
        while (stringIterator.hasNext()) {
            Map.Entry<String, Integer> entry = stringIterator.next();
            formatted += entry.getKey() + ": " + entry.getValue() + "\n";
        }
        return formatted;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Assessment> lastShownAssessments = model.getFilteredAcademicsList();
        if (index.getZeroBased() >= lastShownAssessments.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSESSMENT_DISPLAYED_INDEX);
        }
        Assessment assessment = lastShownAssessments.get(index.getZeroBased());

        Iterator<Map.Entry<String, Integer>> iterator = submissions.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (!model.hasStudentName(entry.getKey())) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_SUBMISSION);
            }
            if (!model.hasStudentSubmitted(assessment, entry.getKey())) {
                throw new CommandException(Messages.MESSAGE_STUDENT_HAS_NOT_SUBMITTED);
            }
            if (entry.getValue() < 0 || entry.getValue() > 100) {
                throw new CommandException(Messages.MESSAGE_INVALID_MARKS_SUBMISSION);
            }
        }

        model.markAssessment(assessment, submissions);
        return new CommandResult(MESSAGE_SUCCESS + formatStudents());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AcademicsMarkCommand); // instanceof handles nulls
    }
}
