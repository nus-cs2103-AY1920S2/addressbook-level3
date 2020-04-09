package seedu.expensela.logic.parser;

import static seedu.expensela.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.expensela.logic.commands.BudgetCommand;

public class BudgetCommandParserTest {

    private BudgetCommandParser parser = new BudgetCommandParser();

    @Test
    public void parse_allFieldsPresentNonRecurring_success() {
        assertParseSuccess(parser, " b/1500", new BudgetCommand(1500.0, false));
    }
}
