package seedu.address.model.recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Recipe's instructions in the recipe book.
 */
public class InstructionList {

    private List<Instruction> instructions = new ArrayList<>();

    /**
     * Constructs for InstructionList.
     */
    public InstructionList() {
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

    //must have accessors to avoid checkstyle failure
    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
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
