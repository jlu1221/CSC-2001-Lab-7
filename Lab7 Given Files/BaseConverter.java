import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Converts whole numbers between bases 2 through 36
 *
 * Digit values 0-9 are written '0'-'9', and 10-35 are written with letters:
 * 'A' = 10, 'B' = 11, ..., 'Z' = 35. Methods that read digits accept
 * upper- or lowercase letters; methods that write digits use uppercase.
 *
 * Only non-negative numbers are supported.
 *
 * Do not rename the class, the public methods, or change their
 * parameters/return types. You may add private helper methods.
 * Do not use Integer.parseInt, Integer.toString(n, base), Integer.valueOf,
 * Character.digit, or Character.forDigit in this file - the point is to
 * write the conversions yourself. (You may use them in your tests.)
 */
public class BaseConverter {

    /**
     * The smallest base supported.
     */
    public static final int MIN_BASE = 2;

    /**
     * The largest base supported (10 digits plus 26 letters).
     */
    public static final int MAX_BASE = 36;

    /**
     * The digit characters, in order of value: DIGITS.charAt(v) is the digit for value v.
     */
    public static final String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // ---------------------------------------------------------------
    // Part 1: reading digits
    // ---------------------------------------------------------------

    /**
     * Returns the value of a single digit character: '0'-'9' are 0-9, and
     * 'A'-'Z' (or 'a'-'z') are 10-35. Returns -1 for any other character.
     * Example: digitValue('7') is 7, digitValue('d') is 13, digitValue('?') is -1.
     */
    // purpose: returns the values of a single digit character 0-9 and a-z is 10-35
    public static int digitValue(char ch) {
        if (ch >= '0' && ch <= '9') {
            return ch - '0';
        }
        if (ch >= 'A' && ch <= 'Z') {
            return ch - 'A' + 10;
        }
        if (ch >= 'a' && ch <= 'z') {
            return ch - 'a' + 10;
        }
        // any other values return -1
        return -1;
    }

    /**
     * Returns the value of digits read as a number in the given base
     * Example: toDecimal("101101", 2) and toDecimal("2D", 16) both return 45.
     * throws IllegalArgumentException if base is not 2 to 36, if digits is
     * null or empty, if any character is not a valid digit in that base, or
     * if the value is larger than Integer.MAX_VALUE
     */

    // purpose: returns the values of digits read as a number base on a given base system
    public static int toDecimal(String digits, int base) {
        checkBase(base);
        if (digits == null || digits.isEmpty()) {
            throw new IllegalArgumentException("digits must not be null or empty");
        }

        long value = 0;
        for (int i = 0; i < digits.length(); i++) {
            int digit = digitValue(digits.charAt(i));
            if (digit < 0 || digit >= base) {
                throw new IllegalArgumentException("invalid digit for base " + base);
            }
            value = value * base + digit;
            if (value > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("value is too large for int");
            }
        }
        return (int) value;
    }

    // ---------------------------------------------------------------
    // Part 2: writing digits, using a stack
    // ---------------------------------------------------------------

    /**
     * Returns the digit character for a digit value: 0-9 give '0'-'9', and
     * 10-35 give 'A'-'Z' (uppercase).
     * Example: digitChar(13) is 'D'.
     * throws IllegalArgumentException if value is not 0 to 35
     */

    // purpose: return the digit char for a digit value, throws arg. exception if value isn't 0-35
    public static char digitChar(int value) {
        if (value < 0 || value >= DIGITS.length()) {
            throw new IllegalArgumentException("digit value must be 0-35");
        }
        return DIGITS.charAt(value);
    }

    /**
     * Returns n written in the given base, using repeated division: each
     * remainder n % base is pushed onto a stack, then the digits are popped
     * off in the right order. Letters are uppercase; there are no leading
     * zeros, except that 0 is written "0".
     * Example: toBase(45, 2) is "101101", toBase(45, 16) is "2D".
     * throws IllegalArgumentException if n is negative or base is not 2 to 36
     */

    // purpose: turn an integer into a given base using repeated division
    public static String toBase(int n, int base) {
        checkBase(base);
        checkNonNegative(n);
        if (n == 0) {
            return "0";
        }
        // TODO: push each remainder onto the stack, then pop them off
        Deque<Character> stack = new ArrayDeque<>();
        while (n > 0) {
            stack.push(digitChar(n % base));
            n /= base;
        }

        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        return result.toString();
    }

    // ---------------------------------------------------------------
    // Part 3: recursion
    // ---------------------------------------------------------------

    /**
     * Returns n written in the given base, the same as toBase, but recursively
     * and without a stack: the digits of n / base, followed by the digit for
     * n % base. A number smaller than the base is a single digit.
     * Example: toBaseRec(45, 3) is "1200".
     * throws IllegalArgumentException if n is negative or base is not 2 to 36
     */

    // purpose: return integer n in the given base value but recursively
    public static String toBaseRec(int n, int base) {
        checkBase(base);
        checkNonNegative(n);
        return toBaseRecHelper(n, base);
    }

    /**
     * Returns the number of digits needed to write n in the given base,
     * recursively: a number smaller than the base has 1 digit; otherwise it
     * has 1 more digit than n / base. countDigits(0, base) is 1.
     * Example: countDigits(4725, 10) is 4, countDigits(45, 2) is 6.
     * throws IllegalArgumentException if n is negative or base is not 2 to 36
     */

    // purpose: returns the number of digits needed to write the integer n in the given base
    public static int countDigits(int n, int base) {
        checkBase(base);
        checkNonNegative(n);
        return countDigitsHelper(n, base);
    }

    // ---------------------------------------------------------------
    // private helpers
    // ---------------------------------------------------------------

    /**
     * Throws IllegalArgumentException unless base is 2 to 36.
     */
    private static void checkBase(int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException("base must be 2 - 36");
        }
    }

    /**
     * Throws IllegalArgumentException if n is negative.
     */
    private static void checkNonNegative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n can not be negative");
        }
    }

    // returns n in base using recursive calls after input (helper)
    private static String toBaseRecHelper(int n, int base) {
        if (n < base) {
            return String.valueOf(digitChar(n));
        }
        return toBaseRecHelper(n / base, base) + digitChar(n % base);
    }

    // counts n's base digits recursively after input
    private static int countDigitsHelper(int n, int base) {
        if (n < base) {
            return 1;
        }
        return 1 + countDigitsHelper(n / base, base);
    }
}

// Part 3 Questions
/*
 1. countDigits(n, 2) makes 1 call for n = 1, then 2, 3, 4, 5, and 11 calls for n = 2, 4, 8, 16, and 1024 respectively.
 Each division by 2 removes one binary digit, including the final call that reaches 1.

 2. countDigits(n, base) and toBaseRec(n, base) are O(log n),
     which is O(log n). Each recursive call divides n by base, so the
     number of calls is the number of base digits in n.

 3. In toBaseRec, the later digits wait on the call stack. Each call
     pauses after making its recursive call; as calls return, each one
     appends its remainder digit. The call stack therefore provides the
     same reverse-order storage that the explicit stack provides in toBase.
*/