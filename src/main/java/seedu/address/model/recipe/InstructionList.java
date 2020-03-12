package seedu.address.model.recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Recipe's instructions in the recipe book.
 */
public class InstructionList {
    public List<Instruction> instructions = new ArrayList<>();

    /**
     * Constructs an {@code InstructionList}.
     */
    public InstructionList() {
    }

    /**
     * Adds an instruction from the instructions list.
     *
     * @param instruction, the instruction to be added.
     */
    public void addInstruction(Instruction instruction) {
        instructions.add(instruction);
    }

    /**
     * Deletes an instruction from the instructions list.
     *
     * @param instruction, the instruction to be deleted.
     */
    public void deleteInstruction(Instruction instruction) {
        instructions.remove(instruction);
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
