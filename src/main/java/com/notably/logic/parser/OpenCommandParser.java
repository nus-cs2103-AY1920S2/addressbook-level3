package com.notably.logic.parser;

import static com.notably.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.List;
import java.util.Optional;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.commands.OpenCommand;
import com.notably.logic.correction.AbsolutePathCorrectionEngine;
import com.notably.logic.parser.exceptions.ParseException;
import com.notably.model.Model;

/**
 * Represent a Parser for OpenCommand.
 */
public class OpenCommandParser implements CommandParser<OpenCommand> {
    private static final int DISTANCE_THRESHOLD = 2;

    private Model notablyModel;
    private AbsolutePathCorrectionEngine correctionEngine;

    public OpenCommandParser(Model notablyModel) {
        this.notablyModel = notablyModel;
        this.correctionEngine = new AbsolutePathCorrectionEngine(notablyModel, DISTANCE_THRESHOLD, false);
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
                throw new ParseException("Path cannot be empty");
            }
        } else {
            titles = argMultimap.getValue(PREFIX_TITLE).get().trim();
        }

        AbsolutePath uncorrectedPath = ParserUtil.createAbsolutePath(titles, notablyModel.getCurrentlyOpenPath());
        Optional<AbsolutePath> correctedPath = correctionEngine.correct(uncorrectedPath).getCorrectedItem();

        if (correctedPath.equals(Optional.empty())) {
            throw new ParseException("Invalid Path");
        }

        return List.of(new OpenCommand(correctedPath.get()));
    }
}
