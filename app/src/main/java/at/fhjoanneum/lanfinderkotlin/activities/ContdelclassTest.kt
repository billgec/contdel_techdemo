package at.fhjoanneum.lanfinderkotlin.activities

import org.junit.Test
import org.junit.Assert.*

/*
 * ContdelclassTest
 * Some unit tests for the techdemo
 * Mai 25, 2023
 */
class CalculatorTest {

    private val calculator = Contdelclass()

    @Test
    fun add_twoNumbers_returnsSum() {
        assertEquals(5, calculator.add(2, 3))
    }

    @Test
    fun subtract_twoNumbers_returnsDifference() {
        assertEquals(1, calculator.subtract(4, 3))
    }

    @Test
    fun multiply_twoNumbers_returnsProduct() {
        assertEquals(6, calculator.multiply(2, 3))
    }

    @Test
    fun divide_twoNumbers_returnsQuotient() {
        assertEquals(2, calculator.divide(4, 2))
    }

    @Test(expected = IllegalArgumentException::class)
    fun divide_divideByZero_throwsException() {
        calculator.divide(5, 0)
    }
}
