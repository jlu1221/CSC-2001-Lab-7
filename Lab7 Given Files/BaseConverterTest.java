import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for BaseConverter.
 *
 * These starter tests (with these exact names) should all pass by the
 * time you're done. They are NOT enough on their own - add your own
 * tests (see the TODO list at the bottom and the lab handout).
 */
public class BaseConverterTest {

    // ---- starter tests ----

    @Test
    void toDecimalLectureExamples() {
        assertEquals(45, BaseConverter.toDecimal("101101", 2));
        assertEquals(45, BaseConverter.toDecimal("55", 8));
        assertEquals(45, BaseConverter.toDecimal("2D", 16));
        assertEquals(45, BaseConverter.toDecimal("1200", 3));
        assertEquals(4725, BaseConverter.toDecimal("4725", 10));
    }

    @Test
    void toBaseLectureExamples() {
        assertEquals("101101", BaseConverter.toBase(45, 2));
        assertEquals("55", BaseConverter.toBase(45, 8));
        assertEquals("2D", BaseConverter.toBase(45, 16));
        assertEquals("1200", BaseConverter.toBase(45, 3));
        assertEquals("45", BaseConverter.toBase(45, 10));
    }

    @Test
    void countDigitsLectureExamples() {
        assertEquals(4, BaseConverter.countDigits(4725, 10));
        assertEquals(6, BaseConverter.countDigits(45, 2));
        assertEquals(2, BaseConverter.countDigits(45, 16));
    }

    @Test
    void digitValueTest() {
        assertEquals(0, BaseConverter.digitValue('0'));
        assertEquals(9, BaseConverter.digitValue('9'));
        assertEquals(10, BaseConverter.digitValue('A'));
    }

    @Test
    void toDecimalTest() {
        assertEquals(45, BaseConverter.toDecimal("2d", 16));
        assertEquals(0, BaseConverter.toDecimal("0", 2));
        assertEquals(45, BaseConverter.toDecimal("00101101", 2));
        assertEquals(1295, BaseConverter.toDecimal("ZZ", 36));
        for (int base : new int[] {2, 10, 16, 36}) {
            String digits = Integer.toString(Integer.MAX_VALUE, base);
            assertEquals(Integer.MAX_VALUE, BaseConverter.toDecimal(digits, base));
        }
    }

    @Test
    void toDecimalThrowTest() {
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("129", 8));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("-45", 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("4 5", 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal(null, 10));
    }

    @Test
    void digitCharTest() {
        assertEquals('0', BaseConverter.digitChar(0));
        assertEquals('9', BaseConverter.digitChar(9));
        assertEquals('A', BaseConverter.digitChar(10));
        assertEquals('Z', BaseConverter.digitChar(35));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.digitChar(-1));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.digitChar(36));
    }

    @Test
    void writingThrowTest() {
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toBase(-1, 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toBase(1, 1));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toBaseRec(-1, 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toBaseRec(1, 37));
    }

    @Test
    void writingTest() {
        assertEquals("0", BaseConverter.toBase(0, 2));
        assertEquals("0", BaseConverter.toBaseRec(0, 36));
        assertEquals("7", BaseConverter.toBase(7, 10));
        assertEquals("FF", BaseConverter.toBaseRec(255, 16));
        assertEquals("ZIK0ZJ", BaseConverter.toBase(Integer.MAX_VALUE, 36));
        assertEquals("ZIK0ZJ", BaseConverter.toBaseRec(Integer.MAX_VALUE, 36));
    }

    @Test
    void countDigitsTest() {
        assertEquals(1, BaseConverter.countDigits(0, 10));
        assertEquals(1, BaseConverter.countDigits(9, 10));
        assertEquals(11, BaseConverter.countDigits(1024, 2));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.countDigits(-1, 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.countDigits(1, 1));
    }
}