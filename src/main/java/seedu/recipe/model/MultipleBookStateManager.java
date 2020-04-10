package seedu.recipe.model;

import java.util.ArrayList;
import java.util.Stack;

import seedu.recipe.logic.commands.CommandType;

/**
 * Maintains different versions (states) of RecipeBooks, PlannedBooks, and CookedRecordBooks in a list.
 */
public class MultipleBookStateManager extends RecipeBook {

    private final ArrayList<ReadOnlyRecipeBook> recipeBookStateList = new ArrayList<>();
    private final ArrayList<ReadOnlyPlannedBook> plannedBookStateList = new ArrayList<>();
    private final ArrayList<ReadOnlyCookedRecordBook> cookedRecordBookStateList = new ArrayList<>();

    private final Stack<CommandType> stackToUndo = new Stack<>();
    private final Stack<CommandType> stackToRedo = new Stack<>();

    private int recipeBookStatePointer;
    private int plannedBookStatePointer;
    private int cookedRecordBookStatePointer;

    public MultipleBookStateManager(ReadOnlyRecipeBook recipeBook, ReadOnlyPlannedBook plannedBook,
                                    ReadOnlyCookedRecordBook cookedRecordBook) {
        this.recipeBookStateList.add(recipeBook);
        this.plannedBookStateList.add(plannedBook);
        this.cookedRecordBookStateList.add(cookedRecordBook);
        this.recipeBookStatePointer = 0;
        this.plannedBookStatePointer = 0;
        this.cookedRecordBookStatePointer = 0;
    }

    /**
     * Checks if it is possible to undo. Returns true if there is at least {@code numberOfUndo} older states
     * of the RecipeBook (relative to the current state) stored in the list.
     */
    boolean canUndo(int numberOfUndo) {
        assert numberOfUndo >= 0;
        if (numberOfUndo > 0) {
            return numberOfUndo <= stackToUndo.size();
        } else {
            return !stackToUndo.empty();
        }
    }

    /**
     * Checks if it is possible to redo. Returns true if there is are at least {@code numberOfRedo} newer states
     * of the RecipeBook (relative to the current state) stored in the list.
     */
    boolean canRedo(int numberOfRedo) {
        assert numberOfRedo >= 0;
        if (numberOfRedo > 0) {
            return numberOfRedo <= stackToRedo.size();
        } else {
            return !stackToRedo.empty();
        }
    }

    /**
     * Removes redundant states before a user commits a book.
     */
    private void removeRedundantStates() {
        while (recipeBookStateList.size() - 1 > recipeBookStatePointer
                || plannedBookStateList.size() - 1 > plannedBookStatePointer
                || cookedRecordBookStateList.size() - 1 > cookedRecordBookStatePointer) {
            assert !stackToRedo.empty();
            CommandType redoCommandType = stackToRedo.pop();
            switch (redoCommandType) {
            case MAIN_LONE:
                recipeBookStateList.remove(recipeBookStateList.size() - 1);
                break;
            case MAIN:
                recipeBookStateList.remove(recipeBookStateList.size() - 1);
                plannedBookStateList.remove(plannedBookStateList.size() - 1);
                break;
            case PLAN:
                plannedBookStateList.remove(plannedBookStateList.size() - 1);
                break;
            case GOALS:
                cookedRecordBookStateList.remove(cookedRecordBookStateList.size() - 1);
                break;
            default:
                break;
            }
        }
    }

    /**
     * Adds the new state of the RecipeBook to the list when the RecipeBook undergoes a state change.
     * If the current state is not the last item on the list, the new state will override all states
     * after the current state.
     */
    void commitRecipeBook(ReadOnlyRecipeBook recipeBook, CommandType commandType) {
        stackToUndo.push(commandType); // CommandType.MAIN_LONE

        recipeBookStateList.add(recipeBook);
        recipeBookStatePointer++;
    }

    /**
     * Commits both the recipe book and planned book as there are changes made to both of them.
     */
    void commitRecipeAndPlannedBook(ReadOnlyRecipeBook recipeBook, ReadOnlyPlannedBook plannedBook,
                                           CommandType commandType) {
        stackToUndo.push(commandType); // CommandType.MAIN
        removeRedundantStates();
        recipeBookStateList.add(recipeBook);
        recipeBookStatePointer++;
        plannedBookStateList.add(plannedBook);
        plannedBookStatePointer++;
    }

    /**
     * Commits only the planned book as changes were only made to it.
     */
    void commitPlannedBook(ReadOnlyPlannedBook plannedBook, CommandType commandType) {
        stackToUndo.push(commandType); // CommandType.PLAN
        removeRedundantStates();
        plannedBookStateList.add(plannedBook);
        plannedBookStatePointer++;
    }

    /**
     * Commits only the cooked record book as changes were only made to it.
     */
    void commitCookedRecordBook(ReadOnlyCookedRecordBook cookedRecordBook, CommandType commandType) {
        stackToUndo.push(commandType); // CommandType.GOALS
        removeRedundantStates();
        cookedRecordBookStateList.add(cookedRecordBook);
        cookedRecordBookStatePointer++;
    }

    /**
     * Reverts the books back by {@code numberOfUndo} states.
     */
    void undo(int numberOfUndo, Model model) {
        assert numberOfUndo >= 0;
        int undoCounter = numberOfUndo;
        if (numberOfUndo == 0) {
            undoCounter = stackToUndo.size();
        }
        while (undoCounter > 0) {
            CommandType commandType = stackToUndo.pop();
            stackToRedo.push(commandType);
            switch (commandType) {
            case MAIN_LONE:
                recipeBookStatePointer--;
                break;
            case MAIN:
                recipeBookStatePointer--;
                plannedBookStatePointer--;
                break;
            case PLAN:
                plannedBookStatePointer--;
                break;
            case GOALS:
                cookedRecordBookStatePointer--;
                break;
            default:
                break;
            }
            undoCounter--;
        }
        model.setRecipeBook(recipeBookStateList.get(recipeBookStatePointer));
        model.setPlannedBook(plannedBookStateList.get(plannedBookStatePointer));
        model.setCookedRecordBook(cookedRecordBookStateList.get(cookedRecordBookStatePointer));
    }

    /**
     * Fast forwards the books by {@code numberOfRedo} states.
     */
    void redo(int numberOfRedo, Model model) {
        assert numberOfRedo >= 0;
        int redoCounter = numberOfRedo;
        if (numberOfRedo == 0) {
            redoCounter = stackToRedo.size();
        }
        while (redoCounter > 0) {
            CommandType commandType = stackToRedo.pop();
            stackToUndo.push(commandType);
            switch (commandType) {
            case MAIN_LONE:
                recipeBookStatePointer++;
                break;
            case MAIN:
                recipeBookStatePointer++;
                plannedBookStatePointer++;
                break;
            case PLAN:
                plannedBookStatePointer++;
                break;
            case GOALS:
                cookedRecordBookStatePointer++;
                break;
            default:
                break;
            }
            redoCounter--;
        }
        model.setRecipeBook(recipeBookStateList.get(recipeBookStatePointer));
        model.setPlannedBook(plannedBookStateList.get(plannedBookStatePointer));
        model.setCookedRecordBook(cookedRecordBookStateList.get(cookedRecordBookStatePointer));
    }

}
