package seedu.recipe.model;

import java.util.ArrayList;
import java.util.Stack;

import seedu.recipe.logic.commands.CommandType;
import seedu.recipe.model.recipe.RecipeBook;

/**
 * Maintains different versions (states) of RecipeBooks, PlannedBooks, and CookedRecordBooks in a list.
 */
public class MultipleBookStateManager extends RecipeBook {

    private final ArrayList<ReadOnlyRecipeBook> recipeBookStateList = new ArrayList<>();
    private final ArrayList<ReadOnlyPlannedBook> plannedBookStateList = new ArrayList<>();
    private final ArrayList<ReadOnlyCookedRecordBook> cookedRecordBookStateList = new ArrayList<>();

    private final Stack<CommandType> commandsToUndo = new Stack<>();
    private final Stack<CommandType> commandsToRedo = new Stack<>();

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
    public boolean canUndo(int numberOfUndo) {
        assert numberOfUndo >= 0;
        if (numberOfUndo > 0) {
            return numberOfUndo <= commandsToUndo.size();
        } else {
            return !commandsToUndo.empty();
        }
    }

    /**
     * Checks if it is possible to redo. Returns true if there is are at least {@code numberOfRedo} newer states
     * of the RecipeBook (relative to the current state) stored in the list.
     */
    public boolean canRedo(int numberOfRedo) {
        assert numberOfRedo >= 0;
        if (numberOfRedo > 0) {
            return numberOfRedo <= commandsToRedo.size();
        } else {
            return !commandsToRedo.empty();
        }
    }

    /**
     * Removes redundant states before a user commits a book.
     */
    private void removeRedundantStates() {
        while (recipeBookStateList.size() - 1 > recipeBookStatePointer
                || plannedBookStateList.size() - 1 > plannedBookStatePointer) {
            assert !commandsToRedo.empty();
            CommandType redoCommandType = commandsToRedo.pop();
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
    public void commitRecipeBook(ReadOnlyRecipeBook recipeBook, CommandType commandType) {
        commandsToUndo.push(commandType); // CommandType.MAIN_LONE
        if ((recipeBookStatePointer != recipeBookStateList.size() - 1)
                || (plannedBookStatePointer != plannedBookStateList.size() - 1)) {
            removeRedundantStates();
        }
        recipeBookStateList.add(recipeBook);
        recipeBookStatePointer++;
    }

    /**
     * Commits both the recipe book and planned book as there are changes made to both of them.
     */
    public void commitRecipeAndPlannedBook(ReadOnlyRecipeBook recipeBook, ReadOnlyPlannedBook plannedBook,
                                           CommandType commandType) {
        commandsToUndo.push(commandType); // CommandType.MAIN
        if ((recipeBookStatePointer != recipeBookStateList.size() - 1)
            || (plannedBookStatePointer != plannedBookStateList.size() - 1)) {
            removeRedundantStates();
        }
        recipeBookStateList.add(recipeBook);
        recipeBookStatePointer++;
        plannedBookStateList.add(plannedBook);
        plannedBookStatePointer++;
    }

    /**
     * Commits only the planned book as changes were only made to it.
     */
    public void commitPlannedBook(ReadOnlyPlannedBook plannedBook, CommandType commandType) {
        commandsToUndo.push(commandType); // CommandType.PLAN
        if (plannedBookStatePointer != plannedBookStateList.size() - 1) {
            removeRedundantStates();
        }
        plannedBookStateList.add(plannedBook);
        plannedBookStatePointer++;
    }

    /**
     * Reverts the books back by {@code numberOfUndo} states.
     */
    public void undo(int numberOfUndo, Model model) {
        assert numberOfUndo >= 0;
        int undoCounter = numberOfUndo;
        if (numberOfUndo == 0) {
            undoCounter = commandsToUndo.size();
        }
        while (undoCounter > 0) {
            assert recipeBookStatePointer > 0 && recipeBookStatePointer <= recipeBookStateList.size() - 1;
            assert plannedBookStatePointer > 0 && plannedBookStatePointer <= plannedBookStateList.size() - 1;
            CommandType commandType = commandsToUndo.pop();
            commandsToRedo.push(commandType);
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
    }

    /**
     * Fast forwards the books by {@code numberOfRedo} states.
     */
    public void redo(int numberOfRedo, Model model) {
        assert numberOfRedo >= 0;
        int redoCounter = numberOfRedo;
        if (numberOfRedo == 0) {
            redoCounter = commandsToRedo.size();
        }
        while (redoCounter > 0) {
            assert recipeBookStatePointer > 0 && recipeBookStatePointer <= recipeBookStateList.size() - 1;
            assert plannedBookStatePointer > 0 && plannedBookStatePointer <= plannedBookStateList.size() - 1;
            CommandType commandType = commandsToRedo.pop();
            commandsToUndo.push(commandType);
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
    }

}
