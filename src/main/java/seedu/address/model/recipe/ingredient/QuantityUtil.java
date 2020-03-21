package seedu.address.model.recipe.ingredient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.parser.exceptions.ParseException;

public class QuantityUtil {

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

    public static int indexOfFirstAlphabet(String details) {
        int index = 0;
        while (index < details.length() && !Character.isLetter(details.charAt(index))) {
            index++;
        }
        return index;
    }

}
// UnitClass Util class
// getAvailableUnits()
// List<String> = UnitClass.values().stream().forEach(unit -> unit.toString())
// .collect(Collectors.toList())

// convertToGrams(Quantity q)
// if q.Units is countable, throw an error
// else, return a double g
// tbsp --> g (*15)
// tsp --> g (*5)
// ml --> g

/* UnitClass util
Find index of first alphabet
int i = 0;
while (i < string.length() && !Character.isDigit(string.charAt(i))) {
    i++;
}

Parsing units!

        if (index == 0 || index == details.length()) {
            // todo: throw exception, one of the fields are missing
        }

Integer.parseInt(string.substring(0, i).trim()); // if fail, throw exception that quantity needs to be a whole number

String unit = string.substring(i, string.length()).trim().tolowerCase();
check if unit belongs to enum
if (!units.contain(unit)) {
    // throw error that it does not belong to one of the units
}
 */