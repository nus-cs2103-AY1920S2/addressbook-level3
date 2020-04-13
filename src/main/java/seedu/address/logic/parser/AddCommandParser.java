package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.UNREACHABLE_STATEMENT_REACHED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBSITE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.add.AddCommand;
import seedu.address.logic.commands.add.AddInternshipCommand;
import seedu.address.logic.commands.add.AddNoteCommand;
import seedu.address.logic.commands.add.AddProjectCommand;
import seedu.address.logic.commands.add.AddResumeCommand;
import seedu.address.logic.commands.add.AddSkillCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.Internship;
import seedu.address.model.item.Item;
import seedu.address.model.item.Note;
import seedu.address.model.item.Project;
import seedu.address.model.item.Resume;
import seedu.address.model.item.Skill;
import seedu.address.model.item.field.Description;
import seedu.address.model.item.field.Level;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Role;
import seedu.address.model.item.field.Time;
import seedu.address.model.item.field.Website;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.ItemUtil;

/**
 * Parses input arguments and creates a new AddCommand object.
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG, PREFIX_ITEM, PREFIX_FROM, PREFIX_TO,
                        PREFIX_ROLE, PREFIX_DESCRIPTION, PREFIX_WEBSITE, PREFIX_LEVEL);

        if (!arePrefixesPresent(argMultimap, PREFIX_ITEM) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(Item.MESSAGE_CONSTRAINTS);
        }

        String itemType = ParserUtil.parseItemType(argMultimap.getValue(PREFIX_ITEM).get());

        switch (itemType) {
        case ItemUtil.RESUME_ALIAS:
            return parseResume(argMultimap);
        case ItemUtil.INTERNSHIP_ALIAS:
            return parseInternship(argMultimap);
        case ItemUtil.PROJECT_ALIAS:
            return parseProject(argMultimap);
        case ItemUtil.SKILL_ALIAS:
            return parseSkill(argMultimap);
        case ItemUtil.NOTE_ALIAS:
            return parseNote(argMultimap);
        default:
            throw new AssertionError(UNREACHABLE_STATEMENT_REACHED);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the arguments in the context of adding a Resume item.
     */
    private AddResumeCommand parseResume(ArgumentMultimap argMultimap) throws ParseException {
        assert argMultimap != null;

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddResumeCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Resume resume = new Resume(name, tagList);
        return new AddResumeCommand(resume);
    }

    /**
     * Parses the arguments in the context of adding an Internship item.
     */
    private AddInternshipCommand parseInternship(ArgumentMultimap argMultimap) throws ParseException {
        assert argMultimap != null;

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_FROM, PREFIX_TO, PREFIX_ROLE, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddInternshipCommand.MESSAGE_USAGE));
        }
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Time from = ParserUtil.parseTime(argMultimap.getValue(PREFIX_FROM).get());
        Time to = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TO).get());

        if (from.compareTo(to) > 0) {
            throw new ParseException(AddInternshipCommand.MESSAGE_FROM_TO_MISORDER);
        }

        Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Internship internship = new Internship(name, role, from, to, description, tagList);
        return new AddInternshipCommand(internship);
    }

    /**
     * Parses the arguments in the context of adding a Project item.
     */
    private AddProjectCommand parseProject(ArgumentMultimap argMultimap) throws ParseException {
        assert argMultimap != null;

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TIME, PREFIX_WEBSITE, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddProjectCommand.MESSAGE_USAGE));
        }
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        Website website = ParserUtil.parseWebsite(argMultimap.getValue(PREFIX_WEBSITE).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Project project = new Project(name, time, website, description, tagList);
        return new AddProjectCommand(project);
    }


    /**
     * Parses the arguments in the context of adding a Skill item.
     */
    private AddSkillCommand parseSkill(ArgumentMultimap argMultimap) throws ParseException {
        assert argMultimap != null;

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_LEVEL) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddSkillCommand.MESSAGE_USAGE));
        }
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Level level = ParserUtil.parseLevel(argMultimap.getValue(PREFIX_LEVEL).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Skill skill = new Skill(name, level, tagList);
        return new AddSkillCommand(skill);
    }

    /**
     * Parses the arguments in the context of adding a Note item.
     */
    private AddNoteCommand parseNote(ArgumentMultimap argMultimap) throws ParseException {
        assert argMultimap != null;

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddNoteCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddNoteCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Time noteTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Note note = new Note(name, noteTime, tagList);
        return new AddNoteCommand(note);
    }
}
