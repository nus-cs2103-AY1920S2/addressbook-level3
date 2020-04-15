package hirelah.logic.commands;

import static hirelah.logic.util.CommandUtil.saveQuestions;
import static java.util.Objects.requireNonNull;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.commons.util.ModelUtil;
import hirelah.logic.commands.exceptions.CommandException;
import hirelah.model.Model;
import hirelah.model.hirelah.Question;
import hirelah.model.hirelah.QuestionList;
import hirelah.storage.Storage;

/**
 * EditQuestionCommand describes the behavior when the
 * client wants to update a question from the list.
 */

public class EditQuestionCommand extends Command {
    public static final String COMMAND_WORD = "question";
    public static final boolean DESIRED_MODEL_FINALIZED_STATE = false;
    public static final String MESSAGE_FORMAT = "edit " + COMMAND_WORD + " <question number> <edited question>";
    public static final String MESSAGE_FUNCTION = ": Edits the question's description, identified by its number.\n";
    public static final String MESSAGE_USAGE = MESSAGE_FORMAT
            + MESSAGE_FUNCTION
            + "Example: edit " + COMMAND_WORD + " 1 what the heck is this question?";

    public static final String MESSAGE_EDIT_QUESTION_SUCCESS = "Edited question %s to %s";

    private final int questionIndex;
    private final String updatedDescription;

    public EditQuestionCommand(int questionIndex, String updatedDescription) {
        this.questionIndex = questionIndex;
        this.updatedDescription = updatedDescription;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireNonNull(model);
        ModelUtil.validateFinalisation(model, DESIRED_MODEL_FINALIZED_STATE);
        QuestionList questions = model.getQuestionList();

        try {
            Question question = questions.edit(questionIndex, updatedDescription);
            saveQuestions(model, storage);
            return new ToggleCommandResult(String.format(MESSAGE_EDIT_QUESTION_SUCCESS, questionIndex,
                    updatedDescription), ToggleView.QUESTION);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditQuestionCommand // instanceof handles nulls
                && questionIndex == ((EditQuestionCommand) other).questionIndex
                && updatedDescription.equals(((EditQuestionCommand) other).updatedDescription)); // state check
    }
}
