package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.Question;
import seedu.address.model.hirelah.QuestionList;

import static java.util.Objects.requireNonNull;

/**
 * NavigationQuestionCommand describes the behavior when the
 * client wants to navigate to a certain remark of the interview.
 */
public class NavigationQuestionCommand extends Command {
    public static final String COMMAND_WORD = "to";
    public static final String MESSAGE_NAVIGATION_QUESTION_SUCCESS = "Here is the remark of question %s: ";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": . "
            + "Example: list " + COMMAND_WORD;

    private final String questionIndex;

    public NavigationQuestionCommand(String questionIndex) {
        this.questionIndex = questionIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        QuestionList questions = model.getQuestionList();
        try {
            if (!model.hasCurrentInterviewee()) {
                throw new CommandException("You need to open a transcript of a particular interviewee " +
                        "to go to the answer of a question.");
            }
            Question question = questions.find(questionIndex);
            return new CommandResult(String.format(MESSAGE_NAVIGATION_QUESTION_SUCCESS, question),
                    ToggleView.TRANSCRIPT);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NavigationQuestionCommand);
    }
}
