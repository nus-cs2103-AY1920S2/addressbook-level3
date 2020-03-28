package com.notably.commons.compiler.tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.notably.commons.compiler.tokenizer.exceptions.UnknownTokenException;
import com.notably.commons.compiler.tokenizer.rule.CarriageReturnRule;
import com.notably.commons.compiler.tokenizer.rule.HashRule;
import com.notably.commons.compiler.tokenizer.rule.LineFeedRule;
import com.notably.commons.compiler.tokenizer.rule.MinusRule;
import com.notably.commons.compiler.tokenizer.rule.Rule;
import com.notably.commons.compiler.tokenizer.rule.SpaceRule;
import com.notably.commons.compiler.tokenizer.rule.TextRule;

/**
 * A class capable of breaking down a plain string into a sequence of {@link Token}s.
 */
public class Tokenizer {
    private static final List<Rule> RULES = List.of(
            new MinusRule(),
            new HashRule(),
            new SpaceRule(),
            new CarriageReturnRule(),
            new LineFeedRule(),
            new TextRule()
    );

    /**
     * Breaks down a string input into a list of {@link Token}s.
     *
     * @param input A string input
     * @return A list of {@link Token}s
     */
    public static List<Token> tokenize(String input) {
        String remainingInput = input;
        List<Token> tokens = new ArrayList<>();

        while (remainingInput.length() > 0) {
            int ruleIndex = 0;
            Optional<Token> nextTokenOptional = RULES.get(ruleIndex).extractFront(remainingInput);
            while (nextTokenOptional.isEmpty() && ruleIndex < RULES.size()) {
                nextTokenOptional = RULES.get(ruleIndex).extractFront(remainingInput);
                ruleIndex += 1;
            }

            Token nextToken = nextTokenOptional.orElseThrow(UnknownTokenException::new);
            tokens.add(nextToken);

            remainingInput = remainingInput.substring(nextToken.getLength());
        }

        tokens.add(Token.EOF);

        return tokens;
    }
}

