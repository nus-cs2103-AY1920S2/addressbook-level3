package cookbuddy.model.recipe.attribute;

import static cookbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a Recipe's instructions in the recipe book.
 */
public class InstructionList {
    public final ArrayList<Instruction> instructionData;

    /**
     * Constructs an instruction list from {@code ingredientList}. Requires that the
     * parameter be non-null. The parameter is guaranteed to be valid, as every item
     * in the list has already been individually checked by the Ingredient
     * constructor.
     *
     * @param instructionList a {@link List} of {@link Instruction}s
     */
    public InstructionList(List<Instruction> instructionList) {
        requireAllNonNull(instructionList);

        this.instructionData = new ArrayList<Instruction>(instructionList);
    }

    /**
     * Adds an instruction from the instructions list.
     *
     * @param instruction the instruction to be added.
     */
    public void addInstruction(Instruction instruction) {
        instructionData.add(instruction);
    }

    /**
     * Deletes an instruction from the instructions list.
     *
     * @param instruction the instruction to be deleted.
     */
    public void deleteInstruction(Instruction instruction) {
        instructionData.remove(instruction);
    }

    public List<Instruction> asList() {
        return List.copyOf(this.instructionData);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof InstructionList)) {
            return false;
        }

        return ((InstructionList) other).instructionData.equals(this.instructionData);
    }

    @Override
    public int hashCode() {
        return instructionData.hashCode();
    }

    @Override
    public String toString() {
        return instructionData.stream().map(Instruction::toString)
                .collect(Collectors.joining(";"));
    }
}
