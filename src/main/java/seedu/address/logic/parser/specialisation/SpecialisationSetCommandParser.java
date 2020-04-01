package seedu.address.logic.parser.specialisation;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.specialisation.SpecialisationSetCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.programmes.specialisations.cs.AlgorithmsAndTheorySpecialisation;

public class SpecialisationSetCommandParser implements Parser<SpecialisationSetCommand> {

    public static final String INVALID_SPECIALISATION = "Invalid Specialisation";
    /**
     * Parses the given {@code String} of arguments in the context of the DeclareMajorCommand
     * and returns a DeclareMajorCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SpecialisationSetCommand parse(String args) throws ParseException {
        requireNonNull(args);

        try {
            switch (args) {
            case "algo":
                return new SpecialisationSetCommand(new AlgorithmsAndTheorySpecialisation());
            default:
                throw new ParseException(INVALID_SPECIALISATION);
            }
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
