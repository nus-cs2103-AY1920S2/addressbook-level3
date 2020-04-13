package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_BLANK_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindRestaurantCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.OrganizationContainsKeywordsPredicate;
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

        String[] keywords = trimmedArgs.split("\\s+");

        // convert all to lower case first
        for (int i = 0; i < keywords.length; i++) {
            keywords[i] = keywords[i].toLowerCase();
        }

        ArrayList<String> organizationKeywords = new ArrayList<>();
        ArrayList<String> nameKeywords = new ArrayList<>();
        ArrayList<String> tagKeywords = new ArrayList<>();

        // run some check to make sure there is no invalid command!
        boolean hasOrganization = false;
        boolean hasName = false;
        boolean hasTags = false;
        for (int i = 0; i < keywords.length; i++) {
            if (keywords[i].contains("o/")) {
                if (keywords[i].substring(0, 2).equals("o/")) {
                    hasOrganization = true;
                }
            }
            if (keywords[i].contains("n/")) {
                if (keywords[i].substring(0, 2).equals("n/")) {
                    hasName = true;
                }
            }
            if (keywords[i].contains("t/")) {
                if (keywords[i].substring(0, 2).equals("t/")) {
                    hasTags = true;
                }
            }
        }
        if ((hasOrganization == false) && (hasName == false) && (hasTags == false)) {
            // then they did not provide any keywords to search for!
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }


        for (int i = 0; i < keywords.length; i++) {
            if (keywords[i].contains("o/")) {
                keywords[i] = keywords[i].substring(2);
                while (!keywords[i].contains("n/") && !keywords[i].contains("t/")
                        && i != keywords.length) {
                    organizationKeywords.add(keywords[i]);
                    i++;
                    if (i == keywords.length) {
                        break;
                    }
                }
                break;
            }
        }

        // if they wrote in the prefix but did not supply keywords
        if (hasOrganization) {
            for (int i = 0; i < organizationKeywords.size(); i++) {
                if (organizationKeywords.get(i).equals("")) {
                    throw new ParseException(
                            String.format(MESSAGE_BLANK_COMMAND_FORMAT, FindRestaurantCommand.MESSAGE_USAGE));
                }
            }
        }

        for (int i = 0; i < keywords.length; i++) {
            if (keywords[i].contains("n/")) {
                keywords[i] = keywords[i].substring(2);
                while (!keywords[i].contains("o/") && !keywords[i].contains("t/")
                        && i != keywords.length) {
                    nameKeywords.add(keywords[i]);
                    i++;
                    if (i == keywords.length) {
                        break;
                    }
                }
                break;
            }
        }

        // if they wrote in the prefix but did not supply keywords
        if (hasName) {
            for (int i = 0; i < nameKeywords.size(); i++) {
                if (nameKeywords.get(i).equals("")) {
                    throw new ParseException(
                            String.format(MESSAGE_BLANK_COMMAND_FORMAT, FindRestaurantCommand.MESSAGE_USAGE));
                }
            }
        }

        for (int i = 0; i < keywords.length; i++) {
            if (keywords[i].contains("t/")) {
                keywords[i] = keywords[i].substring(2);
                while (!keywords[i].contains("o/") && !keywords[i].contains("n/")
                        && i != keywords.length) {
                    tagKeywords.add(keywords[i]);
                    i++;
                    if (i == keywords.length) {
                        break;
                    }
                }
                break;
            }
        }

        // if they wrote in the prefix but did not supply keywords
        if (hasTags) {
            for (int i = 0; i < tagKeywords.size(); i++) {
                if (tagKeywords.get(i).equals("")) {
                    throw new ParseException(
                            String.format(MESSAGE_BLANK_COMMAND_FORMAT, FindRestaurantCommand.MESSAGE_USAGE));
                }
            }
        }


        String[] organizationKeywordsArray = new String[organizationKeywords.size()];
        for (int i = 0; i < organizationKeywords.size(); i++) {
            organizationKeywordsArray[i] = organizationKeywords.get(i);
        }
        String[] nameKeywordsArray = new String[nameKeywords.size()];
        for (int i = 0; i < nameKeywords.size(); i++) {
            nameKeywordsArray[i] = nameKeywords.get(i);
        }
        String[] tagKeywordsArray = new String[tagKeywords.size()];
        for (int i = 0; i < tagKeywords.size(); i++) {
            tagKeywordsArray[i] = tagKeywords.get(i);
        }

        OrganizationContainsKeywordsPredicate organizationPredicate =
                new OrganizationContainsKeywordsPredicate(Arrays.asList(organizationKeywordsArray));
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Arrays.asList(nameKeywordsArray));
        TagsContainsKeywordsPredicate tagPredicate = new TagsContainsKeywordsPredicate(Arrays.asList(tagKeywordsArray));


        return new FindCommand(organizationPredicate, namePredicate, tagPredicate);
    }

}
