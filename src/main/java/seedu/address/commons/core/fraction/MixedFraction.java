package seedu.address.commons.core.fraction;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.math3.fraction.Fraction;

/**
 * Represents a mixed fraction.
 */
public class MixedFraction extends Fraction {
    private static final int NUM_OF_PARTS_IN_PURE_FRACTION = 1;
    private static final int WHOLE_PART_OF_PURE_FRACTION = 0;
    private static final int FRACTIONAL_PART_POSITION_IN_PURE_FRACTION = 0;
    private static final int NUM_OF_PARTS_IN_MIXED_FRACTION = 2;
    private static final int WHOLE_PART_POSITION_IN_MIXED_FRACTION = 0;
    private static final int FRACTIONAL_PART_POSITION_IN_MIXED_FRACTION = 1;
    private static final int NUM_OF_PARTS_IN_FRACTION = 2;
    private static final int NUMERATOR_POSITION = 0;
    private static final int DENOMINATOR_POSITION = 1;

    /**
     * Constructs a {@code MixedFraction} from a {@code Fraction}.
     */
    public MixedFraction(Fraction f) {
        super(f.getNumerator(), f.getDenominator());
    }

    /**
     * Constructs a {@code MixedFraction} from a numerator and a denominator.
     */
    public MixedFraction(int num, int den) {
        super(num, den);
    }

    /**
     * Parses the string argument as an unsigned mixed fraction.
     *
     * @param s a {@code String} containing the {@code MixedFraction} representation to be parsed
     * @return the mixed fraction value represented by the argument.
     * @throws NumberFormatException if the string does not contain a parsable unsigned mixed fraction.
     */
    public static MixedFraction parseUnsignedMixedFraction(String s) {
        if (s == null) {
            throw new NumberFormatException("null");
        }

        int wholePart;
        int fractionalPartPosition;
        String[] splitInput = s.split("\\s+");
        if (splitInput.length == NUM_OF_PARTS_IN_PURE_FRACTION) {
            wholePart = WHOLE_PART_OF_PURE_FRACTION;
            fractionalPartPosition = FRACTIONAL_PART_POSITION_IN_PURE_FRACTION;
        } else if (splitInput.length == NUM_OF_PARTS_IN_MIXED_FRACTION) {
            wholePart = Integer.parseInt(splitInput[WHOLE_PART_POSITION_IN_MIXED_FRACTION]);
            fractionalPartPosition = FRACTIONAL_PART_POSITION_IN_MIXED_FRACTION;
        } else {
            throw new NumberFormatException(s);
        }

        String[] splitFraction = splitInput[fractionalPartPosition].split("/");
        if (splitFraction.length != NUM_OF_PARTS_IN_FRACTION) {
            throw new NumberFormatException(s);
        }

        int numerator = Integer.parseInt(splitFraction[NUMERATOR_POSITION]);
        int denominator = Integer.parseInt(splitFraction[DENOMINATOR_POSITION]);
        numerator += wholePart * denominator;
        return new MixedFraction(numerator, denominator);
    }

    /**
     * Returns the {@code MixedFraction} representation of a {@code BigDecimal} value.
     *
     * @param value the {@code BigDecimal} value.
     * @return the {@code MixedFraction} representation of the {@code BigDecimal} value.
     */
    public static MixedFraction getFromBigDecimal(BigDecimal value) {
        String stringRepresentation = value.toString();
        boolean hasFractionalPart = stringRepresentation.split("\\.").length > 1;
        int wholePart = Integer.parseInt(stringRepresentation.split("\\.")[0]);
        int numerator = wholePart;
        int denominator = 1;

        if (hasFractionalPart) {
            String fractionalPartString = stringRepresentation.split("\\.")[1];
            denominator = new BigInteger("10").pow(fractionalPartString.length()).intValue();
            if (numerator >= 0) {
                numerator = wholePart * denominator + Integer.parseInt(fractionalPartString);
            } else {
                numerator = wholePart * denominator - Integer.parseInt(fractionalPartString);
            }
        }
        return new MixedFraction(numerator, denominator);
    }

    /**
     * Adds the specified mixed fraction.
     */
    public MixedFraction add(MixedFraction mixedFraction) {
        return new MixedFraction(super.add(mixedFraction));
    }

    /**
     * Subtracts the specified mixed fraction.
     */
    public MixedFraction subtract(MixedFraction mixedFraction) {
        return new MixedFraction(super.subtract(mixedFraction));
    }

    /**
     * Divides this mixed fraction by the specified mixed fraction.
     */
    public MixedFraction divide(MixedFraction mixedFraction) {
        return new MixedFraction(super.divide(mixedFraction));
    }

    @Override
    public String toString() {
        int wholePart = intValue();
        int numerator = getNumerator();
        int denominator = getDenominator();
        if (numerator > 0) {
            numerator = numerator - (wholePart * denominator);
        } else if (wholePart < 0) {
            numerator = (wholePart * denominator) - numerator;
        }

        if (numerator == 0) {
            return String.format("%d", wholePart);
        } else if (wholePart == 0) {
            return String.format("%d/%d", numerator, denominator);
        }
        return String.format("%d %d/%d", wholePart, numerator, denominator);
    }
}
