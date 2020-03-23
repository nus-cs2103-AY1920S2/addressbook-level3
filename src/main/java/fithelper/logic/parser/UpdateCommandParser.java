package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntaxUtil.FLAG_FORCE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_ATTRIBUTE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_VALUE;
import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import fithelper.logic.commands.UpdateCommand;
import fithelper.logic.commands.UpdateCommand.UpdateValueDescriptor;
import fithelper.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UpdateCommand object
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    private ArgumentMultimap argMultimap;
    private UpdateValueDescriptor updateValueDescriptor;

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ATTRIBUTE, PREFIX_VALUE);

        if (!arePrefixesPresent(argMultimap, PREFIX_ATTRIBUTE, PREFIX_VALUE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        updateValueDescriptor = new UpdateValueDescriptor();
        updateValueDescriptor.setBoolean(args.contains(FLAG_FORCE.getFlag()));

        String attributeName = argMultimap.getValue(PREFIX_ATTRIBUTE).orElse("").toLowerCase().trim();
        String[] words = attributeName.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            sb.append(words[i]);
        }
        String attributeNameWithoutSpace = sb.toString();

        setName(attributeNameWithoutSpace);
        setGender(attributeNameWithoutSpace);
        setAge(attributeNameWithoutSpace);
        setAddress(attributeNameWithoutSpace);
        setHeight(attributeNameWithoutSpace);
        setTargetWeight(attributeNameWithoutSpace);

        if (!updateValueDescriptor.isAnyFieldUpdated()) {
            throw new ParseException(UpdateCommand.MESSAGE_NOT_UPDATED);
        }

        return new UpdateCommand(updateValueDescriptor);
    }

    public void setName(String attributeName) throws ParseException {
        if ("name".equals(attributeName)) {
            updateValueDescriptor.setName(ParserUtil.parseProfileName(argMultimap.getValue(PREFIX_VALUE).get()));
        }
    }

    public void setGender(String attributeName) throws ParseException {
        if ("gender".equals(attributeName)) {
            updateValueDescriptor.setGender(ParserUtil.parseProfileGender(argMultimap.getValue(PREFIX_VALUE).get()));
        }
    }

    public void setAge(String attributeName) throws ParseException {
        if ("age".equals(attributeName)) {
            updateValueDescriptor.setAge(ParserUtil.parseProfileAge(argMultimap.getValue(PREFIX_VALUE).get()));
        }
    }

    public void setAddress(String attributeName) throws ParseException {
        if ("address".equals(attributeName)) {
            updateValueDescriptor.setAddress(ParserUtil.parseProfileAddress(argMultimap.getValue(PREFIX_VALUE).get()));
        }
    }

    public void setHeight(String attributeName) throws ParseException {
        if ("height".equals(attributeName)) {
            updateValueDescriptor.setHeight(ParserUtil.parseProfileHeight(argMultimap.getValue(PREFIX_VALUE).get()));
        }
    }

    public void setTargetWeight(String attributeName) throws ParseException {
        if ("targetweight".equals(attributeName)) {
            updateValueDescriptor
                    .setTargetWeight(ParserUtil.parseProfileTargetWeight(argMultimap.getValue(PREFIX_VALUE).get()));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
