package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.logic.commands.EditCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;
import csdev.couponstash.model.coupon.savings.Savings;
import csdev.couponstash.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {
    private final String moneySymbol;

    /**
     * Constructor for a EditCommandParser. Requires the
     * money symbol set in UserPrefs as this will be
     * used as the prefix for the monetary amount
     * in the savings field.
     *
     * @param moneySymbol String representing the money symbol.
     */
    public EditCommandParser(String moneySymbol) {
        this.moneySymbol = moneySymbol;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args,
                        CliSyntax.PREFIX_NAME,
                        CliSyntax.PREFIX_PROMO_CODE,
                        CliSyntax.PREFIX_EXPIRY_DATE,
                        CliSyntax.PREFIX_START_DATE,
                        CliSyntax.PREFIX_SAVINGS,
                        CliSyntax.PREFIX_USAGE,
                        CliSyntax.PREFIX_LIMIT,
                        CliSyntax.PREFIX_TAG,
                        CliSyntax.PREFIX_REMIND,
                        CliSyntax.PREFIX_CONDITION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(
                            pe.getMessage() + "\n\n" + MESSAGE_INVALID_COMMAND_FORMAT,
                            EditCommand.MESSAGE_USAGE
                    ),
                    pe
            );
        }

        EditCommand.EditCouponDescriptor editCouponDescriptor = new EditCommand.EditCouponDescriptor();
        if (argMultimap.getValue(CliSyntax.PREFIX_NAME).isPresent()) {
            editCouponDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_PROMO_CODE).isPresent()) {
            editCouponDescriptor.setPromoCode(
                    ParserUtil.parsePromoCode(argMultimap.getValue(CliSyntax.PREFIX_PROMO_CODE).get()));
        }

        parseSavingsForEdit(argMultimap.getAllValues(CliSyntax.PREFIX_SAVINGS))
                .ifPresent(editCouponDescriptor::setSavings);

        if (argMultimap.getValue(CliSyntax.PREFIX_EXPIRY_DATE).isPresent()) {
            editCouponDescriptor.setExpiryDate(ParserUtil.parseExpiryDate(argMultimap
                    .getValue(CliSyntax.PREFIX_EXPIRY_DATE).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_REMIND).isPresent()) {
            editCouponDescriptor.setRemindDate(ParserUtil.parseRemindDate(argMultimap
                    .getValue(CliSyntax.PREFIX_REMIND).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_START_DATE).isPresent()) {
            editCouponDescriptor.setStartDate(ParserUtil.parseStartDate(argMultimap
                    .getValue(CliSyntax.PREFIX_START_DATE).get()));
        }

        // usage is not allowed to be edited due to potential changes in savings
        if (argMultimap.getValue(CliSyntax.PREFIX_USAGE).isPresent()) {
            throw new ParseException(EditCommand.MESSAGE_CANNOT_EDIT_USAGE);
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_LIMIT).isPresent()) {
            editCouponDescriptor.setLimit(ParserUtil.parseLimit(argMultimap
                    .getValue(CliSyntax.PREFIX_LIMIT).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_CONDITION).isPresent()) {
            editCouponDescriptor.setCondition(ParserUtil.parseCondition(argMultimap
                    .getValue(CliSyntax.PREFIX_CONDITION).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(CliSyntax.PREFIX_TAG))
                .ifPresent(editCouponDescriptor::setTags);

        if (!editCouponDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_CONDITION).isPresent()) {
            editCouponDescriptor.setCondition(ParserUtil.parseCondition(argMultimap
                    .getValue(CliSyntax.PREFIX_CONDITION).get()));
        }

        return new EditCommand(index, editCouponDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Parses {@code Collection<String> savings} into a {@code Collection<String>} if {@code savings}
     * is non-empty. If {@code savings} contain only elements which are empty strings, it will be
     * parsed into an Optional.empty().
     *
     * @param savings The Collection of Strings to be checked.
     * @return Returns an Optional possibly containing the
     * non-empty Collection of Strings.
     * @throws ParseException If the Collection received has both
     *                        a monetary amount and an percentage amount, or has
     *                        blank Strings.
     */
    private Optional<Savings> parseSavingsForEdit(Collection<String> savings) throws ParseException {
        assert savings != null;

        if (savings.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(ParserUtil.parseSavings(savings, this.moneySymbol));
        }
    }

}
