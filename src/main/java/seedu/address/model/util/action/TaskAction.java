package seedu.address.model.util.action;

import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Represents a DoableAction involving a {@code Task}.
 */
public class TaskAction implements DoableAction<Task> {
    private final Task target;
    private final Task replacement;
    private final DoableActionType type;

    public TaskAction(Task target, DoableActionType type) {
        assert type != DoableActionType.EDIT;

        this.target = target;
        this.replacement = null;
        this.type = type;
    }

    public TaskAction(Task target, Task replacement, DoableActionType type) {
        assert type == DoableActionType.EDIT;

        this.target = target;
        this.replacement = replacement;
        this.type = type;
    }

    @Override
    public void undo(Model model) {
        switch (type) {
        case ADD:
            model.deleteTask(target);
            break;
        case EDIT:
            assert replacement != null;
            model.setTask(replacement, target);
            break;
        case DELETE:
            model.addTask(target);
            break;
        default:
            break;
        }
    }

    @Override
    public void redo(Model model) {
        switch (type) {
        case ADD:
            model.addTask(target);
            break;
        case EDIT:
            assert replacement != null;
            model.setTask(target, replacement);
            break;
        case DELETE:
            model.deleteTask(target);
            break;
        default:
            break;
        }
    }

    @Override
    public Task getTarget() {
        return this.target;
    }

    @Override
    public Task getReplacement() {
        return this.replacement;
    }

    @Override
    public DoableActionType getType() {
        return this.type;
    }
}
