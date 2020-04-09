package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalReturnOrders.getTypicalReturnOrderBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.parcel.order.Order;
import seedu.address.testutil.OrderBuilder;

//@author Amoscheong97
/**
 * Contains integration tests (interaction with the Model) for {@code InsertCommand}.
 *
 */
public class InsertCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
    }

    @Test
    public void execute_newOrder_success() {
        Order validOrder = new OrderBuilder().build();

        Model expectedModel = new ModelManager(model.getOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        expectedModel.addOrder(validOrder);

        assertCommandSuccess(new InsertCommand(validOrder), model,
                String.format(InsertCommand.MESSAGE_SUCCESS, validOrder), expectedModel);
    }

    @Test
    public void execute_duplicateOrder_throwsCommandException() {
        Order orderInList = model.getOrderBook().getOrderList().get(0);
        assertCommandFailure(new InsertCommand(orderInList), model, InsertCommand.MESSAGE_DUPLICATE_ORDER);
    }

    @Test
    public void execute_addNewDuplicateOrder_throwsCommandException() {
        Order orderInList = model.getOrderBook().getOrderList().get(0);
        String transaction = orderInList.getTid().tid;
        Order anotherOrder = (new OrderBuilder()).withTid(transaction).build();

        assertCommandFailure(new InsertCommand(anotherOrder), model, InsertCommand.MESSAGE_DUPLICATE_ORDER);
    }

    @Test
    public void execute_sameNameAddNewDuplicateOrder_throwsCommandException() {
        Order orderInList = model.getOrderBook().getOrderList().get(0);
        String transaction = orderInList.getTid().tid;
        String name = orderInList.getName().fullName;
        Order anotherOrder = (new OrderBuilder())
                .withTid(transaction)
                .withName(name)
                .build();

        assertCommandFailure(new InsertCommand(anotherOrder), model, InsertCommand.MESSAGE_DUPLICATE_ORDER);
    }

    @Test
    public void execute_someSameAttributesAddNewDuplicateOrder_throwsCommandException() {
        Order orderInList = model.getOrderBook().getOrderList().get(0);
        String transaction = orderInList.getTid().tid;
        String name = orderInList.getName().fullName;
        String cod = orderInList.getCash().cashOnDelivery;
        String address = orderInList.getAddress().value;
        Order anotherOrder = (new OrderBuilder())
                .withTid(transaction)
                .withName(name)
                .withAddress(address)
                .withCash(cod)
                .build();

        assertCommandFailure(new InsertCommand(anotherOrder), model, InsertCommand.MESSAGE_DUPLICATE_ORDER);
    }
}
