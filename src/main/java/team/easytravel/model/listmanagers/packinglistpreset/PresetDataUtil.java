package team.easytravel.model.listmanagers.packinglistpreset;

import team.easytravel.model.listmanagers.packinglistitem.ItemCategory;
import team.easytravel.model.listmanagers.packinglistitem.ItemName;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.listmanagers.packinglistitem.Quantity;

/**
 * The type Preset data util.
 */
public class PresetDataUtil {
    /**
     * Hiking preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] hikingPreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Backpack"), new Quantity(1),
                    new ItemCategory("hiking"), false),
            new PackingListItem(new ItemName("Binoculars"), new Quantity(1),
                    new ItemCategory("hiking"), false),
            new PackingListItem(new ItemName("Bug spray"), new Quantity(1),
                    new ItemCategory("hiking"), false),
            new PackingListItem(new ItemName("First Aid Kit"), new Quantity(1),
                    new ItemCategory("hiking"), false),
            new PackingListItem(new ItemName("Hiking shoes"), new Quantity(1),
                    new ItemCategory("hiking"), false),
            new PackingListItem(new ItemName("Water Bottle"), new Quantity(1),
                    new ItemCategory("hiking"), false)
        };
    }

    /**
     * Clothes female preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] clothesFemalePreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Belt"), new Quantity(1),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("Bras"), new Quantity(7),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("Dress"), new Quantity(3),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("Jacket"), new Quantity(2),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("Jeans"), new Quantity(3),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("Pyjamas"), new Quantity(5),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("Shoes"), new Quantity(2),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("Shorts"), new Quantity(3),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("Skirt"), new Quantity(2),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("Socks"), new Quantity(7),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("T shirt"), new Quantity(7),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("Underwear"), new Quantity(7),
                    new ItemCategory("clothes"), false)
        };
    }

    /**
     * Clothes male preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] clothesMalePreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Belt"), new Quantity(1),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("Jacket"), new Quantity(2),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("Jeans"), new Quantity(4),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("Pyjamas"), new Quantity(5),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("Shoes"), new Quantity(2),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("Shorts"), new Quantity(5),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("Socks"), new Quantity(7),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("T shirt"), new Quantity(7),
                    new ItemCategory("clothes"), false),
            new PackingListItem(new ItemName("Underwear"), new Quantity(7),
                    new ItemCategory("clothes"), false)
        };
    }

    /**
     * Swimming preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] swimmingPreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Goggles"), new Quantity(1),
                    new ItemCategory("swimming"), false),
            new PackingListItem(new ItemName("Sandals"), new Quantity(1),
                    new ItemCategory("swimming"), false),
            new PackingListItem(new ItemName("Swimsuit"), new Quantity(1),
                    new ItemCategory("swimming"), false),
            new PackingListItem(new ItemName("Swimsuit Coverup"), new Quantity(1),
                    new ItemCategory("swimming"), false),
            new PackingListItem(new ItemName("Towel"), new Quantity(1),
                    new ItemCategory("swimming"), false)
        };
    }

    /**
     * Car preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] carPreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Car Keys"), new Quantity(1),
                    new ItemCategory("car"), false),
            new PackingListItem(new ItemName("Car Papers"), new Quantity(1),
                    new ItemCategory("car"), false),
            new PackingListItem(new ItemName("Driving Licence"), new Quantity(1),
                    new ItemCategory("car"), false)
        };
    }

    /**
     * Train preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] trainPreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Book"), new Quantity(3),
                    new ItemCategory("train"), false),
            new PackingListItem(new ItemName("Train Tickets"), new Quantity(1),
                    new ItemCategory("train"), false)
        };
    }

    /**
     * Boat preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] cruisePreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Cruise Tickets"), new Quantity(1),
                    new ItemCategory("cruise"), false),
            new PackingListItem(new ItemName("Seasickness Pills"), new Quantity(1),
                    new ItemCategory("cruise"), false)
        };
    }

    /**
     * Work preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] workPreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Business Cards"), new Quantity(10),
                    new ItemCategory("work"), false),
            new PackingListItem(new ItemName("Laptop"), new Quantity(1),
                    new ItemCategory("work"), false),
            new PackingListItem(new ItemName("Laptop Charger"), new Quantity(1),
                    new ItemCategory("work"), false),
            new PackingListItem(new ItemName("Work Badge"), new Quantity(1),
                    new ItemCategory("work"), false),
            new PackingListItem(new ItemName("Work Phone"), new Quantity(1),
                    new ItemCategory("work"), false),
            new PackingListItem(new ItemName("Work Phone Charger"), new Quantity(1),
                    new ItemCategory("work"), false)
        };
    }

    /**
     * Aeroplane preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] aeroplanePreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Boarding Pass"), new Quantity(1),
                    new ItemCategory("aeroplane"), false),
            new PackingListItem(new ItemName("Eye mask"), new Quantity(1),
                    new ItemCategory("aeroplane"), false),
            new PackingListItem(new ItemName("Headphones"), new Quantity(1),
                    new ItemCategory("aeroplane"), false),
            new PackingListItem(new ItemName("Neck Pillow"), new Quantity(1),
                    new ItemCategory("aeroplane"), false),
            new PackingListItem(new ItemName("Passport"), new Quantity(1),
                    new ItemCategory("aeroplane"), false),
            new PackingListItem(new ItemName("Snacks"), new Quantity(3),
                    new ItemCategory("aeroplane"), false)
        };
    }

    /**
     * Essentials preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] essentialsPreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Camera"), new Quantity(1),
                    new ItemCategory("essentials"), false),
            new PackingListItem(new ItemName("Flashlight"), new Quantity(1),
                    new ItemCategory("essentials"), false),
            new PackingListItem(new ItemName("Backpack"), new Quantity(1),
                    new ItemCategory("essentials"), false),
            new PackingListItem(new ItemName("Headache Pills"), new Quantity(1),
                    new ItemCategory("essentials"), false),
            new PackingListItem(new ItemName("House Key"), new Quantity(1),
                    new ItemCategory("essentials"), false),
            new PackingListItem(new ItemName("Handphone"), new Quantity(1),
                    new ItemCategory("essentials"), false),
            new PackingListItem(new ItemName("Handphone charger"), new Quantity(1),
                    new ItemCategory("essentials"), false),
            new PackingListItem(new ItemName("Medication"), new Quantity(1),
                    new ItemCategory("essentials"), false),
            new PackingListItem(new ItemName("Powerbank"), new Quantity(1),
                    new ItemCategory("essentials"), false),
            new PackingListItem(new ItemName("Sanitary Pads"), new Quantity(1),
                    new ItemCategory("essentials"), false),
            new PackingListItem(new ItemName("Umbrella"), new Quantity(1),
                    new ItemCategory("essentials"), false),
            new PackingListItem(new ItemName("Vitamins"), new Quantity(1),
                    new ItemCategory("essentials"), false),
            new PackingListItem(new ItemName("Wallet"), new Quantity(1),
                    new ItemCategory("essentials"), false),
            new PackingListItem(new ItemName("Watch"), new Quantity(1),
                    new ItemCategory("essentials"), false),
            new PackingListItem(new ItemName("Water Bottle"), new Quantity(1),
                    new ItemCategory("essentials"), false)
        };
    }

    /**
     * International preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] internationalPreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Foreign Currency"), new Quantity(1),
                    new ItemCategory("international"), false),
            new PackingListItem(new ItemName("Medical Insurance Card"), new Quantity(1),
                    new ItemCategory("international"), false),
            new PackingListItem(new ItemName("Passport"), new Quantity(1),
                    new ItemCategory("international"), false),
            new PackingListItem(new ItemName("Pen"), new Quantity(1),
                    new ItemCategory("international"), false),
            new PackingListItem(new ItemName("Power Adapter"), new Quantity(1),
                    new ItemCategory("international"), false),
            new PackingListItem(new ItemName("Travel Guide"), new Quantity(1),
                    new ItemCategory("international"), false),
            new PackingListItem(new ItemName("Vaccination Certificates"), new Quantity(1),
                    new ItemCategory("international"), false),
            new PackingListItem(new ItemName("Visa"), new Quantity(1),
                    new ItemCategory("international"), false)
        };
    }

    /**
     * Camping preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] campingPreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Cooking tools"), new Quantity(1),
                    new ItemCategory("camping"), false),
            new PackingListItem(new ItemName("Flashlight"), new Quantity(1),
                    new ItemCategory("camping"), false),
            new PackingListItem(new ItemName("Headlamp"), new Quantity(1),
                    new ItemCategory("camping"), false),
            new PackingListItem(new ItemName("Matchstick"), new Quantity(1),
                    new ItemCategory("camping"), false),
            new PackingListItem(new ItemName("Long Underwear"), new Quantity(1),
                    new ItemCategory("camping"), false),
            new PackingListItem(new ItemName("Pillow"), new Quantity(2),
                    new ItemCategory("camping"), false),
            new PackingListItem(new ItemName("Plastic Bags"), new Quantity(7),
                    new ItemCategory("camping"), false),
            new PackingListItem(new ItemName("Sleeping Bag"), new Quantity(1),
                    new ItemCategory("camping"), false),
            new PackingListItem(new ItemName("Snacks"), new Quantity(10),
                    new ItemCategory("camping"), false),
            new PackingListItem(new ItemName("Tent"), new Quantity(1),
                    new ItemCategory("camping"), false),
            new PackingListItem(new ItemName("Toilet Paper"), new Quantity(3),
                    new ItemCategory("camping"), false),
            new PackingListItem(new ItemName("Towel"), new Quantity(1),
                    new ItemCategory("camping"), false),
            new PackingListItem(new ItemName("Utility Knife"), new Quantity(1),
                    new ItemCategory("camping"), false),
        };
    }

    /**
     * Toiletries preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] toiletriesPreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Conditioner"), new Quantity(1),
                    new ItemCategory("toiletries"), false),
            new PackingListItem(new ItemName("Contacts"), new Quantity(1),
                    new ItemCategory("toiletries"), false),
            new PackingListItem(new ItemName("Contacts Solution"), new Quantity(1),
                    new ItemCategory("toiletries"), false),
            new PackingListItem(new ItemName("Cotton Buds"), new Quantity(1),
                    new ItemCategory("toiletries"), false),
            new PackingListItem(new ItemName("Cotton Pads"), new Quantity(1),
                    new ItemCategory("toiletries"), false),
            new PackingListItem(new ItemName("Deodorant"), new Quantity(1),
                    new ItemCategory("toiletries"), false),
            new PackingListItem(new ItemName("Hairbrush"), new Quantity(1),
                    new ItemCategory("toiletries"), false),
            new PackingListItem(new ItemName("Makeup Bag"), new Quantity(1),
                    new ItemCategory("toiletries"), false),
            new PackingListItem(new ItemName("Makeup Remover"), new Quantity(1),
                    new ItemCategory("toiletries"), false),
            new PackingListItem(new ItemName("Nail Clipper"), new Quantity(1),
                    new ItemCategory("toiletries"), false),
            new PackingListItem(new ItemName("Perfume"), new Quantity(1),
                    new ItemCategory("toiletries"), false),
            new PackingListItem(new ItemName("Razor"), new Quantity(1),
                    new ItemCategory("toiletries"), false),
            new PackingListItem(new ItemName("Retainers"), new Quantity(1),
                    new ItemCategory("toiletries"), false),
            new PackingListItem(new ItemName("Shampoo"), new Quantity(1),
                    new ItemCategory("toiletries"), false),
            new PackingListItem(new ItemName("Toothbrush"), new Quantity(1),
                    new ItemCategory("toiletries"), false),
            new PackingListItem(new ItemName("Toothpaste"), new Quantity(1),
                    new ItemCategory("toiletries"), false),
            new PackingListItem(new ItemName("Tweezers"), new Quantity(1),
                    new ItemCategory("toiletries"), false)
        };
    }

    /**
     * Formal dinner female preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] formalDinnerFemalePreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Dress"), new Quantity(1),
                    new ItemCategory("formal dinner female"), false),
            new PackingListItem(new ItemName("High heels"), new Quantity(1),
                    new ItemCategory("formal dinner female"), false),
            new PackingListItem(new ItemName("Evening Bag"), new Quantity(1),
                    new ItemCategory("formal dinner female"), false),
            new PackingListItem(new ItemName("Jewellery"), new Quantity(1),
                    new ItemCategory("formal dinner female"), false),
            new PackingListItem(new ItemName("Makeup Bag"), new Quantity(1),
                    new ItemCategory("formal dinner female"), false)
        };
    }

    /**
     * Formal dinner male preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] formalDinnerMalePreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Shirt"), new Quantity(1),
                    new ItemCategory("formal dinner male"), false),
            new PackingListItem(new ItemName("Suit Jacket"), new Quantity(1),
                    new ItemCategory("formal dinner male"), false),
            new PackingListItem(new ItemName("Tie"), new Quantity(1),
                    new ItemCategory("formal dinner male"), false),
            new PackingListItem(new ItemName("Work Belt"), new Quantity(1),
                    new ItemCategory("formal dinner male"), false),
            new PackingListItem(new ItemName("Work Pants"), new Quantity(1),
                    new ItemCategory("formal dinner male"), false),
            new PackingListItem(new ItemName("Work Shoes"), new Quantity(1),
                    new ItemCategory("formal dinner male"), false)
        };
    }

    /**
     * Snow preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] snowPreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Helmet"), new Quantity(1),
                    new ItemCategory("snow"), false),
            new PackingListItem(new ItemName("Lip Balm"), new Quantity(3),
                    new ItemCategory("snow"), false),
            new PackingListItem(new ItemName("Long Johns"), new Quantity(2),
                    new ItemCategory("snow"), false),
            new PackingListItem(new ItemName("Long sleeved Shirt"), new Quantity(3),
                    new ItemCategory("snow"), false),
            new PackingListItem(new ItemName("Scarf"), new Quantity(1),
                    new ItemCategory("snow"), false),
            new PackingListItem(new ItemName("Ski Poles"), new Quantity(1),
                    new ItemCategory("snow"), false),
            new PackingListItem(new ItemName("Ski Snowboard Rental"),
                    new Quantity(1), new ItemCategory("snow"), false),
            new PackingListItem(new ItemName("Snow Gear Rental"), new Quantity(1),
                    new ItemCategory("snow"), false),
            new PackingListItem(new ItemName("Snow Gloves"), new Quantity(1),
                    new ItemCategory("snow"), false),
            new PackingListItem(new ItemName("Snow Goggles"), new Quantity(1),
                    new ItemCategory("snow"), false),
            new PackingListItem(new ItemName("Snow Hat"), new Quantity(1),
                    new ItemCategory("snow"), false),
            new PackingListItem(new ItemName("Snow Jacket"), new Quantity(1),
                    new ItemCategory("snow"), false),
            new PackingListItem(new ItemName("Snow Pants"), new Quantity(1),
                    new ItemCategory("snow"), false),
            new PackingListItem(new ItemName("Sunscreen"), new Quantity(1),
                    new ItemCategory("snow"), false),
            new PackingListItem(new ItemName("Winter boots"), new Quantity(1),
                    new ItemCategory("snow"), false)
        };
    }

    /**
     * Gym preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] gymPreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Gym Bag"), new Quantity(1),
                    new ItemCategory("gym"), false),
            new PackingListItem(new ItemName("Gym Shoes"), new Quantity(1),
                new ItemCategory("gym"), false),
            new PackingListItem(new ItemName("Gym Shorts"), new Quantity(1),
                    new ItemCategory("gym"), false),
            new PackingListItem(new ItemName("Gym Tshirt"), new Quantity(1),
                    new ItemCategory("gym"), false),
            new PackingListItem(new ItemName("Gym Towel"), new Quantity(1),
                    new ItemCategory("gym"), false),
            new PackingListItem(new ItemName("Headphone"), new Quantity(1),
                    new ItemCategory("gym"), false),
            new PackingListItem(new ItemName("Padlock"), new Quantity(1),
                    new ItemCategory("gym"), false),
            new PackingListItem(new ItemName("Water Bottle"), new Quantity(1),
                    new ItemCategory("gym"), false)
        };
    }

    /**
     * Beach preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] beachPreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Beach Bag"), new Quantity(1),
                    new ItemCategory("beach"), false),
            new PackingListItem(new ItemName("Beach Towel"), new Quantity(1),
                    new ItemCategory("beach"), false),
            new PackingListItem(new ItemName("Beach Umbrella"), new Quantity(1),
                    new ItemCategory("beach"), false),
            new PackingListItem(new ItemName("Hat"), new Quantity(1),
                    new ItemCategory("beach"), false),
            new PackingListItem(new ItemName("Sandals"), new Quantity(1),
                    new ItemCategory("beach"), false),
            new PackingListItem(new ItemName("Sunglasses"), new Quantity(1),
                    new ItemCategory("beach"), false),
            new PackingListItem(new ItemName("Sunscreen"), new Quantity(1),
                    new ItemCategory("beach"), false),
            new PackingListItem(new ItemName("Swimsuit"), new Quantity(1),
                    new ItemCategory("beach"), false),
            new PackingListItem(new ItemName("Swimsuit Coverup"), new Quantity(1),
                    new ItemCategory("beach"), false)
        };
    }

    /**
     * Music festival preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] musicFestivalPreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Bandana"), new Quantity(1),
                    new ItemCategory("music festival"), false),
            new PackingListItem(new ItemName("Ear Plugs"), new Quantity(1),
                    new ItemCategory("music festival"), false),
            new PackingListItem(new ItemName("Fanny Pack"), new Quantity(1),
                    new ItemCategory("music festival"), false),
            new PackingListItem(new ItemName("Festival Outfit"), new Quantity(1),
                    new ItemCategory("music festival"), false),
            new PackingListItem(new ItemName("Food"), new Quantity(1),
                    new ItemCategory("music festival"), false),
            new PackingListItem(new ItemName("ID"), new Quantity(1),
                    new ItemCategory("music festival"), false),
            new PackingListItem(new ItemName("Money"), new Quantity(1),
                    new ItemCategory("music festival"), false),
            new PackingListItem(new ItemName("Shoes"), new Quantity(1),
                    new ItemCategory("music festival"), false),
            new PackingListItem(new ItemName("Tissues"), new Quantity(1),
                    new ItemCategory("music festival"), false),
        };
    }

    /**
     * Photography preset packing list item [ ].
     *
     * @return the packing list item [ ]
     */
    public static PackingListItem[] photographyPreset() {
        return new PackingListItem[] {
            new PackingListItem(new ItemName("Battery"), new Quantity(2),
                    new ItemCategory("photography"), false),
            new PackingListItem(new ItemName("Battery Charger"), new Quantity(1),
                    new ItemCategory("photography"), false),
            new PackingListItem(new ItemName("Camera"), new Quantity(1),
                    new ItemCategory("photography"), false),
            new PackingListItem(new ItemName("Camera Bag"), new Quantity(1),
                    new ItemCategory("photography"), false),
            new PackingListItem(new ItemName("Lenses"), new Quantity(1),
                    new ItemCategory("photography"), false),
            new PackingListItem(new ItemName("Memory Card"), new Quantity(3),
                    new ItemCategory("photography"), false),
            new PackingListItem(new ItemName("Tripod"), new Quantity(1),
                    new ItemCategory("photography"), false),
        };
    }
}
