package seedu.address.model.util;

import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.comment.Comment;
import seedu.address.model.itemtype.TypeOfItem;
import seedu.address.model.order.Address;
import seedu.address.model.order.CashOnDelivery;
import seedu.address.model.order.Email;
import seedu.address.model.order.Name;
import seedu.address.model.order.Order;
import seedu.address.model.order.Phone;
import seedu.address.model.order.TimeStamp;
import seedu.address.model.order.TransactionId;
import seedu.address.model.order.Warehouse;

/**
 * Contains utility methods for populating {@code OrderBook} with sample data.
 */
public class SampleDataUtil {
    //getTagSet("colleagues", "friends")),
    public static Order[] getSampleOrders() {
        return new Order[] {
            new Order(new TransactionId("B93838282"), new Name("Alex Yeoh"), new Phone("87438807"),
                    new Email("asdbc@gmail.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new TimeStamp("2019-02-20 1500"), new Warehouse("5 Toh Guan Rd E, S608831"),
                    new CashOnDelivery("$4.10"), new Comment("NIL"),
                    new TypeOfItem("Glass")),
            new Order(new TransactionId("A11111111"), new Name("Bernice Yu"), new Phone("99272758"),
                    new Email("asdbc@gmail.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new TimeStamp("2019-02-20 1500"), new Warehouse("5 Toh Guan Rd E, S608831"),
                    new CashOnDelivery("$4.20"), new Comment("NIL"),
                    new TypeOfItem("Glass")),
            new Order(new TransactionId("A4937272"), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Email("asdbc@gmail.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new TimeStamp("2019-02-20 1500"), new Warehouse("5 Toh Guan Rd E, S608831"),
                    new CashOnDelivery("$4.30"), new Comment("NIL"),
                    new TypeOfItem("Porcelain")),
            new Order(new TransactionId("A000000"), new Name("David Li"), new Phone("91031282"),
                    new Email("asdbc@gmail.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new TimeStamp("2019-02-20 1500"), new Warehouse("5 Toh Guan Rd E, S608831"),
                    new CashOnDelivery("$6"), new Comment("NIL"),
                    new TypeOfItem("Plastic")),
            new Order(new TransactionId("A99999"), new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new Email("asdbc@gmail.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new TimeStamp("2019-02-20 1500"), new Warehouse("5 Toh Guan Rd E, S608831"),
                    new CashOnDelivery("$1"), new Comment("NIL"),
                    new TypeOfItem("Metal")),
            new Order(new TransactionId("C8493929292"), new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Email("asdbc@gmail.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new TimeStamp("2019-02-20 1500"), new Warehouse("5 Toh Guan Rd E, S608831"),
                    new CashOnDelivery("$10"), new Comment("NIL"),
                    new TypeOfItem("Fabric"))
        };
    }

    public static Order[] getSampleReturnOrders() {
        return new Order[] {
            new Order(new TransactionId("B93838282"), new Name("Alex Yeoh"), new Phone("87438807"),
                    new Email("asdbc@gmail.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new TimeStamp("2019-02-20 1500"), new Warehouse("5 Toh Guan Rd E, S608831"),
                    new CashOnDelivery("$4.10"), new Comment("NIL"),
                    new TypeOfItem("Glass")),
            new Order(new TransactionId("A11111111"), new Name("Bernice Yu"), new Phone("99272758"),
                    new Email("asdbc@gmail.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new TimeStamp("2019-02-20 1500"), new Warehouse("5 Toh Guan Rd E, S608831"),
                    new CashOnDelivery("$4.20"), new Comment("NIL"),
                    new TypeOfItem("Glass")),
            new Order(new TransactionId("A4937272"), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Email("asdbc@gmail.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new TimeStamp("2019-02-20 1500"), new Warehouse("5 Toh Guan Rd E, S608831"),
                    new CashOnDelivery("$4.30"), new Comment("NIL"),
                    new TypeOfItem("Porcelain")),
            new Order(new TransactionId("A000000"), new Name("David Li"), new Phone("91031282"),
                    new Email("asdbc@gmail.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new TimeStamp("2019-02-20 1500"), new Warehouse("5 Toh Guan Rd E, S608831"),
                    new CashOnDelivery("$6"), new Comment("NIL"),
                    new TypeOfItem("Plastic")),
            new Order(new TransactionId("A99999"), new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new Email("asdbc@gmail.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new TimeStamp("2019-02-20 1500"), new Warehouse("5 Toh Guan Rd E, S608831"),
                    new CashOnDelivery("$1"), new Comment("NIL"),
                    new TypeOfItem("Metal")),
            new Order(new TransactionId("C8493929292"), new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Email("asdbc@gmail.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new TimeStamp("2019-02-20 1500"), new Warehouse("5 Toh Guan Rd E, S608831"),
                    new CashOnDelivery("$10"), new Comment("NIL"),
                    new TypeOfItem("Fabric"))
        };
    }

    public static ReadOnlyOrderBook getSampleOrderBook() {
        OrderBook sampleOb = new OrderBook();
        for (Order sampleOrder : getSampleOrders()) {
            sampleOb.addOrder(sampleOrder);
        }
        return sampleOb;
    }

    public static ReadOnlyOrderBook getSampleReturnOrderBook() {
        OrderBook sampleRob = new OrderBook();
        for (Order sampleOrder : getSampleReturnOrders()) {
            sampleRob.addOrder(sampleOrder);
        }
        return sampleRob;
    }

}
