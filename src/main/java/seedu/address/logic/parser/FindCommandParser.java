package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.GroupContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagsContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        ArrayList<String> groupnameKeywords = new ArrayList<>();
        ArrayList<String> wordKeywords = new ArrayList<>();
        ArrayList<String> tagKeywords = new ArrayList<>();

        // run some check to make sure there is no invalid command!
        boolean hasGroupName = false;
        boolean hasWord = false;
        boolean hasTags = false;
        for (int i = 0; i < nameKeywords.length; i++) {
            if (nameKeywords[i].contains("-g/")) {
                hasGroupName = true;
            }
            if (nameKeywords[i].contains("-n/")) {
                hasWord = true;
            }
            if (nameKeywords[i].contains("-t/")) {
                hasTags = true;
            }
        }
        if ((hasGroupName == false) && (hasWord == false) && (hasTags == false)) {
            // then they did not provide any keywords to search for!
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }


        for (int i = 0; i < nameKeywords.length; i++) {
            if (nameKeywords[i].contains("-g/")) {
                nameKeywords[i] = nameKeywords[i].substring(3);
                while (!nameKeywords[i].contains("-n/") && !nameKeywords[i].contains("-t/")
                        && i != nameKeywords.length) {
                    groupnameKeywords.add(nameKeywords[i]);
                    i++;
                    if (i == nameKeywords.length) {
                        break;
                    }
                }
                break;
            }
        }

        for (int i = 0; i < nameKeywords.length; i++) {
            if (nameKeywords[i].contains("-n/")) {
                nameKeywords[i] = nameKeywords[i].substring(3);
                while (!nameKeywords[i].contains("-g/") && !nameKeywords[i].contains("-t/")
                        && i != nameKeywords.length) {
                    wordKeywords.add(nameKeywords[i]);
                    i++;
                    if (i == nameKeywords.length) {
                        break;
                    }
                }
                break;
            }
        }

        for (int i = 0; i < nameKeywords.length; i++) {
            if (nameKeywords[i].contains("-t/")) {
                nameKeywords[i] = nameKeywords[i].substring(3);
                while (!nameKeywords[i].contains("-g/") && !nameKeywords[i].contains("-n/")
                        && i != nameKeywords.length) {
                    tagKeywords.add(nameKeywords[i]);
                    i++;
                    if (i == nameKeywords.length) {
                        break;
                    }
                }
                break;
            }
        }


        String[] groupnameKeywordsArray = new String[groupnameKeywords.size()];
        for (int i = 0; i < groupnameKeywords.size(); i++) {
            groupnameKeywordsArray[i] = groupnameKeywords.get(i);
        }
        String[] wordKeywordsArray = new String[wordKeywords.size()];
        for (int i = 0; i < wordKeywords.size(); i++) {
            wordKeywordsArray[i] = wordKeywords.get(i);
        }
        String[] tagKeywordsArray = new String[tagKeywords.size()];
        for (int i = 0; i < tagKeywords.size(); i++) {
            tagKeywordsArray[i] = tagKeywords.get(i);
        }

        GroupContainsKeywordsPredicate groupnamePredicate =
                new GroupContainsKeywordsPredicate(Arrays.asList(groupnameKeywordsArray));
        NameContainsKeywordsPredicate wordPredicate =
                new NameContainsKeywordsPredicate(Arrays.asList(wordKeywordsArray));
        TagsContainsKeywordsPredicate tagPredicate = new TagsContainsKeywordsPredicate(Arrays.asList(tagKeywordsArray));


        return new FindCommand(groupnamePredicate, wordPredicate, tagPredicate);
    }

}
