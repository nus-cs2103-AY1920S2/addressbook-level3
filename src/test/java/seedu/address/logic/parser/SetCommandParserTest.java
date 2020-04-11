package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAILY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SetCommand;
import seedu.address.model.settings.DailyTarget;
import seedu.address.model.settings.PetName;
import seedu.address.model.settings.PomDuration;

public class SetCommandParserTest {
    // private SetCommandParser parser = new SetCommandParser();

    private String VALID_PETNAME = "BB";
    private String VALID_POM = "30";
    private String VALID_DAILY = "150";

    // @Test
    // public void parse_allFieldsPresent_success() {
    //     SetCommandParser parser = new SetCommandParser();
    //     // SetCommand expectedSetCommand = new SetCommand(new PetName(VALID_PETNAME) ,   new PomDuration(VALID_POM) , new DailyTarget(VALID_DAILY));
    //     SetCommand expectedSetCommand = new SetCommand(new PetName(VALID_PETNAME), new PomDuration(""), new DailyTarget(""));

    //     assertParseSuccess(
    //         parser,
    //         " pet/BB",
    //         expectedSetCommand);
    // }
}