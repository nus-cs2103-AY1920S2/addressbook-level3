package seedu.address.model.util.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.model.Model;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.facilitator.ModuleCodesContainKeywordPredicate;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.task.util.TaskNumManager;

/**
 * Represents a DoableAction involving a {@code Module}.
 */
public class ModuleAction implements DoableAction<Module> {
    private final Module target;
    private final Module replacement;
    private final DoableActionType type;
    private List<Lesson> relatedLessons;
    private List<Task> relatedTask;
    private List<Facilitator> relatedFacil;

    public ModuleAction(Module target, DoableActionType type) {
        assert type == DoableActionType.ADD;

        this.target = target;
        this.replacement = null;
        this.type = type;
    }

    public ModuleAction(Module target, Module replacement, DoableActionType type) {
        assert type == DoableActionType.EDIT;

        this.target = target;
        this.replacement = replacement;
        this.type = type;
    }

    public ModuleAction(Module target, List<Lesson> relatedLessons, List<Task> relatedTask,
                        List<Facilitator> relatedFacil, DoableActionType type) {
        assert type == DoableActionType.DELETE;

        this.target = target;
        this.replacement = null;
        this.relatedLessons = relatedLessons;
        this.relatedTask = relatedTask;
        this.relatedFacil = relatedFacil;
        this.type = type;
    }

    @Override
    public void undo(Model model) {
        switch (type) {
        case ADD:
            model.deleteModule(target);
            if (model.getModule().isPresent() && model.getModule().get().equals(target)) {
                model.updateModule(Optional.empty());
            }
            break;
        case EDIT:
            assert replacement != null;
            model.setModule(replacement, target);
            if (!replacement.getModuleCode().equals(target.getModuleCode())) {
                model.setModuleCodeInFacilitatorList(replacement.getModuleCode(), target.getModuleCode());
                model.setModuleCodeInTaskList(replacement.getModuleCode(), target.getModuleCode());
                model.setModuleCodeInLessonList(replacement.getModuleCode(), target.getModuleCode());
            }
            if (model.getModule().isPresent() && model.getModule().get().equals(replacement)) {
                model.updateModule(Optional.of(target));
                model.updateFacilitatorListForModule(
                        new ModuleCodesContainKeywordPredicate(target.getModuleCode().value));
                model.updateTaskListForModule(x -> x.getModuleCode().equals(target.getModuleCode()));
            }
            break;
        case DELETE:
            model.addModule(target);
            ModuleCode moduleCode = target.getModuleCode();
            relatedLessons.forEach(model::addLesson);
            relatedTask.forEach(t -> {
                TaskNumManager.addNum(moduleCode, t.getTaskNum());
                model.addTask(t);
            });
            relatedFacil.forEach(f -> {
                List<Facilitator> query = new ArrayList<>();
                model.getFilteredFacilitatorList()
                                .stream()
                                .filter(facil -> facil.isSameFacilitator(f))
                                .forEach(query::add);
                assert query.size() <= 1;

                if (query.size() == 1) {
                    model.setFacilitator(query.get(0), f);
                } else {
                    model.addFacilitator(f);
                }
            });
            model.updateFacilitatorListForModule(Model.PREDICATE_SHOW_NO_FACILITATORS);
            model.updateTaskListForModule(Model.PREDICATE_SHOW_NO_TASKS);
            break;
        default:
            break;
        }
    }

    @Override
    public void redo(Model model) {
        switch (type) {
        case ADD:
            model.addModule(target);
            break;
        case EDIT:
            assert replacement != null;
            model.setModule(target, replacement);
            if (!replacement.getModuleCode().equals(target.getModuleCode())) {
                model.setModuleCodeInFacilitatorList(target.getModuleCode(), replacement.getModuleCode());
                model.setModuleCodeInTaskList(target.getModuleCode(), replacement.getModuleCode());
                model.setModuleCodeInLessonList(target.getModuleCode(), replacement.getModuleCode());
            }
            if (model.getModule().isPresent() && model.getModule().get().equals(target)) {
                model.updateModule(Optional.of(replacement));
                model.updateFacilitatorListForModule(
                        new ModuleCodesContainKeywordPredicate(replacement.getModuleCode().value));
                model.updateTaskListForModule(x -> x.getModuleCode().equals(replacement.getModuleCode()));
            }
            break;
        case DELETE:
            model.deleteModule(target);
            model.removeLessonFromModule(target.getModuleCode());
            model.deleteTasksWithModuleCode(target.getModuleCode());
            model.deleteModuleCodeFromFacilitatorList(target.getModuleCode());
            if (model.getModule().isPresent() && model.getModule().get().equals(target)) {
                model.updateModule(Optional.empty());
            }
            break;
        default:
            break;
        }
    }

    @Override
    public Module getTarget() {
        return this.target;
    }

    @Override
    public Module getReplacement() {
        return replacement;
    }

    @Override
    public DoableActionType getType() {
        return this.type;
    }
}
