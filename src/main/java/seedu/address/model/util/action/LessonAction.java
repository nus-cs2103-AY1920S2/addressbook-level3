package seedu.address.model.util.action;

import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;

/**
 * Represents a DoableAction involving a {@code Lesson}.
 */
public class LessonAction implements DoableAction<Lesson> {
    private final Lesson target;
    private final Lesson replacement;
    private final DoableActionType type;

    public LessonAction(Lesson target, DoableActionType type) {
        assert type != DoableActionType.EDIT;

        this.target = target;
        this.replacement = null;
        this.type = type;
    }

    public LessonAction(Lesson target, Lesson replacement, DoableActionType type) {
        assert type == DoableActionType.EDIT;

        this.target = target;
        this.replacement = replacement;
        this.type = type;
    }

    @Override
    public void undo(Model model) {
        switch (type) {
        case ADD:
            model.removeLesson(target);
            break;
        case EDIT:
            assert replacement != null;
            model.setLesson(replacement, target);
            break;
        case DELETE:
            model.addLesson(target);
            break;
        default:
            break;
        }
    }

    @Override
    public void redo(Model model) {
        switch (type) {
        case ADD:
            model.addLesson(target);
            break;
        case EDIT:
            assert replacement != null;
            model.setLesson(target, replacement);
            break;
        case DELETE:
            model.removeLesson(target);
            break;
        default:
            break;
        }
    }

    @Override
    public Lesson getTarget() {
        return target;
    }

    @Override
    public Lesson getReplacement() {
        return replacement;
    }

    @Override
    public DoableActionType getType() {
        return type;
    }
}
