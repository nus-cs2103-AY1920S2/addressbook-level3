//package com.notably.logic.parser;
//
//import static com.notably.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static com.notably.logic.parser.CliSyntax.PREFIX_EMAIL;
//import static com.notably.logic.parser.CliSyntax.PREFIX_NAME;
//import static com.notably.logic.parser.CliSyntax.PREFIX_PHONE;
//import static com.notably.logic.parser.CliSyntax.PREFIX_TAG;
//import static java.util.Objects.requireNonNull;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Optional;
//import java.util.Set;
//
//import com.notably.commons.core.index.Index;
//import com.notably.logic.commands.EditCommand;
//import com.notably.logic.commands.EditCommand.EditPersonDescriptor;
//import com.notably.logic.parser.exceptions.ParseException;
//import com.notably.model.tag.Tag;
//
///**
// * Parses input arguments and creates a new EditCommand object
// */
//public class EditCommandParser implements CommandParser<EditCommand> {
//
//    /**
//     * Parses the given {@code String} of arguments in the context of the EditCommand
//     * and returns an EditCommand object for execution.
//     * @throws ParseException if the user input does not conform the expected format
//     */
//    public EditCommand parse(String args) throws ParseException {
//        requireNonNull(args);
//        ArgumentMultimap argMultimap =
//                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
//
//        Index index;
//
//        try {
//            index = ParserUtil.parseIndex(argMultimap.getPreamble());
//        } catch (ParseException pe) {
//            throw new ParseException("MESSAGE_INVALID_COMMAND_FORMAT", pe);
//        }
//
//        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
//        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
//        }
//        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
//        }
//        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
//        }
//        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
//        }
//        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
//
//        return new EditCommand(index, editPersonDescriptor);
//    }
//
//    /**
//     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
//     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
//     * {@code Set<Tag>} containing zero tags.
//     */
//    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
//        assert tags != null;
//
//        if (tags.isEmpty()) {
//            return Optional.empty();
//        }
//        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
//        return Optional.of(ParserUtil.parseTags(tagSet));
//    }
//
//}
