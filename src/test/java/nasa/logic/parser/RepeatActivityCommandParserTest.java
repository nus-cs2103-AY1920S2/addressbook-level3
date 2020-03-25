package nasa.logic.parser;

import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CS1231;
import static nasa.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import nasa.commons.core.index.Index;
import nasa.logic.commands.RepeatCommand;
import nasa.model.activity.Name;
import nasa.model.module.ModuleCode;

class RepeatActivityCommandParserTest {

    private RepeatActivityCommandParser parser = new RepeatActivityCommandParser();
    private ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CS1231);
    private Name nameOfActivity = new Name("Project work");
    private Index index = Index.fromOneBased(1);

    @Test
    void commandParserTest() {
       assertParseSuccess(parser, " m/CS1231 a/Project work r/1",
               new RepeatCommand(moduleCode, nameOfActivity, index));
    }
}