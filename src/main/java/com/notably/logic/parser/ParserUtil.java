package com.notably.logic.parser;

import java.util.stream.Stream;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.commons.core.path.Path;
import com.notably.commons.core.path.RelativePath;

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
    public static Path createPath(String userInput) {
        if (userInput.charAt(0) == '/') {
            return AbsolutePath.fromString(userInput);
        } else {
            return RelativePath.fromString(userInput);
        }
    }
}
