package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASK;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.todolist.Task;
import seedu.address.ui.CalenderDate;
import seedu.address.ui.CalenderPanel;

/**
 * Adds a deadline.
 */

public class DeleteDeadlineCommand extends DeleteTaskCommand {

    public static final String MESSAGE_SUCCESS = "Deadline Deleted: ";
    public static final String MESSAGE_FAIL = "No such deadline exists";




    private final Task deadlineToDelete;

    public DeleteDeadlineCommand(Task deadlineToDelete) {
        requireNonNull(deadlineToDelete);
        this.deadlineToDelete = deadlineToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (Task.getDeadlineTaskList().size() < deadlineToDelete.getIndex()) {
            return new CommandResult(MESSAGE_FAIL);
        }

        Task removed = Task.getDeadlineTaskList().remove(deadlineToDelete.getIndex() - 1);
        removeUiDot(removed);

        model.updateDeadlineTaskList(PREDICATE_SHOW_ALL_TASK);
        return new CommandResult(MESSAGE_SUCCESS + removed);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteDeadlineCommand // instanceof handles nulls
                && deadlineToDelete == deadlineToDelete);
    }

    /**
     * Dummy doc.
     * @param removed
     */
    public void removeUiDot(Task removed) {
        String[] date = removed.getDate().split("-");
        System.out.println(removed);
        int year = Integer.parseInt(date[2]);
        String day = date[0];
        int month = Integer.parseInt(date[1]);
        if (CalenderPanel.getYear() == year && CalenderPanel.getCurrentMonth() == month) {
            CalenderDate calenderDate = CalenderPanel.getCalenderDatesArrayList().get(Integer.parseInt(day) - 1);

            calenderDate.decreaseCount();
            calenderDate.decreaseCount();
            System.out.println("decreasedCount " + calenderDate.getCount());
        }
    }

}
