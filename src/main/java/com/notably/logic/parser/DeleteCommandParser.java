package com.notably.logic.parser;

import static com.notably.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.List;
import java.util.Optional;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.logic.commands.DeleteCommand;
import com.notably.logic.correction.AbsolutePathCorrectionEngine;
import com.notably.logic.parser.exceptions.ParseException;
import com.notably.model.Model;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements CommandParser<DeleteCommand> {
    private Model notablyModel;
    private AbsolutePathCorrectionEngine correctionEngine;

    public DeleteCommandParser(Model notablyModel) {
        this.notablyModel = notablyModel;
        this.correctionEngine = new AbsolutePathCorrectionEngine(notablyModel, 2);
    }
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public List<DeleteCommand> parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format("Invalid Command"));
        }

        String title = argMultimap.getValue(PREFIX_TITLE).get();
        AbsolutePath uncorrectedPath = ParserUtil.createAbsolutePath(title, notablyModel.getCurrentlyOpenPath());
        Optional<AbsolutePath> correctedPath = correctionEngine.correct(uncorrectedPath).getCorrectedItem();
        if (correctedPath.equals(Optional.empty())) {
            throw new ParseException("Invalid Path");
        }
        return List.of(new DeleteCommand(correctedPath.get()));
    }
}
