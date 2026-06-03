package test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class CalculatorTest {

    @Test
    public void testAdd() {
        Calculator calculator = new Calculator();
        assertEquals(5, calculator.add(2, 3));
        assertEquals(-1, calculator.add(-4, 3));
    }

    @Test
    public void testSubtract() {
        Calculator calculator = new Calculator();
        assertEquals(3, calculator.subtract(5, 2));
        assertEquals(-7, calculator.subtract(3, 10));
    }

    @Test
    public void testMultiply() {
        Calculator calculator = new Calculator();
        assertEquals(10, calculator.multiply(2, 5));
        assertEquals(-15, calculator.multiply(3, -5));
    }

    @Test
    public void testDivide() {
        Calculator calculator = new Calculator();
        assertEquals(2, calculator.divide(10, 5));
        assertEquals(-3, calculator.divide(-15, 5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivideByZero() {
        Calculator calculator = new Calculator();
        calculator.divide(10, 0);
    }
}

