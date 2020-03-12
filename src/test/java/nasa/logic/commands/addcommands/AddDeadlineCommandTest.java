package nasa.logic.commands.addcommands;

import nasa.model.Model;
import nasa.model.activity.Activity;
import nasa.model.activity.Deadline;
import nasa.testutil.ActivityBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import nasa.model.ModelManager;
import nasa.model.UserPrefs;
import nasa.model.Model;
import nasa.model.NasaBook;

public class AddDeadlineCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new NasaBook(), new UserPrefs());
    }

    @Test
    public void execute_newDeadline_success() {
        Activity deadline = new ActivityBuilder().build();

        Model expectedModel = new ModelManager(model.getNasaBook(), new UserPrefs());
        //expectedModel.addActivity(deadline);
    }
}
