package com.notably.logic.parser;

import java.util.stream.Stream;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.commons.core.path.RelativePath;
import com.notably.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns either a AbsolutePath or RelativePath base on userInput.
     */
    public static AbsolutePath createAbsolutePath(String text, AbsolutePath currentDirectory) throws ParseException {
        if (AbsolutePath.isValidAbsolutePath(text)) {
            return AbsolutePath.fromString(text);
        }
        if (RelativePath.isValidRelativePath(text)) {
            return RelativePath.fromString(text).toAbsolutePath(currentDirectory);
        }
        throw new ParseException("Invalid Path");
    }
}
