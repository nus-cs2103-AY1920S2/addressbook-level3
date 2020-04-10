package nasa.logic.parser;

import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1231;
import static nasa.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import nasa.commons.core.index.Index;
import nasa.logic.commands.RepeatDeadlineCommand;
import nasa.model.module.ModuleCode;

class RepeatActivityCommandParserTest {

    private RepeatDeadlineCommandParser parser = new RepeatDeadlineCommandParser();
    private ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE_CS1231);
    private Index index = Index.fromZeroBased(1);

    @Test
    void commandParserTest() {
        assertParseSuccess(parser, "1 m/CS1231 r/1",
               new RepeatDeadlineCommand(moduleCode, Index.fromOneBased(1), index));
    }
}
