package seedu.foodiebot.logic.parser;

import static seedu.foodiebot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_CANTEEN;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.stream.Stream;

import seedu.foodiebot.commons.core.index.Index;
import seedu.foodiebot.logic.commands.RandomizeCommand;

import seedu.foodiebot.logic.parser.exceptions.ParseException;
import seedu.foodiebot.model.randomize.Randomize;

/** Process the arguments for Randomize */
public class RandomizeCommandParser implements Parser<RandomizeCommand> {

    private Randomize randomize = Randomize.checkRandomize();

    /**
     * This method process the input for the correct execution.
     * @param args This is the given input from the user.
     * @return RandomizeCommand for execution.
     * @throws ParseException when the input provided is invalid.
     */
    public RandomizeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CANTEEN, PREFIX_TAG);
        String pc = ParserContext.getCurrentContext();
        if (!pc.equals(ParserContext.MAIN_CONTEXT) && !pc.equals(ParserContext.RANDOMIZE_CONTEXT)
                && !pc.equals((ParserContext.CANTEEN_CONTEXT))) {
            throw new ParseException(ParserContext.INVALID_CONTEXT_MESSAGE + ParserContext.getCurrentContext()
                    + "\n" + ParserContext.SUGGESTED_CONTEXT_MESSAGE
                    + ParserContext.MAIN_CONTEXT + ", " + ParserContext.RANDOMIZE_CONTEXT + ", enter 1"
                    + ParserContext.CANTEEN_CONTEXT);
        }


        if (args.equals("")) {
            return new RandomizeCommand("", "all", randomize);
        } else if (!arePrefixesPresent(argMultimap, PREFIX_CANTEEN)) {
            if (!arePrefixesPresent(argMultimap, PREFIX_TAG)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, RandomizeCommand.MESSAGE_USAGE));
            }
        }
        return separatePrefix(argMultimap);
    }

    /**
     * This method separate the argument from the prefix.
     * @param argMultimap given argMultimap
     * @return RandomizeCommand
     * @throws ParseException when the input is invalid.
     */
    public RandomizeCommand separatePrefix(ArgumentMultimap argMultimap) throws ParseException {
        try {
            Prefix firstPrefix = argMultimap.prefixSet().stream().findFirst().get();
            String argValue = getArgString(argMultimap, firstPrefix);
            try {
                Index index = ParserUtil.parseIndex(argValue);
                return new RandomizeCommand(firstPrefix.toString(), index, randomize);
            } catch (ParseException pe) {
                return new RandomizeCommand(firstPrefix.toString(), argValue, randomize);
            }
        } catch (NullPointerException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT));
        }
    }

    /**
     * This method checks if there are Prefixes.
     * @param argumentMultimap arguments for testing.
     * @return Boolean true is there are prefix, false otherwise.
     */
    private static boolean arePrefixesPresent(
            ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


    /**
     * This method extract the argument attached to the prefix.
     * @param argMultimap The arguments provided.
     * @param prefix the prefix available for this method.
     * @return String of the arguement attach to the prefix.
     * @throws ParseException If no value is present.
     */
    public static String getArgString(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        return argMultimap.getValue(prefix)
                .orElseThrow(() -> new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT)
                ));
    }
}
