package seedu.address.model.util;

import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.ReadOnlyReturnOrderBook;
import seedu.address.model.ReturnOrderBook;
import seedu.address.model.Parcel.comment.Comment;
import seedu.address.model.Parcel.itemtype.TypeOfItem;
import seedu.address.model.Parcel.ParcelAttributes.Address;
import seedu.address.model.Parcel.order.CashOnDelivery;
import seedu.address.model.Parcel.ParcelAttributes.Email;
import seedu.address.model.Parcel.ParcelAttributes.Name;
import seedu.address.model.Parcel.order.Order;
import seedu.address.model.Parcel.ParcelAttributes.Phone;
import seedu.address.model.Parcel.ParcelAttributes.TimeStamp;
import seedu.address.model.Parcel.ParcelAttributes.TransactionId;
import seedu.address.model.Parcel.ParcelAttributes.Warehouse;
import seedu.address.model.Parcel.returnorder.ReturnOrder;


/**
 * Contains utility methods for populating {@code OrderBook} with sample data.
 */
public class SampleDataUtil {
    //getTagSet("colleagues", "friends")),
    public static Order[] getSampleOrders() {
        return new Order[] {
            new Order(new TransactionId("B93838282"),
                    new Name("Alex Yeoh"),
                    new Phone("87438807"),
                    new Email("asdbc@gmail.com"),
                    new Address("123 Jurong West Ave 6 #08-111 S649520"),
                    new TimeStamp("2022-02-20 1500"),
                    new Warehouse("5 Toh Guan Rd E S608831"),
                    new CashOnDelivery("$4.10"),
                    new Comment("NIL"),
                    new TypeOfItem("Glass")),
            new Order(new TransactionId("A11111111"),
                    new Name("Bernice Yu"),
                    new Phone("99272758"),
                    new Email("asdbc@gmail.com"),
                    new Address("311 Clementi Ave 2 #02-25 S120363"),
                    new TimeStamp("2022-02-20 1500"),
                    new Warehouse("5 Toh Guan Rd E S608831"),
                    new CashOnDelivery("$4.20"),
                    new Comment("NIL"),
                    new TypeOfItem("Glass")),
            new Order(new TransactionId("A4937272"),
                    new Name("Charlotte Oliveiro"),
                    new Phone("93210283"),
                    new Email("asdbc@gmail.com"),
                    new Address("Telok Blangah Heights #01-22 S100058"),
                    new TimeStamp("2022-02-20 1500"),
                    new Warehouse("5 Toh Guan Rd E S608831"),
                    new CashOnDelivery("$4.30"),
                    new Comment("NIL"),
                    new TypeOfItem("Porcelain")),
            new Order(new TransactionId("A000000"),
                    new Name("David Li"),
                    new Phone("91031282"),
                    new Email("asdbc@gmail.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26 #16-43 S550101"),
                    new TimeStamp("2022-02-20 1500"),
                    new Warehouse("5 Toh Guan Rd E S608831"),
                    new CashOnDelivery("$6"),
                    new Comment("NIL"),
                    new TypeOfItem("Plastic")),
            new Order(new TransactionId("A99999"),
                    new Name("Irfan Ibrahim"),
                    new Phone("92492021"),
                    new Email("asdbc@gmail.com"),
                    new Address("Blk 47 Tampines Street 20 #17-35 S506901"),
                    new TimeStamp("2022-02-20 1500"),
                    new Warehouse("5 Toh Guan Rd E S608831"),
                    new CashOnDelivery("$1"),
                    new Comment("NIL"),
                    new TypeOfItem("Metal")),
            new Order(new TransactionId("C8493929292"),
                    new Name("Roy Balakrishnan"),
                    new Phone("92624417"),
                    new Email("asdbc@gmail.com"),
                    new Address("Blk 45 Aljunied Street 85 #11-31 S380095"),
                    new TimeStamp("2022-02-20 1500"),
                    new Warehouse("5 Toh Guan Rd E S608831"),
                    new CashOnDelivery("$10"),
                    new Comment("NIL"),
                    new TypeOfItem("Fabric"))
        };
    }

    public static ReturnOrder[] getSampleReturnOrders() {
        return new ReturnOrder[] {
            new ReturnOrder(new TransactionId("B93838282"),
                    new Name("Alex Yeoh"),
                    new Phone("87438807"),
                    new Email("asdbc@gmail.com"),
                    new Address("Blk 30 Geylang Street 29 #06-40 S388192"),
                    new TimeStamp("2022-02-20 1500"),
                    new Warehouse("5 Toh Guan Rd E S608831"),
                    new Comment("NIL"),
                    new TypeOfItem("Glass")),
            new ReturnOrder(new TransactionId("A11111111"),
                    new Name("Bernice Yu"),
                    new Phone("99272758"),
                    new Email("asdbc@gmail.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens #07-18 S556053"),
                    new TimeStamp("2022-02-20 1500"),
                    new Warehouse("5 Toh Guan Rd E S608831"),
                    new Comment("NIL"),
                    new TypeOfItem("Glass")),
            new ReturnOrder(new TransactionId("A4937272"),
                    new Name("Charlotte Oliveiro"),
                    new Phone("93210283"),
                    new Email("asdbc@gmail.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74 #11-04 S560101"),
                    new TimeStamp("2022-02-20 1500"),
                    new Warehouse("5 Toh Guan Rd E S608831"),
                    new Comment("NIL"),
                    new TypeOfItem("Porcelain")),
            new ReturnOrder(new TransactionId("A000000"),
                    new Name("David Li"),
                    new Phone("91031282"),
                    new Email("asdbc@gmail.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26 #16-43 S556053"),
                    new TimeStamp("2022-02-20 1500"),
                    new Warehouse("5 Toh Guan Rd E S608831"),
                    new Comment("NIL"),
                    new TypeOfItem("Plastic")),
            new ReturnOrder(new TransactionId("A99999"),
                    new Name("Irfan Ibrahim"),
                    new Phone("92492021"),
                    new Email("asdbc@gmail.com"),
                    new Address("Blk 47 Tampines Street 20 #17-35 S520201"),
                    new TimeStamp("2022-02-20 1500"),
                    new Warehouse("5 Toh Guan Rd E S608831"),
                    new Comment("NIL"),
                    new TypeOfItem("Metal")),
            new ReturnOrder(new TransactionId("C8493929292"),
                    new Name("Roy Balakrishnan"),
                    new Phone("92624417"),
                    new Email("asdbc@gmail.com"),
                    new Address("Blk 45 Aljunied Street 85 #11-31 S389825"),
                    new TimeStamp("2022-02-20 1500"),
                    new Warehouse("5 Toh Guan Rd E S608831"),
                    new Comment("NIL"),
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

    public static ReadOnlyReturnOrderBook getSampleReturnOrderBook() {
        ReturnOrderBook sampleRob = new ReturnOrderBook();
        for (ReturnOrder sampleOrder : getSampleReturnOrders()) {
            sampleRob.addReturnOrder(sampleOrder);
        }
        return sampleRob;
    }

}
