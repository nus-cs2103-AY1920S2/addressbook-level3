package seedu.address.model.recipe;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ItemListParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Recipe's instructions in the recipe book.
 */
public class InstructionList {

    public static final String MESSAGE_CONSTRAINTS = "Instructions can take any values for now, and it should not be "
            + "blank";

    /*
     * The first character of the instructions must not be a whitespace, otherwise
     * " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final List<Instruction> data;

    /**
     * Constructs for InstructionList.
     *
     * @throws ParseException
     */
    public InstructionList(String instructionListString) {
        requireNonNull(instructionListString);
        //checkArgument(isValidInstructions(instructionListString), MESSAGE_CONSTRAINTS);

        List<Instruction> templist;
        try {
            templist = ItemListParser.parseInstructionsToList(instructionListString);
        } catch (ParseException e) {
            templist = new ArrayList<Instruction>();
        }
        this.data = templist;
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
        data.add(instruction);
    }

    /**
     * Deletes an instruction from the instructions list.
     *
     * @param instruction the instruction to be deleted.
     */
    public void deleteInstruction(Instruction instruction) {
        data.remove(instruction);
    }

    @Override
    public String toString() {
        return (String) this.data.stream()
                .map(Instruction::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Prints out the instructions list.
     */
    public void print() {
        for (int i = 0; i < data.size(); i++) {
            System.out.print(data.get(i));
        }
    }
}
