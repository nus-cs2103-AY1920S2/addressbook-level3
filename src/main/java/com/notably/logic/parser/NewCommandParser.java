package com.notably.logic.parser;

import static com.notably.logic.parser.CliSyntax.PREFIX_BODY;
import static com.notably.logic.parser.CliSyntax.PREFIX_JUMP;
import static com.notably.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.stream.Stream;

import com.notably.logic.commands.NewCommand;
import com.notably.logic.parser.exceptions.ParseException;
import com.notably.model.block.Block;
import com.notably.model.block.BlockStub;

public class NewCommandParser implements CommandParser<NewCommand>{
    public NewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_BODY, PREFIX_JUMP);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_BODY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format("Invalid Command"));
        }

        String title= argMultimap.getValue(PREFIX_TITLE).get();
        String phone = argMultimap.getValue(PREFIX_BODY).get();

        Block block = new BlockStub(title, phone);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_BODY, PREFIX_JUMP)) {
            return new NewCommand(block, true);
        }
        return new NewCommand(block, false);
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
