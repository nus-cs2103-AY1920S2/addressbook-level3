package seedu.address.logic.parser.people;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_KEYWORD_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.people.PeopleFindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PeopleEmailPredicate;
import seedu.address.model.person.PeopleNamePredicate;
import seedu.address.model.person.PeoplePhonePredicate;
import seedu.address.model.person.PeoplePredicate;
import seedu.address.model.person.PeopleTagPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new PeopleFindCommand object
 */
public class PeopleFindCommandParser implements Parser<PeopleFindCommand> {

    public static final String MESSAGE_INVALID_TAG_PREDICATE = "'Debt' and 'Loan' (case-insensitive) are the only "
            + "tags that can be used in people find command.";

    private final Set<Tag> validTags = new HashSet<>();

    /**
     * Parses the given {@code String} of arguments in the context of the PeopleFindCommand
     * and returns a PeopleFindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public PeopleFindCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG);

        PeoplePredicate predicate = null;

        int numOfTokensPresent = 0;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            numOfTokensPresent++;
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            numOfTokensPresent++;
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            numOfTokensPresent++;
        }
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            numOfTokensPresent++;
        }

        if (numOfTokensPresent > 1) {
            throw new ParseException(
                    String.format(PeopleFindCommand.ONLY_ONE_PARAMETER_ALLOWED, PeopleFindCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            if (nameKeywords.length == 1 && nameKeywords[0].equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_KEYWORD_NOT_FOUND, PeopleFindCommand.MESSAGE_USAGE));
            }
            predicate = new PeopleNamePredicate(Arrays.asList(nameKeywords));
        } else if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String[] phoneKeywords = argMultimap.getValue(PREFIX_PHONE).get().split("\\s+");
            if (phoneKeywords.length == 1 && phoneKeywords[0].equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_KEYWORD_NOT_FOUND, PeopleFindCommand.MESSAGE_USAGE));
            }
            predicate = new PeoplePhonePredicate(Arrays.asList(phoneKeywords));
        } else if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String[] emailKeywords = argMultimap.getValue(PREFIX_EMAIL).get().split("\\s+");
            if (emailKeywords.length == 1 && emailKeywords[0].equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_KEYWORD_NOT_FOUND, PeopleFindCommand.MESSAGE_USAGE));
            }
            predicate = new PeopleEmailPredicate(Arrays.asList(emailKeywords));
        } else if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            String[] tagKeywords = argMultimap.getValue(PREFIX_TAG).get().split("\\s+");

            if (tagKeywords.length == 1 && tagKeywords[0].equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_KEYWORD_NOT_FOUND, PeopleFindCommand.MESSAGE_USAGE));
            }

            //Can only search for debt or loan in tag
            validTags.add(new Tag("Debt"));
            validTags.add(new Tag("Loan"));
            if (!validTags.stream().anyMatch(tag -> Arrays.asList(tagKeywords).stream()
                    .anyMatch(tagKeyword -> StringUtil.containsWordIgnoreCase(tag.tagName, tagKeyword)))) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MESSAGE_INVALID_TAG_PREDICATE));
            }

            predicate = new PeopleTagPredicate(Arrays.asList(tagKeywords));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PeopleFindCommand.MESSAGE_USAGE));
        }

        return new PeopleFindCommand(predicate);
    }

}
