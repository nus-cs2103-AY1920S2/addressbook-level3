package NASA.logic.commands.addcommands;

import NASA.model.Model;
import NASA.model.activity.Activity;
import NASA.model.activity.Deadline;
import NASA.testutil.ActivityBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import NASA.model.ModelManagerNasa;
import NASA.model.UserPrefsNasa;
import NASA.model.ModelNasa;
import NASA.model.NasaBook;

public class AddDeadlineCommandTest {

    private ModelNasa model;

    @BeforeEach
    public void setUp() {
        model = new ModelManagerNasa(new NasaBook(), new UserPrefsNasa());
    }

    @Test
    public void execute_newDeadline_success() {
        Activity deadline = new ActivityBuilder().build();

        ModelNasa expectedModel = new ModelManagerNasa(model.getNasaBook(), new UserPrefsNasa());
        //expectedModel.addActivity(deadline);
    }
}
