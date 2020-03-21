package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.recipe.ingredient.Quantity;
import seedu.address.model.recipe.ingredient.Unit;

/**
 * Contains utility methods used for parsing strings into a Quantity.
 */
public class QuantityUtil {

    /**
     * Returns a list of Strings containing all the Units, in String, that HYBB supports.
     */
    public static List<String> getAvailUnitsAsList() {
        return Arrays.stream(Unit.values())
                .map(unit -> unit.toString())
                .collect(Collectors.toList());
    }

    /**
     * Parses the unit String into a Unit, assuming ParseExceptions have been handled.
     *
     */
    public static Unit parseUnit(String unit) {
        unit = unit.toLowerCase();

        switch (unit) {
        case "ml":
            return Unit.MILLILITER;
        case "g":
            return Unit.GRAM;
        case "tbsp":
            return Unit.TABLESPOON;
        case "tsp":
            return Unit.TEASPOON;
        case "cup":
            return Unit.CUP;
        default:
            return null;
        }
    }

    /**
     * Parses the quantity String into a Quantity, assuming ParseExceptions have been handled.
     *
     */
    public static Quantity parseQuantity(String quantity) {
        quantity = quantity.toLowerCase().trim();
        int indexOfUnit = indexOfFirstAlphabet(quantity);

        double magnitude = Double.parseDouble(quantity.substring(0, indexOfUnit));
        String unitString = quantity.substring(indexOfUnit);

        Unit unit = parseUnit(unitString);
        return new Quantity(magnitude, unit);
    }

    /**
     * Converts the Quantity into a double value of the quantity, converted into grams.
     */
    public static double getQuantityInGram(Quantity quantity) {
        Unit unit = quantity.getUnit();
        double magnitude = quantity.getMagnitude();
        switch (unit) {
        case GRAM:
            return magnitude;
        case MILLILITER:
            return magnitude;
        case TABLESPOON:
            return magnitude * 15;
        case TEASPOON:
            return magnitude * 5;
        case CUP:
            return magnitude; // todo: throw exception
        default:
            System.out.println("This unit is not supported.");
            return -1;
        }
    }

    /**
     * Returns the index of the first alphabet detected in a String.
     *
     */
    public static int indexOfFirstAlphabet(String details) {
        int index = 0;
        while (index < details.length() && !Character.isLetter(details.charAt(index))) {
            index++;
        }
        return index;
    }

}
