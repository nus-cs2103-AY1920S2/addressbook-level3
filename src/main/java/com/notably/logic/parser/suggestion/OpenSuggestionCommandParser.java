package com.notably.logic.parser.suggestion;

import static com.notably.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Optional;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.commands.suggestion.OpenSuggestionCommand;
import com.notably.logic.commands.suggestion.SuggestionCommand;
import com.notably.logic.correction.CorrectionEngine;
import com.notably.logic.correction.CorrectionResult;
import com.notably.logic.correction.CorrectionStatus;
import com.notably.logic.parser.ArgumentMultimap;
import com.notably.logic.parser.ArgumentTokenizer;
import com.notably.logic.parser.ParserUtil;
import com.notably.logic.parser.exceptions.ParseException;
import com.notably.model.Model;

/**
 * Represents a Parser for DeleteSuggestionCommand.
 */
public class OpenSuggestionCommandParser implements SuggestionCommandParser<SuggestionCommand> {
    private static final String RESPONSE_MESSAGE = "Open a note";

    private Model model;
    private CorrectionEngine<AbsolutePath> pathCorrectionEngine;

    public OpenSuggestionCommandParser(Model model, CorrectionEngine<AbsolutePath> pathCorrectionEngine) {
        this.model = model;
        this.pathCorrectionEngine = pathCorrectionEngine;
    }

    /**
     * Parses user input in the context of the OpenSuggestionCommand.
     * @param userInput The user's input.
     * @return An optional OpenSuggestionCommand object with a corrected absolute path.
     */
    @Override
    public Optional<SuggestionCommand> parse(String userInput) {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_TITLE);

        String title;
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            title = userInput.trim();
        } else {
            title = argMultimap.getValue(PREFIX_TITLE).get();
        }

        if (title.isEmpty()) {
            model.setResponseText(RESPONSE_MESSAGE);
            return Optional.empty();
        } else {
            AbsolutePath uncorrectedPath;
            try {
                uncorrectedPath = ParserUtil.createAbsolutePath(title, model.getCurrentlyOpenPath());
            } catch (ParseException pe) {
                model.setResponseText("Cannot open \"" + title + "\". Invalid path.");
                return Optional.empty();
            }

            model.setResponseText(RESPONSE_MESSAGE + " entitled \"" + title + "\"");

            CorrectionResult<AbsolutePath> correctionResult = pathCorrectionEngine.correct(uncorrectedPath);
            if (correctionResult.getCorrectionStatus() == CorrectionStatus.FAILED) {
                return Optional.empty();
            }

            // TODO: Pass in the list of corrected items and create suggestions based on that
            return Optional.of(new OpenSuggestionCommand(correctionResult.getCorrectedItems().get(0), title));
        }
    }
}
