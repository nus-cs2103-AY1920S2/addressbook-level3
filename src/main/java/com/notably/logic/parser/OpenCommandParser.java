package com.notably.logic.parser;

import static com.notably.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.List;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.commands.OpenCommand;
import com.notably.logic.correction.CorrectionEngine;
import com.notably.logic.correction.CorrectionResult;
import com.notably.logic.correction.CorrectionStatus;
import com.notably.logic.parser.exceptions.ParseException;
import com.notably.model.Model;

/**
 * Represent a Parser for OpenCommand.
 */
public class OpenCommandParser implements CommandParser<OpenCommand> {
    private static final String ERROR_PATH = "The Path \"%s\" does not exist in the storage. "
            + "Please provide a valid Path";
    private static final String ERROR_EMPTY_PATH = "Empty Path detected. "
            + "Please provide a valid Path";

    private Model notablyModel;
    private CorrectionEngine<AbsolutePath> pathCorrectionEngine;

    public OpenCommandParser(Model notablyModel, CorrectionEngine<AbsolutePath> pathCorrectionEngine) {
        this.notablyModel = notablyModel;
        this.pathCorrectionEngine = pathCorrectionEngine;
    }
    /**
     * Creates OpenCommand with user input.
     * @param args to be parse by into CorrectionEngine.
     * @return List of command containing OpenCammnd.
     * @throws ParseException
     */
    public List<OpenCommand> parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE);

        String titles;
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            titles = args.trim();
            if (titles.isEmpty()) {
                throw new ParseException(ERROR_EMPTY_PATH);
            }
        } else {
            titles = argMultimap.getValue(PREFIX_TITLE).get();
        }

        AbsolutePath uncorrectedPath = ParserUtil.createAbsolutePath(titles, notablyModel.getCurrentlyOpenPath());
        CorrectionResult<AbsolutePath> correctionResult = pathCorrectionEngine.correct(uncorrectedPath);
        if (correctionResult.getCorrectionStatus() == CorrectionStatus.FAILED) {
            throw new ParseException(String.format(ERROR_PATH, titles));
        }

        return List.of(new OpenCommand(correctionResult.getCorrectedItems().get(0)));
    }
}
