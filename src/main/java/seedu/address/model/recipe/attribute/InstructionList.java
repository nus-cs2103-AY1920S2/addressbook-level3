package seedu.address.model.recipe.attribute;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Recipe's instructions in the recipe book.
 */
public class InstructionList {

    public static final String MESSAGE_CONSTRAINTS =
            "Instructions can take any values for now, and it should not be " + "blank";

    /*
     * The first character of the instructions must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final List<Instruction> instructions;

    // TODO: change to List, for now using String to temp store instruction list
    public final String instructionListString;

    /**
     * Constructs for InstructionList.
     */
    public InstructionList(String instructionListString) {
        requireNonNull(instructionListString);
        checkArgument(isValidInstructions(instructionListString), MESSAGE_CONSTRAINTS);

        this.instructionListString = instructionListString;

        // TODO: Update InstructionList to use arraylist instead of raw String
        this.instructions = new ArrayList<Instruction>();
    }

    /**
     * Returns true if a given string is a valid instruction list.
     */
    public static boolean isValidInstructions(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Adds an instruction from the instructions list.
     *
     * @param instruction the instruction to be added.
     */
    public void addInstruction(Instruction instruction) {
        instructions.add(instruction);
    }

    /**
     * Deletes an instruction from the instructions list.
     *
     * @param instruction the instruction to be deleted.
     */
    public void deleteInstruction(Instruction instruction) {
        instructions.remove(instruction);
    }

    @Override
    public String toString() {
        return instructionListString;
    }

    /**
     * Prints out the instructions list.
     */
    public void print() {
        for (int i = 0; i < instructions.size(); i++) {
            System.out.print(instructions.get(i));
        }
    }
}
