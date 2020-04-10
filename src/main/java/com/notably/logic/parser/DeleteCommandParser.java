package com.notably.logic.parser;

import static com.notably.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import java.util.List;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.commands.DeleteCommand;
import com.notably.logic.correction.CorrectionEngine;
import com.notably.logic.correction.CorrectionResult;
import com.notably.logic.correction.CorrectionStatus;
import com.notably.logic.parser.exceptions.ParseException;
import com.notably.model.Model;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements CommandParser<DeleteCommand> {
    private Model notablyModel;
    private CorrectionEngine<AbsolutePath> pathCorrectionEngine;

    public DeleteCommandParser(Model notablyModel, CorrectionEngine<AbsolutePath> pathCorrectionEngine) {
        this.notablyModel = notablyModel;
        this.pathCorrectionEngine = pathCorrectionEngine;
    }
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public List<DeleteCommand> parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE);

        String title;
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            title = args.trim();
            if (title.isEmpty()) {
                throw new ParseException("Path cannot be empty");
            }
        } else {
            title = argMultimap.getValue(PREFIX_TITLE).get();
        }

        AbsolutePath uncorrectedPath = ParserUtil.createAbsolutePath(title, notablyModel.getCurrentlyOpenPath());
        CorrectionResult<AbsolutePath> correctionResult = pathCorrectionEngine.correct(uncorrectedPath);
        if (correctionResult.getCorrectionStatus() == CorrectionStatus.FAILED) {
            throw new ParseException("Invalid Path");
        }

        return List.of(new DeleteCommand(correctionResult.getCorrectedItems().get(0)));
    }
}
