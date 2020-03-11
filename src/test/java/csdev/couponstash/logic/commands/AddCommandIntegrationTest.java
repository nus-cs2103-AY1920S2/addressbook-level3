package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandFailure;
import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;

import csdev.couponstash.testutil.PersonBuilder;
import csdev.couponstash.testutil.TypicalPersons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;
import csdev.couponstash.model.UserPrefs;
import csdev.couponstash.model.coupon.Coupon;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Coupon validCoupon = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validCoupon);

        assertCommandSuccess(new AddCommand(validCoupon), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validCoupon), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Coupon couponInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(couponInList), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
